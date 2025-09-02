package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.VoteOption;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollControllerTest {
    private final RestClient restClient;
    private final RestClient userRestClient;

    public PollControllerTest(@LocalServerPort int port) {
        this.restClient = RestClient.builder().baseUrl("http://localhost:" + port + "/api/v1/polls").build();
        this.userRestClient = RestClient.builder().baseUrl("http://localhost:" + port + "/api/v1/users").build();
    }

    @Test
    public void createPoll() {
        // create user
        String creatorId = UUID.randomUUID().toString();
        User user = new User(creatorId, "user", "userpassword", "user@hotmail.com");
        userRestClient.post().uri("").body(user).retrieve().toEntity(User.class);

        // create vote options for the poll
        Set<VoteOption> voteOptions = new HashSet<>();
        VoteOption voteOption = new VoteOption("Red", 0);
        VoteOption voteOption2 = new VoteOption("Green", 1);
        voteOptions.add(voteOption);
        voteOptions.add(voteOption2);

        // create a new poll
        String pollId = UUID.randomUUID().toString();
        Poll poll = new Poll(pollId, "What is your favorite color?", voteOptions, creatorId, false, null, null);
        ResponseEntity<Poll> result =  restClient.post().uri("").body(poll).retrieve().toEntity(Poll.class);

        System.out.println(result.getBody());

        assertThat(result.getBody().getCreatorId()).isEqualTo(creatorId);
        assertThat(result.getBody().getQuestion()).isEqualTo("What is your favorite color?");
        assertThat(result.getBody().getVoteOptions()).isEqualTo(voteOptions);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
