package com.lm_api.librarymanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LibraryMangementApiApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(LibraryMangementApiApplication.class, args);
    }

}
