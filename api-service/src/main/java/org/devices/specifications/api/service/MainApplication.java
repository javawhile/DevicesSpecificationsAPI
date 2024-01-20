package org.devices.specifications.api.service;

import org.devices.specifications.api.service.services.impl.ApplicationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.UUID;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "org.devices.specifications.api.*")
@EntityScan(basePackages = "org.devices.specifications.api.*")
@EnableScheduling
public class MainApplication extends SpringBootServletInitializer {

    private final Logger logger = LoggerFactory.getLogger(MainApplication.class);

    @Autowired
    private ApplicationServiceImpl applicationServiceImpl;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }

    @PostConstruct
    public void initApplicationPassword() {
        String uuid = UUID.randomUUID().toString();
        applicationServiceImpl.setPassword(uuid);
        logger.info("Using Application secure password = {}", applicationServiceImpl.getPassword());
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void invokeSelf() {
        try {
            String statusUrl = applicationServiceImpl.getSelfUrl() + "/status";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(statusUrl, String.class);
            if (response.hasBody() && response.getStatusCode() == HttpStatus.OK) {
                logger.info("selfInvoke Done statusUrl={}", statusUrl);
            } else {
                logger.error("selfInvoke Failure statusUrl={}", statusUrl);
            }
        } catch (final Exception exception) {
            String message = exception.getMessage();
            logger.error("Error while selfInvoke. message={}", message);
        }
    }
}
