# Short report

At the beginning I thought it was unclear about how much we should do in this exercise. Should I follow the given domain model in the assignment or the linked problem description? This confusion made me go back and fourth a lot of times; writing code, deleting it, maybe wrote the same code again. However, after going to the group session I got more clarity of the scope of the task and that we are free to solve the task however we want to.

### Technical problems

So I used ids instead of storing the full objects when it comes to saving references. It felt more natural for me to do this this way as I do not think all the information inside each object is relevant for the other objects (ex. user's password should not be visible for Poll). In addition I avoided the association cycles which was a plus.

When I started on the assignment I wrote as simple as possible and expanded the code when I added more functionality. In the group session I got a tip to use Lombok to reduce boilerplate code which led to a quite a lot of refactoring of my old code to make it work again. 

I also had a lot of problems making the tests work. At the beginning I was testing one method at the time and checked if they worked. But when I tested the whole test classes they did not work anymore because they affected each other. I tried using `@BeforeEach`, `@BeforeAll` and similar annotations but it did not work ( probably because I used it wrong ). So I ended up using `@TestMethodOrder` which is probably not the best way to do it.

### Pending issues

Pending issue I have now is that I don't show the most recent vote for user 2, I show all the votes instead. I tried to use `@RequestParam` and `@RequestParam(required=false)` but all my tests failed so I gave up on it for now. In general I am lacking some functionalities from the task decription and I have not tested thoroughly for all edge cases in the existing handler/controller methods. 
