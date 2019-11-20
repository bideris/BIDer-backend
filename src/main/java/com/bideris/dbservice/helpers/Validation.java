package com.bideris.dbservice.helpers;

import com.bideris.dbservice.configs.AppConfig;
import com.bideris.dbservice.model.User;
import com.bideris.dbservice.model.UserRegistration;
import com.bideris.dbservice.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Slf4j
@PropertySource("file:/home/benas/KTU/BIDer-backend/src/main/resources/project.properties")
public class Validation {
    AppConfig  appConfig = new AppConfig();
    String emailRegex = appConfig.getEmailRegex();
    private String role = appConfig.getUser();

    private UsersRepository usersRepository;
    private StatusCodes statusCodes = new StatusCodes();
    public Validation(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public ResponseUser valid(UserRegistration user){



        System.out.println("EMAILL " + appConfig.getEmailRegex());
        ResponseUser responseUser = new ResponseUser();

        if(usersRepository.findUserByUserNameAndRole(user.getUserName(),role) != null) {
            log.warn(user.getUserName() + " Username already exists");
            responseUser.setStatus(statusCodes.getStatuse(21));
            return responseUser;
        }
        else if(usersRepository.findUserByEmailAndRole(user.getEmail(),role) != null){
            log.warn(user.getEmail() +" Email already exits");
            responseUser.setStatus(statusCodes.getStatuse(23));
            return responseUser;
        }
        else if(user.getPassword().length() < 8 && !user.getPassword().matches("[0-9]")  ){
            log.warn("PasswordHashing : " + user.getPassword());
            responseUser.setStatus(statusCodes.getStatuse(30));
            return responseUser;
        }else if (!user.getPassword().equals(user.getPassword2())){
            log.warn("PasswordHashing : " + user.getPassword());
            log.warn("Password2 : " + user.getPassword2());
            responseUser.setStatus(statusCodes.getStatuse(31));
            return responseUser;
        }else if(!user.getEmail().matches(emailRegex)){
            log.warn("Email : " + user.getEmail());
            responseUser.setStatus(statusCodes.getStatuse(32));
            return responseUser;
        }
        responseUser.setStatus(statusCodes.getStatuse(0));
        responseUser.setUser(user.toUser());
        return responseUser;

    }


    public ResponseUser valid(User user){
        ResponseUser responseUser = new ResponseUser();
        if(usersRepository.findUserByUserNameOrEmailAndRole(user.getUserName() ,user.getEmail(),role) !=null){
            if(PasswordHashing.hashPassword(user.getPassword()).equals(usersRepository.findUserByUserNameOrEmailAndRole(user.getUserName() ,user.getEmail(),role).
                    getPassword())) {
                responseUser.setUser(user);
                responseUser.setStatus(statusCodes.getStatuse(0));
                return responseUser;

            }else {
                responseUser.setStatus(statusCodes.getStatuse(31));
                return responseUser;
            }
        }else {
            responseUser.setStatus(statusCodes.getStatuse(15));
            return responseUser;
        }
    }
}
