package org.dat250.poll;

import org.dat250.poll.domains.User;
import org.dat250.poll.domains.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {
    private final PollManager pollManager;

    public UserController(@Autowired PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
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
    public ResponseEntity<Map<Integer, Vote>> getVotes(@PathVariable int id){
        User user = this.pollManager.getUsers().get(id);
        return ResponseEntity.ok(user.getVotes());
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (this.pollManager.add(user)) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();
            return ResponseEntity.created(location).body(user); // successful POST
        };
        return  ResponseEntity.badRequest().build(); // Invalid request
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        if (this.pollManager.removeUser(id)) {
            return ResponseEntity.noContent().build(); // successful DELETE
        }
        return ResponseEntity.badRequest().build(); // Invalid request
    }
}
