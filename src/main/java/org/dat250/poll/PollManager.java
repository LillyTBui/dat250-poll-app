package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PollManager {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Poll> polls = new HashMap<>();

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    public Map<String, Poll> getPolls() {
        return polls;
    }

    public void setPolls(Map<String, Poll> polls) {
        this.polls = polls;
    }

    // add a new user
    public void add(User user){
        String uniqueID = UUID.randomUUID().toString();
        user.setId(uniqueID);
        this.users.put(user.getId(), user);
    }

    // user creates a new poll
    public boolean add(Poll poll){
        // a poll must have a question and a set of voting options
        if (poll.getQuestion() == null || poll.getQuestion().isEmpty()){
            return false;
        }
        if (poll.getVoteOptions() == null || poll.getVoteOptions().size() < 2){
            return false;
        }

        // add newly created poll to manager
        String uniqueID = UUID.randomUUID().toString();
        poll.setId(uniqueID);
        this.polls.put(poll.getId(), poll);

        // add the poll to the user's list
        String userID = poll.getCreatorId();
        User user = users.get(userID);
        user.addPoll(poll);

        return true;
    }

    // user votes on poll
    public boolean addVote(Vote vote){
        // check if poll and user exists
        if (this.polls.containsKey(vote.getPollId()) && this.users.containsKey(vote.getUserId())) {
            Poll poll = this.polls.get(vote.getPollId());
            // check if the voteOption is valid
            if (poll.getVoteOptions().contains(vote.getVoteOption())) {
                String uniqueID = UUID.randomUUID().toString();
                vote.setId(uniqueID);
                poll.addVote(vote);
                User user = this.users.get(vote.getUserId());
                user.addVote(vote);
            }
            return true;
        }
        return false;
    }
}
