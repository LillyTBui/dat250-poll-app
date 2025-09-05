package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/polls")
@CrossOrigin
public class PollController {
    private final PollManager pollManager;

    public PollController(@Autowired PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping
    public ResponseEntity<Collection<Poll>> getPolls(){
        Collection<Poll> polls = this.pollManager.getPolls().values();
        return ResponseEntity.ok(polls);
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        if (this.pollManager.add(poll)) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(poll.getId())
                    .toUri();
            return ResponseEntity.created(location).body(poll); // successful POST
        }
        return ResponseEntity.badRequest().build(); // invalid request
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePoll(@PathVariable int id) {
        if (this.pollManager.deletePoll(id)) {
            return ResponseEntity.noContent().build(); // successful DELETE
        }
        return ResponseEntity.badRequest().build(); // invalid request
    }

    @PostMapping("/{pollId}/votes")
    public ResponseEntity<Vote> votePoll(@PathVariable int pollId, @RequestBody Vote vote) {
        vote.setPollId(pollId);
        if (this.pollManager.addVote(vote)) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{voteId}")
                    .buildAndExpand(vote.getId())
                    .toUri();
            return ResponseEntity.created(location).body(vote); // successful POST
        }
        return ResponseEntity.badRequest().build(); // invalid request
    }

    @PutMapping("/{pollId}/votes/{voteId}")
    public ResponseEntity<Vote> updateVote(@PathVariable int pollId, @PathVariable int voteId, @RequestBody Vote vote) {
         if (this.pollManager.updateVote(pollId, voteId, vote))  {
             return ResponseEntity.ok(vote);
         }
         return ResponseEntity.notFound().build();
    }
}
