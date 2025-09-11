## Short report

### Technical problems
During the completion of the tutorial I had to change a little bit on 
the previous assignment in order to make it work with the frontend code.
Originally I added the constraint that a user cannot vote on his own poll, 
but this meant I must have some sort of way of knowing which user is logged
in. I did not implement any login handler or looked at localStorage, so I
removed this constraint in `backend:PollManager` instead. But the user
can still only vote maximum one time at the same poll. So right now the
login page do not do anything. I also added the possibility to vote as an
anonymous vote which get the default value of 0 for id. You can vote 
anonymously on the `Polls` page if you have created some poll(s) first
(I did not make any dummy data).

Additionally I had some presentation problems on the frontend side, where
the voteOptions were displayed in the wrong order inside `frontend:Poll`.
This was due to the use of Set when storing the voteOptions `backend:Poll` 
so I changed it to List. I also got duplication errors for presentationOrder
in `VoteOption` because at the beginning I used the array length to set the 
order, but this caused problems when I added and removed several options. This
was fixed by manually fixing the orders before sending the request in `frontend:CreatePoll`
but this is probably a bad solution.

Another problem was incorrect removal of old vote when updating the user's vote. 
This was because the vote I used in remove(vote) in `backend:poll` where the 
updated vote and not the old one. 

- Link to component you can create user: `Register` - https://github.com/LillyTBui/dat250-poll-app/blob/frontend/frontend/src/components/Register.tsx
- Link to component where user can create poll: `CreatePoll` - https://github.com/LillyTBui/dat250-poll-app/blob/frontend/frontend/src/components/polls/CreatePoll.tsx
- Link to component where vote on created poll: `Poll` - https://github.com/LillyTBui/dat250-poll-app/blob/frontend/frontend/src/components/polls/Poll.tsx

> The whole assignment is inside the branch called `frontend` - https://github.com/LillyTBui/dat250-poll-app/tree/frontend.
> I did not merge the changes because the previous assignment has not been graded yet.

### Pending issue
Even if the frontend code works fine in local development, the CI
github actions fails because of typescript and dependency errors. 
There is also an error if you try to refresh the `Polls` page after
you have created some poll(s) when you run the server with the
static files. 



