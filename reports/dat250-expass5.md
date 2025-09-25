## Short report

### Use Case 1
- To see all the possible commands for Set datatype I used `HELP @set` in the command line.
- To see that no user is logged in, I thought the commands `SCARD` or `SMEMBERS` are suitable.
- To simulate that an user logs in I used the command `SADD log "user"`, and the command 
  `SREM log "user"` to log out.
- After performing the operations in the task, I check if the set called `log` gives 
  - integer 2 for `SCARD`, or
  - "bob" and "eve" for `SMEMBERS`

### Use Case 2
- I thought it was easier to look at the JSON type than using the Hash datatype, 
  because the example looks like a JSON object.
- To create the complex object I used this command:

```
JSON.SET Poll $ '{"id": "03ebcb7b-bd69-440b-924e-f5b7d664af7b", "title": "Pineapple on Pizza?", "options": [{"caption": "Yes, yammy!","voteCount": 269 },{"caption": "Mamma mia, nooooo!","voteCount": 268 },{"caption": "I do not really care ...","voteCount": 42 }]}'
```
- To get the first option in the created object I used the command `JSON.GET Poll $.options[0]`
- To increment the vote of this option I used the command `JSON.NUMINCRBY Poll $.options[0].voteCount 1`

### Using the Java library task

I made two new classes: `PollObject` and `Option` that fits the object I needed to represent.
I then created a new PollObject with the given values from the task. The problem encountered
was that I tried to set this object directly without first converting it into a json string. For
this I used Jackson.

Link to the test code: ...

### Implementing a Cache task

- I used the JSON type to store the denormalized `Poll` presentation, identical to the previous exercice.
- I followed the same logic as explained in the task, and took inspiration from `PollTest` from the last exercice
  assignment to connect and populate the database.
- To fetch the votes from the database I translated the query in the task description to `createQuery`, 
  but I replaced presentationOrder with caption. I did not see the use case for presentationOrder because the object
  from `Use Case 2:Represent complex information` did not have it.
- To invalidate a `Poll` entry in the cache in the event of a vote, I deleted the key which would result in 
  fetching results from the database the next time you tried to retrieve votes from the same poll.
- Before closing jedis I deleted the keys to start fresh when I ran the code again
- To test the code I tried to retrieve votes several times to see if:
  - the first time would result in retrieving data from the database and caching the results
  - the second time would result in retrieving data from the cache
  - user votes on the poll, then the third time would again retrieve data from the database and the count increased
    for that voteOption

Link to cache code: ...
