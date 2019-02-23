package com.bideris.dbservice.helpers;

import com.bideris.dbservice.model.User;
import com.bideris.dbservice.model.UserRegistration;
import com.bideris.dbservice.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validation {

    private UsersRepository usersRepository;
    private String role = "user";
    private StatusCodes statusCodes = new StatusCodes();
    public Validation(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public ResponseUser valid(UserRegistration user){
        String emailRegex = "^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)";
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
