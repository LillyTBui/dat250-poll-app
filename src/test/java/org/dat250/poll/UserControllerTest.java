package org.dat250.poll;

import org.dat250.poll.domains.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    private final RestClient restClient;

    UserControllerTest(@LocalServerPort int port) {
        this.restClient = RestClient.builder().baseUrl("http://localhost:" + port + "/api/v1/users").build();
    }

    @Test
    public void createUserTest(){
        // create user
        User user = new User(0, "user", "userpassword", "user@hotmail.com");
        ResponseEntity<User> results = restClient.post().uri("").body(user).retrieve().toEntity(User.class);

        System.out.println(results.getBody());

        assertThat(results.getBody().getUsername()).isEqualTo("user");
        assertThat(results.getBody().getPassword()).isEqualTo(null); // password hould not be visible
        assertThat(results.getBody().getEmail()).isEqualTo("user@hotmail.com");
        assertThat(results.getStatusCode().equals(HttpStatus.CREATED));
    }

    @Test
    public void getUserTest(){
        // create user
        User user = new User(0, "user", "userpassword", "user@hotmail.com");
        ResponseEntity<User> results = restClient.post().uri("").body(user).retrieve().toEntity(User.class);
        // get the specific user
        ResponseEntity<User> result = restClient.get().uri("/{id}", 1).retrieve().toEntity(User.class);

        System.out.println(result.getBody());

        assertThat(results.equals(result));
    }

    @Test
    public void getUsersTest(){
        // create users
        User user = new User(0, "user", "userpassword", "user@hotmail.com");
        restClient.post().uri("").body(user).retrieve().toEntity(User.class);

        User user2 = new User(1, "user2", "userpassword2", "user2@hotmail.com");
        restClient.post().uri("").body(user2).retrieve().toEntity(User.class);

        // get all users
        ResponseEntity<Collection<User>> results = restClient.get()
                .uri("")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Collection<User>>() {});

        System.out.println(results.getBody());

        assertThat(results.getStatusCode().equals(HttpStatus.OK));
    }

    @Test
    public void deleteUserTest(){
        // create user
        User user = new User(0, "user", "userpassword", "user@hotmail.com");
        ResponseEntity<User> results = restClient.post().uri("").body(user).retrieve().toEntity(User.class);

        System.out.println(results.getBody());

        // delete the same user
        ResponseEntity<Void> resultsAfterDelete = restClient.delete().uri("/{id}", 1).retrieve().toEntity(Void.class);
        assertThat(resultsAfterDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
