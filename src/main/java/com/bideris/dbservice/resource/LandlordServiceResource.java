package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.ResponseUser;
import com.bideris.dbservice.helpers.StatusCodes;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/landlord")
public class LandlordServiceResource {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    private String role = "landlord";
    private StatusCodes statusCodes = new StatusCodes();

    public LandlordServiceResource() {

    }

    @GetMapping("/{id}")
    public ResponseUser getLandlord(@PathVariable("id") final Integer id){
        ResponseUser responseUser = new ResponseUser();
        User user = getLandlordByLandlordName(id);
        if(user == null){

            responseUser.setStatus(statusCodes.getStatuse(10));

        }
        else {
            responseUser.setUser(user);
            responseUser.setStatus(statusCodes.getStatuse(0));
        }
        return responseUser;

    }

    private User getLandlordByLandlordName(Integer landlordId) {

        return usersRepository.findUserByIdAndRole(landlordId,role);

    }

    @PostMapping("/add")
    public ResponseUser add(@RequestBody final User landlord){
        landlord.setRole(role);
        ResponseUser responseUser = new ResponseUser();
        User user2 = getLandlordByLandlordName(landlord.getId());
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
            responseUser.setUser(getLandlordByLandlordName(landlordId));
            responseUser.setStatus(statusCodes.getStatuse(0));
        }

        apartmentRepository.deleteAll(apartmentRepository.findApartmentsByUser(landlord));
        usersRepository.delete(landlord);

        return responseUser;
    }

//    @PostMapping("/users/{id}")
//    public List<User> getUserListByPost(){
//
//    }


}
