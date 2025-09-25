package no.hvl.dat250.jpa.polls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.json.JsonSetParams;
import redis.clients.jedis.json.Path2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");

        List<Option> options = new ArrayList<Option>();
        options.add(new Option("Yes, yammy!", 269));
        options.add(new Option("Mamma mia, nooooo!", 268));
        options.add(new Option("I do not really care ...", 42));

        PollObject poll = new PollObject("03ebcb7b-bd69-440b-924e-f5b7d664af7b", "Pineapple on Pizza?", options);

        try{
            ObjectMapper mapper = new ObjectMapper();
            String jsonPoll = mapper.writeValueAsString(poll);
            String res1 = jedis.jsonSet("Poll", jsonPoll, JsonSetParams.jsonSetParams());
            System.out.println(res1);

            // get the json object
            Object json = jedis.jsonGet("Poll");
            System.out.println(json);

            // increment vote of the first option
            Object res2 = jedis.jsonNumIncrBy("Poll", Path2.of("$.options[0].voteCount"), 1);
            System.out.println(res2);

            // get the json object again and see that voteCount has incremented
            Object json2 = jedis.jsonGet("Poll");
            System.out.println(json2);
        } catch(JsonProcessingException e) {
            System.err.println(e.getMessage());
        }
        jedis.close();
    }
}