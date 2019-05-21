package com.bideris.dbservice.configs;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class EmailConfig {

    //@Value("${spring.mail.host}")
    private String host = "smtp.mailtrap.io";

    //@Value("${spring.mail.port}")
    private int port = 2525;

   // @Value("${spring.mail.username}")
    private String username = "b4df7caeb27cd3";

    //@Value("${spring.mail.password}")
    private  String password = "4b435c855949e8";


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
