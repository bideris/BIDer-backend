package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.ResponseUser;
import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/rest/landlord")
public class LandlordServiceResource {

    private UsersRepository usersRepository;
    private ApartmentRepository apartmentRepository;
    private String role = "landlord";
    private StatusCodes statusCodes = new StatusCodes();
    public LandlordServiceResource(UsersRepository UsersRepository, ApartmentRepository apartmentRepository) {
        this.usersRepository = UsersRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @GetMapping("/{username}")
    public ResponseUser getLandlord(@PathVariable("username") final String username){
        ResponseUser responseUser = new ResponseUser();
        User user = getLandlordByLandlordName(username);
        if(user == null){

            responseUser.setStatus(statusCodes.getStatuse(10));

        }
        else {
            responseUser.setUser(user);
            responseUser.setStatus(statusCodes.getStatuse(0));
        }
        return responseUser;

    }

    private User getLandlordByLandlordName(String username) {

        return usersRepository.findUserByUserNameAndRole(username,role);

    }

    @PostMapping("/add")
    public ResponseUser add(@RequestBody final User landlord){
        landlord.setRole(role);
        ResponseUser responseUser = new ResponseUser();
        User user2 = getLandlordByLandlordName(landlord.getUserName());
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

    @PostMapping("/delete/{username}")
    public ResponseUser delete(@PathVariable("username") final String username) {

        ResponseUser responseUser = new ResponseUser();
        User landlord = usersRepository.findUserByUserNameAndRole(username,role);

        if(landlord == null){
            responseUser.setStatus(statusCodes.getStatuse(10));
            return responseUser;
        }else {
            responseUser.setUser(getLandlordByLandlordName(username));
            responseUser.setStatus(statusCodes.getStatuse(0));
        }

        apartmentRepository.deleteAll(apartmentRepository.findApartmentsByUser(landlord));
        usersRepository.delete(landlord);

        return responseUser;
    }



}
