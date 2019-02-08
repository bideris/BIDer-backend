package com.bideris.dbservice.resource;

import com.bideris.dbservice.model.Landlord;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.LandlordRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/landlord")
public class LandlordServiceResource {

    private LandlordRepository landlordRepository;
    private ApartmentRepository apartmentRepository;

    public LandlordServiceResource(LandlordRepository landlordRepository, ApartmentRepository apartmentRepository) {
        this.landlordRepository = landlordRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @GetMapping("/{username}")
    public Landlord getQuotes(@PathVariable("username") final String username){

        return getLandlordByLandlordName(username);

    }

    private Landlord getLandlordByLandlordName(@PathVariable("username") String username) {

        return landlordRepository.findLandlordByUserName(username);

    }

    @PostMapping("/add")
    public Landlord add(@RequestBody final Landlord landlord){

        landlordRepository.save(landlord);

        return getLandlordByLandlordName(landlord.getUserName());
    }

    @PostMapping("/delete/{username}")
    public Landlord delete(@PathVariable("username") final String username) {


        Landlord landlord = landlordRepository.findLandlordByUserName(username);
        apartmentRepository.deleteAll(apartmentRepository.findApartmentsByLandlord(landlord));
        landlordRepository.delete(landlord);

        return getLandlordByLandlordName(username);
    }
}
