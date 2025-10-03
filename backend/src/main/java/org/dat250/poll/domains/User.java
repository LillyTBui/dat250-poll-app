package org.dat250.poll.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.dat250.poll.dto.PollDto;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Poll> created;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.created = new LinkedHashSet<>();
    }

    public void addPoll(Poll newPoll) {
        newPoll.setCreatedBy(this);
        this.created.add(newPoll);
    }

    /**
     * Creates a new Vote for a given VoteOption in a Poll
     * and returns the Vote as an object.
     */
    public Vote voteFor(VoteOption option, Instant votePublished) {
        return new Vote(option, votePublished);
    }



    /*public void addVote(Vote vote) {
        this.votes.put(vote.getId(), vote);
    }

    public void addPoll(Poll poll) {
        this.polls.add(poll);
    }

    public void removePoll(Poll poll) {
        this.polls.remove(poll);
    }

    public void removeVote(int voteId) {
        this.votes.remove(voteId);
    }*/
}
