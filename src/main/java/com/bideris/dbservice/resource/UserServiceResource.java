package com.bideris.dbservice.resource;

import com.bideris.dbservice.helpers.PasswordHashing;
import com.bideris.dbservice.helpers.Response;
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
    private String role = "user";

    public UserServiceResource(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/{username}")
    public Response getUser(@PathVariable("username") final String username){
        Response response = new Response();
        User user = getUserByUserName(username);
        if(user == null){
            response.setStatus(404);
            response.setStatusMessage("User Not Found");
        }else {
            response.setUser(getUserByUserName(username));
            response.setStatus(200);
            response.setStatusMessage("OK");
        }
        return response;

    }

    private User getUserByUserName(String username) {

        return usersRepository.findUserByUserNameAndRole(username,role);

    }

    @PostMapping("/add")
    public Response add(@RequestBody final User user){
        user.setRole(role);
        Response response = new Response();
        User user2 = getUserByUserName(user.getUserName());
        if(user2 != null){
            response.setStatus(500);
            response.setStatusMessage("User with this username already exists ");
        }else {
            response.setUser(user);
            response.setStatus(200);
            response.setStatusMessage("OK");
        }

        usersRepository.save(user);

        return response;
    }

    @PostMapping("/delete/{username}")
    public Response delete(@PathVariable("username") final String username) {

        User user = usersRepository.findUserByUserNameAndRole(username,role);
        Response response = new Response();
        if(user == null){
            response.setStatus(404);
            response.setStatusMessage("User Not Found");
            return response;
        }else {
            response.setUser(getUserByUserName(username));
            response.setStatus(200);
            response.setStatusMessage("OK user deleted");
        }
        usersRepository.delete(user);
        return response;

    }



    @PostMapping("/register")
    public Response register(@RequestBody final UserRegistration user){

        Response response = valid(user);
        if(valid(user).getStatus() == 200) {
            usersRepository.save(user.toUser());
            return response;
        }
        return response;

    }


    @PostMapping("/login")
    public Response login(@RequestBody final User user){

        Response response = valid(user);
        if(response.getStatus() == 200) {
            //saugoti sesija? daryti dalykus
            return response;
        }
        return response;

    }


    private Response valid(UserRegistration user){
        String emailRegex = "^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)";
        Response response = new Response();
        if(usersRepository.findUserByUserNameAndRole(user.getUserName(),role) != null) {
            log.warn(user.getUserName() + " Username already exists");
            response.setStatus(500);
            response.setStatusMessage(user.getUserName() + " Username already exists");
            return response;
        }
        else if(usersRepository.findUserByEmailAndRole(user.getEmail(),role) != null){
            log.warn(user.getEmail() +" Email already exits");
            response.setStatus(500);
            response.setStatusMessage(user.getEmail() +" Email already exits");
            return response;
        }
        else if(user.getPassword().length() < 8 && !user.getPassword().matches("[0-9]")  ){
            log.warn("PasswordHashing : " + user.getPassword());
            log.warn("8 simbols containing numbers");
            response.setStatus(418);
            response.setStatusMessage("418 I'm a teapot");
            return response;
        }else if (!user.getPassword().equals(user.getPassword2())){
            log.warn("PasswordHashing : " + user.getPassword());
            log.warn("Password2 : " + user.getPassword2());
            log.warn("passwords mach not");
            log.warn("Master Yoda am I");
            response.setStatus(500);
            response.setStatusMessage("Passwords did mach not");
            return response;
        }else if(!user.getEmail().matches(emailRegex)){
            log.warn("Email : " + user.getEmail());
            log.warn("Email not Email");
            response.setStatus(500);
            response.setStatusMessage("Email not Email");
            return response;
        }
        response.setStatus(200);
        response.setStatusMessage("OK");
        response.setUser(user.toUser());
        return  response;

    }


    private Response valid(User user){
        Response response = new Response();
        if(usersRepository.findUserByUserNameOrEmailAndRole(user.getUserName() ,user.getEmail(),role) !=null){
            if(PasswordHashing.hashPassword(user.getPassword()).equals(usersRepository.findUserByUserNameOrEmailAndRole(user.getUserName() ,user.getEmail(),role).
                                                 getPassword())) {
                log.info("Passwords mach user can login");
                response.setUser(user);
                response.setStatus(200);
                response.setStatusMessage("OK");
                return response;

            }else {
                log.warn("passwords did not mach");
                response.setStatus(400);
                response.setStatusMessage("passwords did not mach");
                return response;
            }
        }else {
        log.warn("no user with such name or email");
            response.setStatus(404);
            response.setStatusMessage("You know what 404 means");
            return response;
        }
    }





}
