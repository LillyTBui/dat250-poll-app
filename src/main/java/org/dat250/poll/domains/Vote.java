package org.dat250.poll.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class Vote {
    private int id;
    private int pollId;
    private int userId;
    private VoteOption voteOption;
    private Instant publishedAt;
    private boolean isValid;
}
