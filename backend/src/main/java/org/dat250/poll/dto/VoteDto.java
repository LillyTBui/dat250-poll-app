package org.dat250.poll.dto;

import lombok.Data;

@Data
public class VoteDto {
    private Long userId;
    private Long pollId;
    private VoteOptionDto voteOption;
}
