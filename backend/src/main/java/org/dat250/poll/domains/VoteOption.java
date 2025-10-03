package org.dat250.poll.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    private Integer presentationOrder;
    @ManyToOne
    @JoinColumn(name = "pollId")
    @JsonBackReference
    private Poll poll;

    public VoteOption(String caption, int presentationOrder,  Poll poll) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
        this.poll = poll;
    }

    @Override
    public String toString() {
        return "VoteOption{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", presentationOrder=" + presentationOrder +
                '}';
    }
}
