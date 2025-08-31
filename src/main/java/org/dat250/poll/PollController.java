package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/polls")
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
        // check if user exists
        if (this.pollManager.getUsers().containsKey(poll.getCreatorId())) {
            // create new poll
            this.pollManager.add(poll);
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
    public ResponseEntity<Void> deletePoll(@PathVariable String id) {
        if (this.pollManager.getPolls().containsKey(id)) {
            // remove poll connected to user
            this.pollManager.getUserPolls().remove(id);
            // remove the poll
            this.pollManager.getPolls().remove(id);
            return ResponseEntity.noContent().build(); // successful DELETE
        }
        return ResponseEntity.badRequest().build(); // invalid request
    }
}
