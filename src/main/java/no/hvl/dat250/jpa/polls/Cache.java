package no.hvl.dat250.jpa.polls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.json.JsonSetParams;
import redis.clients.jedis.json.Path2;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
        connectToDb();

        // try to retrieve number of votes (first time will retrieve from db)
        List<Long> votes = retrieveVotes(1, jedis);
        System.out.println(votes);

        // try to retrieve again, this time it should be given from cache
        List<Long> votes2 = retrieveVotes(1, jedis);
        System.out.println(votes2);

        // user votes on a poll
        vote(jedis, 1, 1, 1);

        // try to retrieve again, this time it should be given from db because someone voted
        List<Long> votes3 = retrieveVotes(1, jedis);
        System.out.println(votes3);

        jedis.flushDB(); // delete all keys to start fresh
        jedis.close();
    }

    public static void connectToDb(){
        EntityManagerFactory emf = new PersistenceConfiguration("polls")
                .managedClass(Poll.class)
                .managedClass(User.class)
                .managedClass(Vote.class)
                .managedClass(VoteOption.class)
                .property(PersistenceConfiguration.JDBC_URL, "jdbc:h2:mem:polls")
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "drop-and-create")
                .property(PersistenceConfiguration.JDBC_USER, "sa")
                .property(PersistenceConfiguration.JDBC_PASSWORD, "")

                .createEntityManagerFactory();
        emf.runInTransaction(em -> {
            populate(em);
        });
        Cache.emf = emf;
    }

    public static List<Long> retrieveVotes(int id,  UnifiedJedis jedis) {
        List<Long> numbVotes = new ArrayList<>();
        // check if poll is cached
        Object json = jedis.jsonGet("Poll:" + id);
        if (json != null) {
            // get the number of votes per option
            List<Long> numbOptions = jedis.jsonArrLen("Poll:" + id, Path2.of("$.options"));
            for(int i = 0; i < numbOptions.getFirst(); i++) {
                Object nbVote = jedis.jsonGet("Poll:" + id, Path2.of("$.options[" + i + "].voteCount"));
                String nbVoteStr = nbVote.toString().substring(1, nbVote.toString().length() - 1);
                numbVotes.add(Long.valueOf(nbVoteStr));
            }
            System.out.println("Got it from cache 😀");
        } else {
            // need to connect to db and aggregate the numbers ourselves
            return retrieveVotesFromDb(id, jedis);
        }
        return numbVotes;
    }

    public static List<Long> retrieveVotesFromDb(int id, UnifiedJedis jedis) {
        List<Long> numbVotes = new ArrayList<>();
        emf.runInTransaction(em -> {
            String title = em.createQuery("SELECT p.question FROM Poll p where p.id = :id", String.class).setParameter("id", id).getSingleResult();
            List<Object[]> result = em.createQuery("SELECT o.caption, count(v) FROM VoteOption o LEFT JOIN Vote v on o = v.votesOn where o.poll.id = :id group by o.presentationOrder order by o.presentationOrder", Object[].class).setParameter("id", id).getResultList();
            // cache the results before returning the votes
            List<Option> options = new ArrayList<Option>();

            for(Object[] obj : result){
                String caption = obj[0].toString();
                int count = Integer.parseInt(obj[1].toString());
                options.add(new Option(caption, count));
                numbVotes.add((long) count);
            }
            PollObject pollObject = new PollObject(Integer.toString(id), title, options);
            // create a denormalized presentation of poll
            cachePoll(pollObject, "Poll:" + id, jedis, 30);
        });
        System.out.println("Got it from db 📀");
        return numbVotes;
    }

    public static void vote(UnifiedJedis jedis, int userId, int pollId, int presentationOrder){
        emf.runInTransaction(em -> {
            User user = em.find(User.class, userId);
            VoteOption voteOption = em.createQuery("SELECT o FROM VoteOption o where o.poll.id = :pollId and o.presentationOrder = :presOrder", VoteOption.class).setParameter("pollId", pollId).setParameter("presOrder", presentationOrder).getSingleResult();
            // update the db
            em.persist(user.voteFor(voteOption));
            // invalidate the poll entry in the cache
            jedis.del("Poll:" + pollId);
        });
    }

    public static void cachePoll(PollObject poll, String pollName,  UnifiedJedis jedis, int ttl) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonPoll = mapper.writeValueAsString(poll);
            jedis.jsonSet(pollName, jsonPoll, JsonSetParams.jsonSetParams());

            // set time to live of Poll in the cache
            jedis.expire(pollName, ttl);
        } catch(JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void populate(EntityManager em) {
        User alice = new User("alice", "alice@online.com");
        User bob = new User("bob", "bob@bob.home");
        User eve = new User("eve", "eve@mail.org");
        em.persist(alice);
        em.persist(bob);
        em.persist(eve);
        Poll poll = alice.createPoll("Pineapple on Pizza?");
        VoteOption yum = poll.addVoteOption("Yes, yammy!");
        VoteOption no = poll.addVoteOption("Mamma mia, nooooo!");
        VoteOption dontcare = poll.addVoteOption("I do not really care ...");
        em.persist(poll);
        em.persist(alice.voteFor(yum));
        em.persist(bob.voteFor(yum));
        em.persist(eve.voteFor(dontcare));
    }
}
