package org.dat250.poll.domains;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PollManager {
    private Map<Poll, User> polls = new HashMap<>();
    private Map<Poll, Vote> votes = new HashMap<>();
    private Map<Vote, User> userVotes = new HashMap<>();

    public void createPoll(Poll poll, User user) {
        polls.put(poll, user);
    }
}
