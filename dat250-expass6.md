## Short report

To do this assignment I created a branch out of `expass3`. I started by creating a `Producer` and `Consumer` class. The
producer class register a topic and publishes a given message, and the consumer subscribe to a topic. Since the task stated that the
`pollApp` should listen for **voting events**, the consumer only listens/subscribe for this. To test the connection I first
called the register and subscribe method after creating a new `Poll` in `PollManager`. Then, when a user voted on a poll I
called the publish method and sent a message to be printed out in the terminal.

Since I did `expass4` and `expass5` from scratch, I had to implement the JPA so 
that entities can be saved to the database. To do this I created `JpaConfig` to connect to the h2 database and 
`Repository` to communicate with the database. They were annotated as beans to make sure they are only created once. 
Then I removed most of the methods inside `PollController` and `UserController` so that only the following actions can be made:
- create new user
- get a single user
- user creates a new poll
- get all polls
- votes on poll

This was the minimum methods I thought I needed to do the assignment. I still kept the `PollManager` to handle logic like 
"check if user exists", but instead of storing data in the PollManager, it uses the repository to communicate with the database.
I did not want to change my old payload a lot, so I used DTOs. This was because I wanted to keep
using ids instead of full objects inside the payload. When making queries to the database, I had problems with the 
`EntityManagerFactory` and `runInTransaction` because it returns void. So I used `AtomicReference` to be able to 
return an object. I also had problems with "no session", which was solved including "left join fetch" inside the queries 
and using `@JsonManagedReference` and `@JsonBackReference` to manage bidirectional relationships between entities.

Below are the results in the terminal after:
1) User creates a new Poll
2) User votes on the same poll
3) Anonymous votes 
on the same poll.

```
topic created
Subscribing to topic Poll:1
[x] Sent 'vote.created':'User with username = `user` voted on poll with id = 1'
[x] Received 'vote.created':'User with username = `user` voted on poll with id = 1'
[x] Sent 'vote.created':'anonymous voted on poll with id = 1'
[x] Received 'vote.created':'anonymous voted on poll with id = 1'
```

- Link to Consumer: 
- Link to Producer: 
- Link to UserController: 
- Link to PollController: 
- Link to PollManager:
- Link to Repository:
- Link to JpaConfig:

### Pending issues
When doing this assignment I did not make the solution work with the testcases and the frontend.
This is because I was focusing more on making the messaging and database part work.