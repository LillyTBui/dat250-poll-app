package org.dat250.poll;

import org.dat250.poll.domains.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
    private final RestClient restClient;

    UserControllerTest(@LocalServerPort int port) {
        this.restClient = RestClient.builder().baseUrl("http://localhost:" + port + "/api/v1/users").build();
    }

    @Test
    @Order(1)
    public void createUserTest(){
        // create user
        User user = new User(1, "user", "userpassword", "user@hotmail.com");
        ResponseEntity<User> results = restClient.post().uri("").body(user).retrieve().toEntity(User.class);

        System.out.println(results.getBody());

        assertThat(results.getBody()).isNotNull();
        assertThat(results.getBody().getUsername()).isEqualTo("user");
        assertThat(results.getBody().getPassword()).isEqualTo(null); // password hould not be visible
        assertThat(results.getBody().getEmail()).isEqualTo("user@hotmail.com");
        assertThat(results.getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    @Order(2)
    public void getUserTest(){
        // get the first user
        ResponseEntity<User> results = restClient.get().uri("/{id}", 1).retrieve().toEntity(User.class);

        System.out.println(results.getBody());

        assertThat(results.getBody()).isNotNull();
        assertThat(results.getBody().getUsername()).isEqualTo("user");
        assertThat(results.getBody().getPassword()).isEqualTo(null); // password hould not be visible
        assertThat(results.getBody().getEmail()).isEqualTo("user@hotmail.com");
        assertThat(results.getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    @Order(3)
    public void getUsersTest(){
        // create second user
        User user2 = new User(1, "user2", "userpassword2", "user2@hotmail.com");
        restClient.post().uri("").body(user2).retrieve().toEntity(User.class);

        // get all users
        ResponseEntity<Collection<User>> results = restClient.get()
                .uri("")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Collection<User>>() {});

        System.out.println(results.getBody());

        assertThat(results.getStatusCode().equals(HttpStatus.OK));
        assertThat(results.getBody().size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    public void deleteUserTest(){
        // delete the last user
        ResponseEntity<Void> resultsAfterDelete = restClient.delete().uri("/{id}", 1).retrieve().toEntity(Void.class);
        assertThat(resultsAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
