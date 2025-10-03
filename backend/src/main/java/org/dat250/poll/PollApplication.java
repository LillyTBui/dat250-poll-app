package org.dat250.poll;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PollApplication {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        SpringApplication.run(PollApplication.class, args);
    }
}
