package com.skillstorm.spring_profiles.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("prod")
@Component
public class ProdOnlyClass implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("###################################");
        System.out.println("##### PROD PROFILE IS RUNNING #####");
        System.out.println("###################################");
    }

}
