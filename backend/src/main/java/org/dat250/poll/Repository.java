package org.dat250.poll;

import jakarta.persistence.EntityManagerFactory;
import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.dat250.poll.domains.VoteOption;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Repository {
    private EntityManagerFactory emf;

    public Repository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(User user){
        emf.runInTransaction(em -> {
            em.persist(user);
        });
    }

    public void userVote(Vote vote){
        emf.runInTransaction(em -> {
            em.persist(vote);
        });
    }

    public User findUserById(Long id){
        final AtomicReference<User> user = new AtomicReference<>();
        emf.runInTransaction(em -> {
            user.set(em.createQuery("select u from User u left join fetch u.created p left join fetch p.options where u.id = :id", User.class)
                            .setParameter("id", id)
                            .getSingleResultOrNull()
            );
        });
        return user.get();
    }

    public void save(Poll poll){
        emf.runInTransaction(em -> {
            em.persist(poll);
        });
    }

    public Poll findPollById(Long id){
        final AtomicReference<Poll> poll = new AtomicReference<>();
        emf.runInTransaction(em -> {
            poll.set(em.createQuery("select p from Poll p left join fetch p.options where p.id = :id", Poll.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull());
        });
        return poll.get();
    }

    public List<Poll> findAllPolls(){
        final AtomicReference<List<Poll>> polls = new AtomicReference<>();
        emf.runInTransaction(em -> {
            polls.set(em.createQuery("select p from Poll p left join fetch p.options left join fetch p.createdBy", Poll.class).getResultList());
        });
        return polls.get();
    }

    public VoteOption findVoteOptionById(String caption, Long pollId){
        final AtomicReference<VoteOption> voteOption = new AtomicReference<>();
        emf.runInTransaction(em -> {
            voteOption.set(em.createQuery("select v from VoteOption v join v.poll p where v.caption = :caption and p.id = :pollId", VoteOption.class)
            .setParameter("caption", caption).setParameter("pollId", pollId).getSingleResultOrNull());
        });
        return voteOption.get();
    }
}
