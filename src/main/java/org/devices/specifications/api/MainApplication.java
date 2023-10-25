package org.devices.specifications.api;

import org.devices.specifications.api.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
public class MainApplication {

    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

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
        } catch (final Exception ignored) {

        }
    }

    @Scheduled(cron = "* */3 * * * *")
    public void invokeSelf() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = "https://api-dev-specs.onrender.com/status";
            ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
            logger.info("invokeSelf: {}", response.getBody());
        } catch (final Exception ignored) {

        }
    }

}
