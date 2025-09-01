package org.dat250.poll.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public class User {
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private Set<Poll> polls = new HashSet<>();
    private Set<Vote> votes = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String email, String id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }

    public void addPoll(Poll poll) {
        this.polls.add(poll);
    }

    public void removePoll(Poll poll) {
        this.polls.remove(poll);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(polls, user.polls) && Objects.equals(votes, user.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email, polls, votes);
    }
}
