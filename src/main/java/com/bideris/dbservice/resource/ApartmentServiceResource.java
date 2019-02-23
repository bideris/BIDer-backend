package com.bideris.dbservice.resource;

import com.bideris.dbservice.model.Apartment;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/rest/apartment")
public class ApartmentServiceResource {

    private ApartmentRepository apartmentRepository;
    private UsersRepository usersRepository;
    private String rolel = "landlord";

    public ApartmentServiceResource(ApartmentRepository apartmentRepository, UsersRepository usersRepository) {
        this.apartmentRepository = apartmentRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/{id}")
    public Apartment getApartment(@PathVariable("id") final Integer id){

        return getApartmentById(id);

    }

    @GetMapping("/all/{name}")
    public List<Apartment> getApartments(@PathVariable("name") final String name){

        return getApartmentsByLandlordName(name);

    }

    private List<Apartment> getApartmentsByLandlordName(String name) {

        return apartmentRepository.findApartmentsByUser(usersRepository.findUserByUserNameAndRole(name,rolel));

    }

    private Apartment getApartmentById(@PathVariable("id") Integer id) {

        return apartmentRepository.findApartmentById(id);

    }

    @PostMapping("/add/{username}")
    public Apartment add(@RequestBody final Apartment apartment,@PathVariable("username") final String username){

        User user = usersRepository.findUserByUserName(username);
        user.setApartmentCount(user.getApartmentCount() + 1);
        apartment.setUser(user);

        apartmentRepository.save(apartment);

        return getApartmentById(apartment.getId());
    }

    @PostMapping("/delete/{id}")
    public Apartment delete(@PathVariable("id") final Integer id) {

        Apartment apartment = apartmentRepository.findApartmentById(id);
        apartmentRepository.delete(apartment);

        return apartment;
    }
}
