package com.sunjiahui;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
@Controller
public class SampleWebSecureApplication implements WebMvcConfigurer {
    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(SampleWebSecureApplication.class).run(args);
    }

}
