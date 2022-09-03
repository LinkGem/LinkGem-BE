package com.linkgem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LinkGemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LinkGemApplication.class, args);
    }

}
