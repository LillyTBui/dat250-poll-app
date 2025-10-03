package org.dat250.poll;

import org.dat250.poll.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {
    private final Repository repository;
    private final PollManager pollManager;

    public UserController(@Autowired Repository repository, @Autowired PollManager pollManager) {
        this.repository = repository;
        this.pollManager = pollManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = this.repository.findUserById(id);
        if (user != null){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
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
}
