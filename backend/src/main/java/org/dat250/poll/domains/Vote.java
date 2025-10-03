package org.dat250.poll.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant publishedAt;
    @ManyToOne
    private VoteOption votesOn;

    public Vote(VoteOption votesOn, Instant publishedAt) {
        this.votesOn = votesOn;
        this.publishedAt = publishedAt;
    }
}
