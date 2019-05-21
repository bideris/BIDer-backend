package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.ResponseUser;
import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.*;
import com.bideris.dbservice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/landlord")
public class LandlordServiceResource {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserAuctionRepository userAuctionRepository;

    @Autowired
    BidRepository bidRepository;

    @Autowired
    MessageRepository messageRepository;

    private String role = "landlord";
    private StatusCodes statusCodes = new StatusCodes();

    public LandlordServiceResource() {

    }

    @GetMapping("/{id}")
    public ResponseUser getLandlord(@PathVariable("id") final Integer id){
        ResponseUser responseUser = new ResponseUser();
        User user = getLandlordByLandlordId(id);
        if(user == null){

            responseUser.setStatus(statusCodes.getStatuse(10));

        }
        else {
            responseUser.setUser(user);
            responseUser.setStatus(statusCodes.getStatuse(0));
        }
        return responseUser;

    }

    private User getLandlordByLandlordId(Integer landlordId) {

        return usersRepository.findUserByIdAndRole(landlordId,role);

    }

    @PostMapping("/add")
    public ResponseUser add(@RequestBody final User landlord){
        landlord.setRole(role);
        ResponseUser responseUser = new ResponseUser();
        User user2 = getLandlordByLandlordId(landlord.getId());
        if(user2 != null){
            responseUser.setStatus(statusCodes.getStatuse(20));
            return responseUser;
        }else {
            responseUser.setUser(landlord);
            responseUser.setStatus(statusCodes.getStatuse(0));
        }
        usersRepository.save(landlord);


        return responseUser;
    }

    @PostMapping("/delete/{landlordId}")
    public ResponseUser delete(@PathVariable("landlordId") final Integer landlordId) {

        ResponseUser responseUser = new ResponseUser();
        User landlord = usersRepository.findUserByIdAndRole(landlordId,role);

        if(landlord == null){
            responseUser.setStatus(statusCodes.getStatuse(10));
            return responseUser;
        }else {
            responseUser.setUser(getLandlordByLandlordId(landlordId));
            responseUser.setStatus(statusCodes.getStatuse(0));
        }

        apartmentRepository.deleteAll(apartmentRepository.findApartmentsByUser(landlord));
        usersRepository.delete(landlord);

        return responseUser;
    }

    @GetMapping("/deleteWinner/{auctionId}")
    public int getwinners(@PathVariable("auctionId") final Integer auctionId){

        Auction auction = auctionRepository.findAuctionById(auctionId);
        userAuctionRepository.deleteAll(userAuctionRepository.findUserAuctionsByAuctionFk(auctionId));
        bidRepository.deleteAll(bidRepository.findBidsByAuctionFk(auctionId));
        messageRepository.deleteAll(messageRepository.findMessagesByAuctionFk(auctionId));
        //bid
        //message

        auctionRepository.delete(auction);

        return 0;


    }
    @PostMapping("/writeReview/{ownerId}/{userid}")
    private List<Review> writeReview(@PathVariable("ownerId") final Integer ownerId,
                               @PathVariable("userid") final Integer userid,
                               @RequestBody final Review review){
        review.setUserFFk(ownerId);//vettintojas
        review.setUserRFk(userid);
        review.setUserF(usersRepository.findUserById(ownerId));
        review.setUserR(usersRepository.findUserById(userid));
        review.setDate(new Date());
        reviewRepository.save(review);
        return reviewRepository.findReviewsByUserRFk(userid);
    }



}
