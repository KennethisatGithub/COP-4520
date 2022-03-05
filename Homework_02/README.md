# COP-4520
##Assignment 2

## Minotaur’s Birthday Party
Efficiency: The program chooses the thread randomly through one of two choices. Either let the program automatically use a lock that's part of the synchronized function, which automatically randomly selects threads most efficiently. With this method the program executes within 60 ms to 120 ms. The other method is to use the Random class to randomly select threads to be used within the program. This typically took longer, between 1000 ms to 2000 ms.

Correctness: Used print statements along with checking without prints utilizing Thread.sleep() to check for the approximate run time based on the thread count.

## Minotaur’s Crystal Vase
### Strategy 1
* This strategy is good at the fact that we will have no downtime of a guest waiting to get in. But the problem is that we may dissuade other guests from trying to enter the room. Even then, sometimes some guests may be able to go several times, while other guests may never have the chance, even if they have a bigger need or desire to go.

### Strategy 2
* This strategy is good at using the guests' time efficiently, allowing the guests to explore other activities in between while checking to see if the room is available. The problem is that it can sometimes either create moments where none of the guests are aware if the room is available, or it creates a bottleneck of a bunch of guests needing to check to see if the room is available.

### Strategy 3
* This strategy seems to strike a good balance between having the room consistently have people entering it through a queue while also having the potential to have guests do other things or join the queue if they wished. Sometimes this can cause other problems such as the queue could create a really long line. So sometimes if someone needs to get back in line again quickly, depending on the queue size, it could be a while.

Efficiency: The Vase Room is currently setup to have an open time of 3 seconds. But everyone in the queue will still be processed. We're using an Array-Based Queue Lock to process the program and allow each thread to be able to queue up to access the Vase Room. The efficiency is based off of the critical area depending on how much work is done, along with the Vase Viewing time.

Correctness: We can check to see if the program is processing each thread one at a time by using the Thread.sleep() function. With the number of threads in the program, we're able to see that every thread is able to run in the queue by also using a hashset to check if they've all been processed.