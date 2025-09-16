package no.hvl.dat250.jpa.polls;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    @OneToMany
    private Set<Poll> created;

    /**
     * Creates a new User object with given username and email.
     * The id of a new user object gets determined by the database.
     */
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.created = new LinkedHashSet<>();
    }

    /**
     * Creates a new Poll object for this user
     * with the given poll question
     * and returns it.
     */
    public Poll createPoll(String question) {
        Poll newPoll = new Poll(question, this);
        this.created.add(newPoll);
        return newPoll;
    }

    /**
     * Creates a new Vote for a given VoteOption in a Poll
     * and returns the Vote as an object.
     */
    public Vote voteFor(VoteOption option) {
        return new Vote(option);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                '}';
    }
}
