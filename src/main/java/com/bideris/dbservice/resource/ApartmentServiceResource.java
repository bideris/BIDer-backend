package com.bideris.dbservice.resource;

import com.bideris.dbservice.model.Apartment;
import com.bideris.dbservice.model.Landlord;
import com.bideris.dbservice.repository.ApartmentRepository;
import com.bideris.dbservice.repository.LandlordRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/apartment")
public class ApartmentServiceResource {

    private ApartmentRepository apartmentRepository;
    private LandlordRepository landlordRepository;

    public ApartmentServiceResource(ApartmentRepository apartmentRepository, LandlordRepository landlordRepository) {
        this.apartmentRepository = apartmentRepository;
        this.landlordRepository = landlordRepository;
    }

    @GetMapping("/{id}")
    public Apartment getQuotes(@PathVariable("id") final Integer id){

        return getApartmentById(id);

    }

    @GetMapping("/all/{name}")
    public List<Apartment> getQuotes(@PathVariable("name") final String name){

        return getApartmentsByLandlordName(name);

    }

    private List<Apartment> getApartmentsByLandlordName(@PathVariable("name") String name) {

        return apartmentRepository.findApartmentsByLandlord(landlordRepository.findLandlordByUserName(name));

    }

    private Apartment getApartmentById(@PathVariable("id") Integer id) {

        return apartmentRepository.findApartmentById(id);

    }

    @PostMapping("/add/{username}")
    public Apartment add(@RequestBody final Apartment apartment,@PathVariable("username") final String username){

        apartment.setLandlord((landlordRepository.findLandlordByUserName(username)));
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
