package com.bideris.dbservice.resource;


import com.bideris.dbservice.helpers.ResponseUser;
import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.helpers.Validation;
import com.bideris.dbservice.model.*;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.AuctionRepository;
import com.bideris.dbservice.repository.UserAuctionRepository;
import com.bideris.dbservice.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserServiceResource {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserAuctionRepository userAuctionRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    private Validation validation;
    private String role = "user";
    private StatusCodes statusCodes = new StatusCodes();

    public UserServiceResource() {
        validation = new Validation(usersRepository);
    }

    @GetMapping("/{username}")
    public ResponseUser getUser(@PathVariable("username") final String username){
        ResponseUser responseUser = new ResponseUser();
        User user = getUserByUserName(username);
        if(user == null){
            responseUser.setStatus(statusCodes.getStatuse(13));
        }else {
            responseUser.setUser(getUserByUserName(username));
            responseUser.setStatus(statusCodes.getStatuse(0));
        }
        return responseUser;

    }

    private User getUserByUserName(String username) {

        return usersRepository.findUserByUserNameAndRole(username,role);

    }

    @PostMapping("/add")
    public ResponseUser add(@RequestBody final User user){
        user.setRole(role);
        ResponseUser responseUser = new ResponseUser();
        User user2 = getUserByUserName(user.getUserName());
        if(user2 != null){
            responseUser.setStatus(statusCodes.getStatuse(21));
        }else {
            responseUser.setUser(user);
            responseUser.setStatus(statusCodes.getStatuse(0));
        }

        usersRepository.save(user);

        return responseUser;
    }

    @PostMapping("/delete/{username}")
    public ResponseUser delete(@PathVariable("username") final String username) {

        User user = usersRepository.findUserByUserNameAndRole(username,role);
        ResponseUser responseUser = new ResponseUser();
        if(user == null){
            responseUser.setStatus(statusCodes.getStatuse(13));
            return responseUser;
        }else {
            responseUser.setUser(getUserByUserName(username));
            responseUser.setStatus(statusCodes.getStatuse(0));
        }
        usersRepository.delete(user);
        return responseUser;

    }



    @PostMapping("/register")
    public ResponseUser register(@RequestBody final UserRegistration user){

        ResponseUser responseUser = validation.valid(user);

        if(responseUser.getStatus().getStatus() == 0) {
            User userf = user.toUser();
            userf.setRole(role);
            usersRepository.save(userf);
            responseUser.setUser(userf);
            return responseUser;
        }
        return responseUser;

    }


    @PostMapping("/login")
    public ResponseUser login(@RequestBody final User user){

        ResponseUser responseUser = this.validation.valid(user);
        if(responseUser.getStatus().getStatus() == 0) {
            //saugoti sesija? daryti dalykus
            return responseUser;
        }
        return responseUser;

    }

    @PostMapping("/like/{userId}/{postId}")
    public UserAuction LikePost(@PathVariable("userId") final Integer userId, @PathVariable("postId") final Integer postId){
        User user = usersRepository.findUserById(userId);
        Post post = apartmentRepository.findApartmentById(postId);
        Auction auction = auctionRepository.findAuctionByPostFk(postId);
        log.info("User - {} \n Post - {} \n Aucion - {}" ,user,post,auction);
        if(auction == null ){
            log.info("nėra auckijono");
            auction = new Auction();
            auction.setDuration(post.getDuration());
            auction.setStartDate(post.getStartDate());
            auction.setStatus("Pending");
            auction.setPost(post);
            auction.setPostFk(postId);
            auctionRepository.save(auction);
            log.info("New Auction - {}",auction);
        }

        UserAuction userAuction = new UserAuction();
        if(userAuctionRepository.findUserAuctionByUserFkAndAuctionFk(userId,
                auctionRepository.findAuctionByPostFk(postId).getId()) != null){
            log.info("Useris jau dalyvauja aukcione");

            return userAuction; // pakeisti i statusa

        }else {

            userAuction.setAuctionFk(auctionRepository.findAuctionByPostFk(postId).getId());
            userAuction.setAuction(auction);
            userAuction.setUser(user);
            userAuction.setUserFk(userId);
            userAuctionRepository.save(userAuction);
        }


    return userAuctionRepository.findUserAuctionsByAuctionFk(auction.getId());// pakeisti i statusa

    }

}
