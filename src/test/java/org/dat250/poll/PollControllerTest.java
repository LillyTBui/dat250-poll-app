package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.dat250.poll.domains.VoteOption;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PollControllerTest {
    private final RestClient restClient;
    private final RestClient userRestClient;

    @Autowired
    private PollManager pollManager;

    public PollControllerTest(@LocalServerPort int port) {
        this.restClient = RestClient.builder().baseUrl("http://localhost:" + port + "/api/v1/polls").build();
        this.userRestClient = RestClient.builder().baseUrl("http://localhost:" + port + "/api/v1/users").build();
    }

    @Test
    @Order(1)
    public void createPoll() {
        // create user
        User user = new User(1, "user", "userpassword", "user@hotmail.com");
        userRestClient.post().uri("").body(user).retrieve().toEntity(User.class);

        Set<VoteOption> voteOptions = createVoteOption();
        ResponseEntity<Poll> result = createPoll(1, voteOptions, null);

        System.out.println(result.getBody());

        assertThat(result.getBody().getCreatorId()).isEqualTo(1);
        assertThat(result.getBody().getQuestion()).isEqualTo("What is your favorite color?");
        assertThat(result.getBody().getVoteOptions()).isEqualTo(voteOptions);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    public void getPolls(){
        // add another Poll
        ResponseEntity<Poll> result = createPoll(1, createVoteOption(), Instant.now().plus(Duration.ofDays(70)));

        ResponseEntity<Collection<Poll>> results = restClient.get()
                .uri("")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Collection<Poll>>() {});

        System.out.println(results.getBody());

        assertThat(results.getStatusCode().equals(HttpStatus.OK));
        assertThat(results.getBody().size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void addVote(){
        // create second user
        User user = new User(2, "user2", "userpassword2", "user2@hotmail.com");
        userRestClient.post().uri("").body(user).retrieve().toEntity(User.class);

        // the second user votes on the poll the first user created
        VoteOption voteOption = new VoteOption("Red", 0);
        Vote vote = new Vote(0, 1 , 2, voteOption, null);
        ResponseEntity<Vote> result = restClient.post().uri("/{pollId}/votes", 1).body(vote).retrieve().toEntity(Vote.class);

        System.out.println(result.getBody());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(4)
    public void invalidVote(){
        // the second user votes on the second poll but is not within publishedAt and validUntil
        VoteOption voteOption = new VoteOption("Red", 0);
        Vote vote = new Vote(1, 2 , 2, voteOption, null);

        String result = restClient.post().uri("/{pollId}/votes", 2).body(vote).retrieve().onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
        }).body(String.class);

        System.out.println(result);

        assertThat(Objects.equals(result, "null"));
    }

    @Test
    @Order(5)
    public void updateVote(){
        // second user updates their vote on poll 1
        VoteOption voteOption = new VoteOption("Green", 1);
        Vote vote = new Vote(1, 1 , 2, voteOption, null);
        ResponseEntity<Vote> result = restClient.put().uri("/{pollId}/votes/{voteId}", 1, 1).body(vote).retrieve().toEntity(Vote.class);

        System.out.println(result.getBody());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(6)
    public void getUserVote(){
        // retrieve the vote we made for second user)
        ResponseEntity<Map<Integer, Vote>> userVotes = userRestClient.get().uri("/{id}/votes", 2).retrieve().toEntity(new ParameterizedTypeReference<Map<Integer, Vote>>() {});

        System.out.println(userVotes.getBody());

        assertThat(userVotes.getStatusCode()).isEqualTo(HttpStatus.OK);
        // should only be one vote as we update existing vote
        assertThat(userVotes.getBody().size()).isEqualTo(1);
    }

    @Test
    @Order(8)
    public void deletePoll(){
        ResponseEntity<Poll> result = restClient.delete().uri("/{id}", 1).retrieve().toEntity(Poll.class);
        System.out.println(result.getBody());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(9)
    public void getUserVoteAfterDeletingPoll(){
        // retrieve the vote we made for second user)
        ResponseEntity<Map<Integer, Vote>> userVotes = userRestClient.get().uri("/{id}/votes", 2).retrieve().toEntity(new ParameterizedTypeReference<Map<Integer, Vote>>() {});

        System.out.println(userVotes.getBody());

        assertThat(userVotes.getStatusCode()).isEqualTo(HttpStatus.OK);
        // should not be any votes after poll is deleted
        assertThat(userVotes.getBody().size()).isEqualTo(0);
    }

    private ResponseEntity<Poll> createPoll(int creatorId, Set<VoteOption> voteOptions, Instant publishedAt) {
        // create a new poll
        Poll poll = new Poll(0, "What is your favorite color?", voteOptions, creatorId, false, publishedAt, null);
        return restClient.post().uri("").body(poll).retrieve().toEntity(Poll.class);
    }

    private Set<VoteOption> createVoteOption(){
        // create vote options for the poll
        Set<VoteOption> voteOptions = new HashSet<>();
        VoteOption voteOption = new VoteOption("Red", 0);
        VoteOption voteOption2 = new VoteOption("Green", 1);
        voteOptions.add(voteOption);
        voteOptions.add(voteOption2);

        return voteOptions;
    }
}
