package org.dat250.poll.domains;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteOption {
    private String caption;
    private Integer presentationOrder;
}
