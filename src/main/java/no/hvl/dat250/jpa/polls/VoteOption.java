package no.hvl.dat250.jpa.polls;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VoteOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caption;
    private int presentationOrder;
    @ManyToOne
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
