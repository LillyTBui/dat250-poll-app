package no.hvl.dat250.jpa.polls;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil ;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<VoteOption> options;
    @ManyToOne
    private User createdBy;

    public Poll(String question, User createdBy) {
        this.question = question;
        this.options = new LinkedHashSet<>();
        this.createdBy = createdBy;
    }

    /**
     *
     * Adds a new option to this Poll and returns the respective
     * VoteOption object with the given caption.
     * The value of the presentationOrder field gets determined
     * by the size of the currently existing VoteOptions for this Poll.
     * I.e. the first added VoteOption has presentationOrder=0, the secondly
     * registered VoteOption has presentationOrder=1 and so on.
     */
    public VoteOption addVoteOption(String caption) {
        int presentationOrder = this.options == null ? 0 : this.options.size();
        VoteOption voteOption = new VoteOption(caption, presentationOrder, this);
        this.options.add(voteOption);
        return voteOption;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", publishedAt=" + publishedAt +
                ", validUntil=" + validUntil +
                ", options=" + options +
                '}';
    }
}
