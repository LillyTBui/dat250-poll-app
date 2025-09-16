package no.hvl.dat250.jpa.polls;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant publishedAt;
    @ManyToOne
    private VoteOption votesOn;

    public Vote(VoteOption votesOn) {
        this.votesOn = votesOn;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", publishedAt=" + publishedAt +
                ", votesOn=" + votesOn +
                '}';
    }
}
