package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.dat250.poll.domains.VoteOption;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PollManager {
    private Map<String, User> users = new HashMap<>();
    private Map<String, Poll> polls = new HashMap<>();
    private Map<String, Vote> votes = new HashMap<>();

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
            // check that user does not vote on its own poll
            if (poll.getCreatorId().equals(vote.getUserId())) {
                return false;
            }
            // check if user has not already voted
            Set<Vote> votes = poll.getVotes();
            for (Vote v : votes) {
                if (v.getUserId().equals(vote.getUserId())){
                    return false;
                }
            }
            // check that voteOption is valid
            if (poll.getVoteOptions().contains(vote.getVoteOption()) ) {
                String uniqueID = UUID.randomUUID().toString();
                vote.setId(uniqueID);
                poll.addVote(vote);
                User user = this.users.get(vote.getUserId());
                user.addVote(vote);
                this.votes.put(vote.getId(), vote);
            }
            return true;
        }
        return false;
    }

    // user updates existing vote
    public boolean updateVote(String pollId, String voteId, Vote vote) {
        // check if poll and vote exists
        if (!this.polls.containsKey(pollId) && !this.votes.containsKey(voteId)) {
            return false;
        }
        // check if user has a vote in the poll
        Poll poll = this.polls.get(pollId);
        Set<Vote> votes = poll.getVotes();
        boolean hasVoted = false;
        for (Vote v : votes) {
            if (v.getUserId().equals(vote.getUserId())){
                hasVoted = true;
                break;
            }
        }
        if (hasVoted){
            // check if new vote option is valid
            User user = this.users.get(vote.getUserId());
            VoteOption voteOption = vote.getVoteOption();
            if (poll.getVoteOptions().contains(voteOption) ) {
                // remove old vote from poll
                poll.removeVote(vote);
                // remove old vote from user
                this.users.get(vote.getUserId()).removeVote(vote);
                // remove old vote from memory
                this.votes.remove(voteId);
                // save new vote
                poll.addVote(vote);
                user.addVote(vote);
                this.votes.put(vote.getId(), vote);
                return true;
            }
        }
        return false;
    }
}
