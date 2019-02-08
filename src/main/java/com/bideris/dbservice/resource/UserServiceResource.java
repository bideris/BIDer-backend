package com.bideris.dbservice.resource;

import com.bideris.dbservice.model.User;
import com.bideris.dbservice.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/user")
public class UserServiceResource {

    private UsersRepository usersRepository;

    public UserServiceResource(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/{username}")
    public User getQuotes(@PathVariable("username") final String username){

        return getUserByUserName(username);

    }

    private User getUserByUserName(@PathVariable("username") String username) {

        return usersRepository.findUserByUserName(username);

    }

    @PostMapping("/add")
    public User add(@RequestBody final User user){

        usersRepository.save(user);

        return getUserByUserName(user.getUserName());
    }

    @PostMapping("/delete/{username}")
    public User delete(@PathVariable("username") final String username) {

        User user = usersRepository.findUserByUserName(username);
        usersRepository.delete(user);

        return getUserByUserName(username);
    }

}
