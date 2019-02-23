package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.ResponseApartment;
import com.bideris.dbservice.helpers.ResponseUser;
import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.Apartment;
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
        Apartment apartment = getApartmentById(id);
        if(apartment != null){
            response.setApartments(new ArrayList<Apartment>(){{
                add(apartment);
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
        List<Apartment> apartments = getApartmentsByLandlordName(name);
        if(apartments != null){
            response.setApartments(apartments);
            response.setStatus(statusCodes.getStatuse(0));
        }else
        {
            response.setStatus(statusCodes.getStatuse(12));
        }



        return response;

    }

    private List<Apartment> getApartmentsByLandlordName(String name) {

        return apartmentRepository.findApartmentsByUser(usersRepository.findUserByUserNameAndRole(name,rolel));

    }

    private Apartment getApartmentById(@PathVariable("id") Integer id) {

        return apartmentRepository.findApartmentById(id);

    }

    @PostMapping("/add/{username}")
    public ResponseApartment add(@RequestBody final Apartment apartment,@PathVariable("username") final String username){
        ResponseApartment response = new ResponseApartment();

        User user = usersRepository.findUserByUserName(username);
        if(user == null){
            response.setStatus(statusCodes.getStatuse(15));
            return response;
        }else {
            user.setApartmentCount(user.getApartmentCount() + 1);
            apartment.setUser(user);
            apartmentRepository.save(apartment);
            response.setStatus(statusCodes.getStatuse(0));

            response.setApartments(new ArrayList<Apartment>(){{
                addAll(getApartmentsByLandlordName(username) );
            }});
        }
        return response;
    }

    @PostMapping("/delete/{id}")
    public ResponseApartment delete(@PathVariable("id") final Integer id) {


        ResponseApartment response = new ResponseApartment();
        Apartment apartment = apartmentRepository.findApartmentById(id);
        if(apartment == null){
            response.setStatus(statusCodes.getStatuse(11));

            return response;
        }
        User user = apartment.getUser();

        user.setApartmentCount(user.getApartmentCount() - 1);
        apartmentRepository.delete(apartment);
        response.setStatus(statusCodes.getStatuse(0));

        response.setApartments(new ArrayList<Apartment>(){{
            addAll(getApartmentsByLandlordName(user.getUserName()) );
        }});

        return response;
    }
}
