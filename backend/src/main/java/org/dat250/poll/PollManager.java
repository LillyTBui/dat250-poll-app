package org.dat250.poll;

import lombok.Data;
import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.dat250.poll.domains.VoteOption;
import org.dat250.poll.dto.PollDto;
import org.dat250.poll.dto.VoteDto;
import org.dat250.poll.dto.VoteOptionDto;
import org.dat250.poll.messaging.Consumer;
import org.dat250.poll.messaging.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Component
@Data
public class PollManager {
    private Producer producer;
    private Consumer consumer;
    private Repository repository;

    public PollManager(@Autowired Repository repository) {
        this.repository = repository;
        this.producer = new Producer();
        this.consumer = new Consumer();
    }

    // add a new user
    public boolean add(User user){
        // check if user has the mandatory fields
        if (!user.getUsername().isEmpty() && !user.getEmail().isEmpty()) {
            this.repository.save(user);
            return true;
        }
        return false;
    }

    // user creates a new poll
    public Poll createPoll(PollDto poll) throws Exception {
        // check if user exists
        Long userId = poll.getCreatorId();
        User user = this.repository.findUserById(userId);
        if (user == null) {
            throw new Exception("User not found");
        }

        // a poll must have a question
        if (poll.getQuestion() == null || poll.getQuestion().isEmpty()){
            throw new Exception("Question is empty");
        }
        // a poll must have at least 2 voting options
        if (poll.getVoteOptions() == null || poll.getVoteOptions().size() < 2){
            throw new Exception("Vote options cannot be empty");
        }

        // if publishedAt and validUntil are not set by user, then set default values
        if (poll.getPublishedAt() == null){
            poll.setPublishedAt(Instant.now());
        }
        if (poll.getValidUntil() == null){
            poll.setValidUntil(poll.getPublishedAt().plus(Duration.ofDays(7)));
        }
        // create new poll
        Poll newPoll = new Poll(poll.getQuestion(), user, poll.getPublishedAt(), poll.getValidUntil(), poll.isVisibility());
        // add voteOptions to the new poll
        for (VoteOptionDto optionDto : poll.getVoteOptions()){
            newPoll.addVoteOption(optionDto.getCaption());
        }

        // add the poll to the user's list
        user.addPoll(newPoll);

        // add newly created poll to database
        this.repository.save(newPoll);

        // register a topic with the same name
        this.producer.registerTopic("Poll:" + newPoll.getId());
        // subscribe to the topic
        this.consumer.subscribeToTopic("Poll:" + newPoll.getId());

        return newPoll;
    }

    // user votes on poll
    public Vote addVote(VoteDto voteDto) throws Exception {
        // check if poll exists
        Poll poll = this.repository.findPollById(voteDto.getPollId());
        Instant votePublished = Instant.now();
        if (poll != null) {
            // check if user exists
            User user = this.repository.findUserById(voteDto.getUserId());
            if (user != null){
                VoteOption voteOption = this.repository.findVoteOptionById(voteDto.getVoteOption().getCaption(), poll.getId());
                Vote vote = user.voteFor(voteOption, votePublished);

                // save vote to db
                this.repository.userVote(vote);

                // Publish message
                String message = "User with username = `" + user.getUsername() + "` voted on poll with id = " + poll.getId();
                this.producer.publishMessage("Poll:" + poll.getId(), message, "vote.created");

                return vote;
            } else {
                // if user do not exist then there is an anonymous vote
                VoteOption voteOption = this.repository.findVoteOptionById(voteDto.getVoteOption().getCaption(), poll.getId());
                Vote vote = new Vote(voteOption, votePublished);

                // Publish message
                String message = "anonymous voted on poll with id = " + poll.getId();
                this.producer.publishMessage("Poll:" + poll.getId(), message, "vote.created");

                return vote;
                }
            }
        return null;
        }
    }