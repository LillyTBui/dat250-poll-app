package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.dat250.poll.domains.VoteOption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    public void createPoll() {
        // create user
        User user = new User(1, "user", "userpassword", "user@hotmail.com");
        userRestClient.post().uri("").body(user).retrieve().toEntity(User.class);

        Set<VoteOption> voteOptions = createVoteOption();
        ResponseEntity<Poll> result = createPoll(1, voteOptions);

        System.out.println(result.getBody());

        assertThat(result.getBody().getCreatorId()).isEqualTo(1);
        assertThat(result.getBody().getQuestion()).isEqualTo("What is your favorite color?");
        assertThat(result.getBody().getVoteOptions()).isEqualTo(voteOptions);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void getPolls(){
        // add another Poll
        Set<VoteOption> voteOptions = createVoteOption();
        ResponseEntity<Poll> result = createPoll(1, voteOptions);

        ResponseEntity<Collection<Poll>> results = restClient.get()
                .uri("")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Collection<Poll>>() {});

        System.out.println(results.getBody());

        assertThat(results.getStatusCode().equals(HttpStatus.OK));
        assertThat(results.getBody().size()).isGreaterThan(0);
    }

    @Test
    public void deletePoll(){
        ResponseEntity<Poll> result = restClient.delete().uri("/{id}", 1).retrieve().toEntity(Poll.class);
        System.out.println(result.getBody());
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void addVote(){
        Map<Integer, User> users = this.pollManager.getUsers();
        Map<Integer, Poll> polls = this.pollManager.getPolls();
        while(users.size() < 2){
            // create user
            User user = new User(1, "user", "userpassword", "user@hotmail.com");
            userRestClient.post().uri("").body(user).retrieve().toEntity(User.class);
        }
        if (polls.isEmpty()) {
            // add poll
            Set<VoteOption> voteOptions = createVoteOption();
            ResponseEntity<Poll> pollResult = createPoll(1, voteOptions);
        }
        System.out.println(this.pollManager.getPolls());
        // the second user votes on the poll the first user created
        VoteOption voteOption = new VoteOption("Red", 0);
        Vote vote = new Vote(0, 1 , 2, voteOption, null, true);
        ResponseEntity<Vote> result = restClient.post().uri("/{pollId}/votes", 1).body(vote).retrieve().toEntity(Vote.class);

        System.out.println(result.getBody());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void updatePoll(){
        VoteOption voteOption = new VoteOption("Green", 1);
        Vote vote = new Vote(1, 1 , 2, voteOption, null, true);
        ResponseEntity<Vote> result = restClient.put().uri("/{pollId}/votes/{voteId}", 1, 1).body(vote).retrieve().toEntity(Vote.class);

        System.out.println(result.getBody());

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    private ResponseEntity<Poll> createPoll(int creatorId, Set<VoteOption> voteOptions) {
        // create a new poll
        Poll poll = new Poll(0, "What is your favorite color?", voteOptions, creatorId, false, null, null);
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
