package org.apache.ignite.thick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.map.repository.config.EnableMapRepositories;

@SpringBootApplication
@EnableMapRepositories
public class ThickClientEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(ThickClientEntryPoint.class, args);

    }

}
