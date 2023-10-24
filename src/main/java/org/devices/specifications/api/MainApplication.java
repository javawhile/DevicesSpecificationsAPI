package org.devices.specifications.api;

import org.devices.specifications.api.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class MainApplication {

    public static void main(String[] args){
        SpringApplication.run(MainApplication.class, args);
    }

    @Autowired
    private MailService mailService;

    @Scheduled(cron = "0 0 * ? * *")
    public void sendEmail() {
        try {
            System.out.println("Starting email send");
            mailService.sendMail();
        } catch (Exception ignored) {

        }
    }

}
