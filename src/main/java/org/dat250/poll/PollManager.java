package org.dat250.poll;

import lombok.Data;
import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.dat250.poll.domains.VoteOption;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Data
public class PollManager {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Poll> polls = new HashMap<>();
    private final Map<Integer, Vote> votes = new HashMap<>();

    private final AtomicInteger nextId = new AtomicInteger(0);
    private final AtomicInteger pollId = new AtomicInteger(0);
    private final AtomicInteger voteId = new AtomicInteger(0);

    public Integer getNextId() {
        return nextId.incrementAndGet();
    }

    public Integer getPollId() {
        return pollId.incrementAndGet();
    }

    public Integer getVoteId() {
        return voteId.incrementAndGet();
    }

    // add a new user
    public boolean add(User user){
        // check if user has the mandatory fields
        if (!user.getUsername().isEmpty() && !user.getEmail().isEmpty()) {
            user.setId(getNextId());
            this.users.put(user.getId(), user);
            return true;
        }
        return false;
    }

    // remove a user
    public boolean removeUser(int userId){
        if (this.users.containsKey(userId)) {
            this.users.remove(userId);
            return true;
        }
        return false;
    }

    // user creates a new poll
    public boolean add(Poll poll){
        // check if user exists
        if (!this.users.containsKey(poll.getCreatorId())) {
            return false;
        }

        // a poll must have a question
        if (poll.getQuestion() == null || poll.getQuestion().isEmpty()){
            return false;
        }
        // a poll must have at least 2 voting options
        if (poll.getVoteOptions() == null || poll.getVoteOptions().size() < 2){
            return false;
        }

        // add newly created poll to manager
        poll.setId(getPollId());
        this.polls.put(poll.getId(), poll);

        // add the poll to the user's list
        int userID = poll.getCreatorId();
        User user = this.users.get(userID);
        user.addPoll(poll);

        return true;
    }

    // user votes on poll
    public boolean addVote(Vote vote){
        // check if poll and user exists
        if (this.polls.containsKey(vote.getPollId()) && this.users.containsKey(vote.getUserId())) {
            Poll poll = this.polls.get(vote.getPollId());
            // check that user does not vote on its own poll
            if (poll.getCreatorId() == vote.getUserId()) {
                return false;
            }
            // check if user has not already voted
            Set<Vote> votes = poll.getVotes();
            for (Vote v : votes) {
                if (v.getUserId() == vote.getUserId()){
                    return false;
                }
            }
            // check that voteOption is valid
            if (poll.getVoteOptions().contains(vote.getVoteOption()) ) {
                vote.setId(getVoteId());
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
    public boolean updateVote(int pollId, int voteId, Vote vote) {
        // check if poll and vote exists
        if (!this.polls.containsKey(pollId) && !this.votes.containsKey(voteId)) {
            return false;
        }
        // check if user has a vote in the poll
        Poll poll = this.polls.get(pollId);
        Set<Vote> votes = poll.getVotes();
        boolean hasVoted = false;
        for (Vote v : votes) {
            if (v.getUserId() == vote.getUserId()){
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
                user.removeVote(vote.getId());
                // remove old vote from memory
                this.votes.remove(voteId);
                // save new vote
                vote.setId(voteId); // keep the old voteID
                vote.setPollId(pollId); // keep the same pollID
                poll.addVote(vote);
                user.addVote(vote);
                this.votes.put(vote.getId(), vote);
                return true;
            }
        }
        return false;
    }

    public boolean deletePoll(int id) {
        // check if poll exists
        if (this.polls.containsKey(id)) {
            Poll poll = this.polls.get(id);
            // remove poll connected to user
            User user = this.users.get(poll.getCreatorId());
            user.removePoll(poll);
            // remove the votes
            for (Vote vote : poll.getVotes()) {
                User votedUser = this.users.get(vote.getUserId());
                votedUser.removeVote(vote.getId());
                this.votes.remove(vote.getId());
            }
            // remove the poll from manager
            this.polls.remove(id);
            return true;
        }
        return false;
    }
}
