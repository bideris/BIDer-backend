package com.bideris.dbservice.helpers;

import com.bideris.dbservice.configs.EmailConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/email")
public class EmailHelper {

    @Autowired
    private EmailConfig emailConfig;

    @Data
    static  class  Notification {
        @NotNull
        private String name;

        @NotNull
        @Email
        private String email;

        @NotNull
        @Min(10)
        private String feedback;

        @NotNull
        @Min(10)
        private String title;


    }


    @PostMapping
    public void SendNotification(@RequestBody Notification notification, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException("Feedback is not valid");
        }

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("BIDERIS");
        mailMessage.setTo(notification.getEmail()); //ciuj usser
        mailMessage.setSubject(notification.getTitle()); //idk
        mailMessage.setText(notification.getFeedback());

        // Send mail
        mailSender.send(mailMessage);
    }


}
