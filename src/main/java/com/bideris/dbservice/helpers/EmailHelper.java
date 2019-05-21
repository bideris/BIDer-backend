package com.bideris.dbservice.helpers;

import com.bideris.dbservice.configs.EmailConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
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

@Slf4j
public class EmailHelper {

    @Autowired
    private EmailConfig emailConfig = new EmailConfig();

    @Data
    public static class  Notification {
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

        public Notification(@NotNull String name, @NotNull @Email String email, @NotNull @Min(10) String feedback, @NotNull @Min(10) String title) {
            this.name = name;
            this.email = email;
            this.feedback = feedback;
            this.title = title;
        }
    }

    public void SendNotification(Notification notification) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(this.emailConfig.getHost());
        mailSender.setPort(this.emailConfig.getPort());
        mailSender.setUsername(this.emailConfig.getUsername());
        mailSender.setPassword(this.emailConfig.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("BIDERIS");
        mailMessage.setTo(notification.getEmail());
        mailMessage.setSubject(notification.getTitle());
        mailMessage.setText(notification.getFeedback());

        // Send mail
        mailSender.send(mailMessage);
        log.info("Email sent");
    }


}
