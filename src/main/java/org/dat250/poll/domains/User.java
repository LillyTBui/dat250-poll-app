package org.dat250.poll.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private final Set<Poll> polls = new HashSet<>();
    private final Set<Vote> votes = new HashSet<>();

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }

    public void addPoll(Poll poll) {
        this.polls.add(poll);
    }

    public void removePoll(Poll poll) {
        this.polls.remove(poll);
    }

    public void removeVote(Vote vote) {
        this.votes.remove(vote);
    }
}
