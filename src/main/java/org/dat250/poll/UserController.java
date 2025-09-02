package org.dat250.poll;

import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final PollManager pollManager;

    public UserController(@Autowired PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        if (this.pollManager.getUsers().containsKey(id)) {
            return ResponseEntity.ok(this.pollManager.getUsers().get(id));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<Collection<User>> getUsers(){
        Collection<User> users = this.pollManager.getUsers().values();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/votes")
    public ResponseEntity<Collection<Vote>> getVotes(@PathVariable String id){
        User user = this.pollManager.getUsers().get(id);
        return ResponseEntity.ok(user.getVotes());
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        this.pollManager.add(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (this.pollManager.getUsers().containsKey(id)) {
            this.pollManager.getUsers().remove(id);
            return ResponseEntity.noContent().build(); // successful DELETE
        }
        return ResponseEntity.badRequest().build(); // Invalid request
    }

}
