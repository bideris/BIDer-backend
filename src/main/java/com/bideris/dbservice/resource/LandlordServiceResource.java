package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.Response;
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

    public LandlordServiceResource(UsersRepository UsersRepository, ApartmentRepository apartmentRepository) {
        this.usersRepository = UsersRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @GetMapping("/{username}")
    public Response getLandlord(@PathVariable("username") final String username){
        Response response = new Response();
        User user = getLandlordByLandlordName(username);
        if(user == null){
            response.setStatus(404);
            response.setStatusMessage("Landlord with this username not found");
        }
        else {
            response.setUser(user);
            response.setStatus(200);
            response.setStatusMessage("OK");
        }
        return response;

    }

    private User getLandlordByLandlordName(String username) {

        return usersRepository.findUserByUserNameAndRole(username,role);

    }

    @PostMapping("/add")
    public Response add(@RequestBody final User landlord){
        landlord.setRole(role);
        Response response = new Response();
        User user2 = getLandlordByLandlordName(landlord.getUserName());
        if(user2 != null){
            response.setStatus(500);
            response.setStatusMessage("landlord with this username already exists ");
            return response;
        }else {
            response.setUser(landlord);
            response.setStatus(200);
            response.setStatusMessage("OK");
        }
        usersRepository.save(landlord);


        return response;
    }

    @PostMapping("/delete/{username}")
    public Response delete(@PathVariable("username") final String username) {

        Response response = new Response();
        User landlord = usersRepository.findUserByUserNameAndRole(username,role);

        if(landlord == null){
            response.setStatus(404);
            response.setStatusMessage("landlord Not Found");
            return response;
        }else {
            response.setUser(getLandlordByLandlordName(username));
            response.setStatus(200);
            response.setStatusMessage("OK user deleted");
        }

        apartmentRepository.deleteAll(apartmentRepository.findApartmentsByUser(landlord));
        usersRepository.delete(landlord);

        return response;
    }



}
