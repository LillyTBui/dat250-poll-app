package org.dat250.poll.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.*;

@Data
@AllArgsConstructor
public class Poll {
    private int id;
    private String question;
    private final Set<Vote> votes = new HashSet<>();
    private List<VoteOption> voteOptions = new ArrayList<>();
    private int creatorId;
    private boolean visibility;
    private Instant publishedAt;
    private Instant validUntil;

    public void removeVote(Vote vote) {
        this.votes.remove(vote);
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }
}
