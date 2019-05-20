package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.ResponseApartment;
import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.Auction;
import com.bideris.dbservice.model.Post;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.AuctionRepository;
import com.bideris.dbservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/apartment")
public class ApartmentServiceResource {

    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AuctionRepository auctionRepository;

    private String rolel = "landlord";
    private StatusCodes statusCodes =new StatusCodes();

    public ApartmentServiceResource() {
    }

    @GetMapping("/{id}")
    public ResponseApartment getApartment(@PathVariable("id") final Integer id){
        ResponseApartment response = new ResponseApartment();
        Post post = getApartmentById(id);
        if(post != null){
            response.setPosts(new ArrayList<Post>(){{
                add(post);
            }});

            response.setStatus(statusCodes.getStatuse(0));

        }else
        {
            response.setStatus(statusCodes.getStatuse(10));
        }
        return response;

    }

    @GetMapping("/all/{landlordId}")
    public ResponseApartment getApartments(@PathVariable("landlordId") final Integer landlordId){


        ResponseApartment response = new ResponseApartment();
        List<Post> posts = getApartmentsByLandlordId(landlordId);
        if(posts != null){
            response.setPosts(posts);
            response.setStatus(statusCodes.getStatuse(0));
        }else
        {
            response.setStatus(statusCodes.getStatuse(12));
        }



        return response;

    }

    private List<Post> getApartmentsByLandlordId(Integer id) {

        return apartmentRepository.findApartmentsByUser(usersRepository.findUserByIdAndRole(id,rolel));

    }

    private Post getApartmentById(@PathVariable("id") Integer id) {

        return apartmentRepository.findApartmentById(id);

    }

    @PostMapping("/add/{landlordId}")
    public ResponseApartment add(@RequestBody final Post post, @PathVariable("landlordId") final Integer landlordId){
        ResponseApartment response = new ResponseApartment();

        User user = usersRepository.findUserByIdAndRole(landlordId,"landlord");
        if(user == null){
            user = usersRepository.findUserById(landlordId);
            user.setRole("landlord");
        }
        user.setApartmentCount(user.getApartmentCount() + 1);
        post.setUser(user);
        post.setUserFk(user.getId());
        apartmentRepository.save(post);
        response.setStatus(statusCodes.getStatuse(0));

        response.setPosts(new ArrayList<Post>(){{
            addAll(getApartmentsByLandlordId(landlordId) );
        }});

        return response;
    }

    @PostMapping("/delete/{id}")
    public ResponseApartment delete(@PathVariable("id") final Integer id) {


        ResponseApartment response = new ResponseApartment();
        Post post = apartmentRepository.findApartmentById(id);
        if(post == null){
            response.setStatus(statusCodes.getStatuse(11));

            return response;
        }
        User user = post.getUser();

        user.setApartmentCount(user.getApartmentCount() - 1);
        apartmentRepository.delete(post);
        response.setStatus(statusCodes.getStatuse(0));

        response.setPosts(new ArrayList<Post>(){{
            addAll(getApartmentsByLandlordId(user.getId()) );
        }});

        return response;
    }


    @GetMapping("/winners/{landlordId}")
    public List<Auction> getwinners(@PathVariable("landlordId") final Integer landlordId){


        List<Post> posts = getApartmentsByLandlordId(landlordId);

        List<Auction> users = new ArrayList<>();
        Auction auction;
        for (Post post: posts) {
            if((auction = auctionRepository.findAuctionByPostFk(post.getId())) != null)
                if(auction.getWinner() != null){
                    users.add(auction);
                }

        }

        return users;

    }
}
