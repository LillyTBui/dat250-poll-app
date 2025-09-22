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

