package org.dat250.poll;

import org.dat250.poll.domains.Poll;
import org.dat250.poll.domains.Vote;
import org.dat250.poll.dto.PollDto;
import org.dat250.poll.dto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/polls")
@CrossOrigin
public class PollController {
    private final Repository repository;
    private final PollManager pollManager;

    public PollController(@Autowired Repository repository, @Autowired PollManager pollManager) {
        this.repository = repository;
        this.pollManager = pollManager;
    }

    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody PollDto pollDto) throws Exception {
        try {
            Poll poll = this.pollManager.createPoll(pollDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(poll.getId())
                    .toUri();
            return ResponseEntity.created(location).body(poll); // successful POST
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build(); // invalid request
        }
    }

    @PostMapping("/{pollId}/votes")
    public ResponseEntity<Vote> votePoll(@PathVariable Long pollId, @RequestBody VoteDto voteDto) throws Exception {
        voteDto.setPollId(pollId);
        Vote vote = this.pollManager.addVote(voteDto);
        if (vote != null) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{voteId}")
                    .buildAndExpand(vote.getId())
                    .toUri();
            return ResponseEntity.created(location).body(vote); // successful POST*/
        }
        return ResponseEntity.badRequest().build(); // invalid request
    }

    @GetMapping
    public ResponseEntity<List<Poll>> getPolls(){
        List<Poll> polls = this.repository.findAllPolls();
        return ResponseEntity.ok(polls);
    }
}
