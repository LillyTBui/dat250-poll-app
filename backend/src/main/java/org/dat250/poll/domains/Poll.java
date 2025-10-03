package org.dat250.poll.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private Instant publishedAt;
    private Instant validUntil;
    private boolean visibility;
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<VoteOption> options;
    @ManyToOne
    @JsonBackReference
    private User createdBy;

    public Poll(String question, User createdBy, Instant publishedAt, Instant validUntil, boolean visibility) {
        this.question = question;
        this.createdBy = createdBy;
        this.publishedAt = publishedAt;
        this.validUntil = validUntil;
        this.visibility = visibility;
        this.options = new LinkedHashSet<>();
    }

    public void addVoteOption(String caption) {
        int presentationOrder = this.options == null ? 0 : this.options.size();
        VoteOption voteOption = new VoteOption(caption, presentationOrder, this);
        this.options.add(voteOption);
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", publishedAt=" + publishedAt +
                ", validUntil=" + validUntil +
                '}';
    }
}
