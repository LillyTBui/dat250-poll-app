package org.dat250.poll;

import org.dat250.poll.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder ucb) {
        this.pollManager.add(user);
        URI location = ucb.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }
}
