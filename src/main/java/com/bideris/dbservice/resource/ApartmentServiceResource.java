package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.ResponseApartment;
import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.Post;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/rest/apartment")
public class ApartmentServiceResource {

    private ApartmentRepository apartmentRepository;
    private UsersRepository usersRepository;
    private String rolel = "landlord";
    private StatusCodes statusCodes =new StatusCodes();

    public ApartmentServiceResource(ApartmentRepository apartmentRepository, UsersRepository usersRepository) {
        this.apartmentRepository = apartmentRepository;
        this.usersRepository = usersRepository;
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

    @GetMapping("/all/{name}")
    public ResponseApartment getApartments(@PathVariable("name") final String name){


        ResponseApartment response = new ResponseApartment();
        List<Post> posts = getApartmentsByLandlordName(name);
        if(posts != null){
            response.setPosts(posts);
            response.setStatus(statusCodes.getStatuse(0));
        }else
        {
            response.setStatus(statusCodes.getStatuse(12));
        }



        return response;

    }

    private List<Post> getApartmentsByLandlordName(String name) {

        return apartmentRepository.findApartmentsByUser(usersRepository.findUserByUserNameAndRole(name,rolel));

    }

    private Post getApartmentById(@PathVariable("id") Integer id) {

        return apartmentRepository.findApartmentById(id);

    }

    @PostMapping("/add/{username}")
    public ResponseApartment add(@RequestBody final Post post, @PathVariable("username") final String username){
        ResponseApartment response = new ResponseApartment();

        User user = usersRepository.findUserByUserName(username);
        if(user == null){
            response.setStatus(statusCodes.getStatuse(15));
            return response;
        }else {
            user.setApartmentCount(user.getApartmentCount() + 1);
            post.setUser(user);
            apartmentRepository.save(post);
            response.setStatus(statusCodes.getStatuse(0));

            response.setPosts(new ArrayList<Post>(){{
                addAll(getApartmentsByLandlordName(username) );
            }});
        }
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
            addAll(getApartmentsByLandlordName(user.getUserName()) );
        }});

        return response;
    }
}
