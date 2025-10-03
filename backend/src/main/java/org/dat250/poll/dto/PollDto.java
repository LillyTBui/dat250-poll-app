package org.dat250.poll.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class PollDto {
    public Long creatorId;
    public String question;
    public Set<VoteOptionDto> voteOptions;
    public boolean visibility;
    public Instant publishedAt;
    public Instant validUntil;
}
