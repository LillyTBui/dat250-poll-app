package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PollManager {
    private Map<Poll, Vote> votes = new HashMap<>();
    private Map<Vote, User> userVotes = new HashMap<>();

    private Map<String, String> userPolls = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private Map<String, Poll> polls = new HashMap<>();


    public Map<Vote, User> getUserVotes() {
        return userVotes;
    }

    public Map<String, Poll> getPolls() {
        return polls;
    }

    public void setPolls(Map<String, Poll> polls) {
        this.polls = polls;
    }

    public Map<String, String> getUserPolls() {
        return userPolls;
    }

    public void setUserPolls(Map<String, String> userPolls) {
        this.userPolls = userPolls;
    }

    public void setUserVotes(Map<Vote, User> userVotes) {
        this.userVotes = userVotes;
    }

    public Map<Poll, Vote> getVotes() {
        return votes;
    }

    public void setVotes(Map<Poll, Vote> votes) {
        this.votes = votes;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    // add a new user
    public void add(User user){
        String uniqueID = UUID.randomUUID().toString();
        user.setId(uniqueID);
        this.users.put(user.getId(), user);
    }

    // user creates a new poll
    public void add(Poll poll){
        String uniqueID = UUID.randomUUID().toString();
        poll.setId(uniqueID);
        this.polls.put(poll.getId(), poll);
        this.userPolls.put(poll.getId(), poll.getCreatorId());
    }

}
