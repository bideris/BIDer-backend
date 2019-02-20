package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.PasswordHashing;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.model.UserRegistration;
import com.bideris.dbservice.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/rest/user")
public class UserServiceResource {

    private UsersRepository usersRepository;

    public UserServiceResource(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") final String username){

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



    @PostMapping("/register")
    public User register(@RequestBody final UserRegistration user){


        if(valid(user)) {
            usersRepository.save(user.toUser());
            return getUserByUserName(user.getUserName());
        }
        return null;

    }


    @PostMapping("/login")
    public String login(@RequestBody final User user){


        if(valid(user)) {

            return "Success";
        }
        return "Login failed";

    }


    private boolean valid(UserRegistration user){
        String emailRegex = "^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)";
        if(usersRepository.findUserByUserName(user.getUserName()) != null) {
            log.warn(user.getUserName() + " Username already exists");
            return false;
        }
        else if(usersRepository.findUserByEmail(user.getEmail()) != null){
            log.warn(user.getEmail() +" Email already exits");
            return false;
        }
        else if(user.getPassword().length() < 8 && !user.getPassword().matches("[0-9]")  ){
            log.warn("PasswordHashing : " + user.getPassword());
            log.warn("8 simbols containing numbers");
            return false;
        }else if (!user.getPassword().equals(user.getPassword2())){
            log.warn("PasswordHashing : " + user.getPassword());
            log.warn("Password2 : " + user.getPassword2());
            log.warn("passwords mach not");
            log.warn("Master Yoda am I");
            return false;
        }else if(!user.getEmail().matches(emailRegex)){
            log.warn("Email : " + user.getEmail());
            log.warn("Email not Email");
            return false;
        }

        return  true;

    }


    private boolean valid(User user){
        if(usersRepository.findUserByUserNameOrEmail(user.getUserName() ,user.getEmail()) !=null){
            if(PasswordHashing.hashPassword(user.getPassword()).equals(usersRepository.findUserByUserNameOrEmail(user.getUserName() ,user.getEmail()).
                                                 getPassword())) {
                log.info("Passwords mach user can login");
                return true;

            }else {
                log.warn("passwords did not mach");
                return false;
            }
        }else {
        log.warn("no user with such name or email");
        return false;
        }
    }





}
