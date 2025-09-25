package no.hvl.dat250.jpa.polls;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    private String caption;
    private int voteCount;
}
