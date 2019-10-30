**WAIT** - I'm waiting to be given some work, so I'm idle right now.

**BLOCKED** - I'm busy trying to get work done but another thread is standing in my way, so I'm idle right now.

The important difference between the `blocked` and `wait` states is the impact on the scheduler. 

- A thread in a blocked state is part of a waitset contending for a lock; that thread still counts as something the scheduler needs to service, possibly getting factored into the scheduler's decisions about how much time to give running threads.

- Once a thread is in the wait state the stress it puts on the system is minimized, and the scheduler doesn't have to worry about it. It goes dormant until it receives a notification. Except for the fact that it keeps an OS thread occupied it is entirely out of play.

This is why using notifyAll is less than ideal, it causes a bunch of threads that were previously happily dormant putting no load on the system to get woken up, where most of them will block until they can acquire the lock, find the condition they are waiting for is not true, and go back to waiting. `It would be preferable to notify only those threads that have a chance of making progress`.

> (Using ReentrantLock instead of intrinsic locks allows you to have multiple conditions for one lock, so that you can make sure the notified thread is one that's waiting on a particular condition, avoiding the lost-notification bug in the case of a thread getting notified for something it can't act on.)


### Answer 01

Once a thread gets awoken from a notify (or even from a spurious wakeup) it needs to relock the monitor of the object on which it was waiting. This is the BLOCKED state.

> Thread state for a thread blocked waiting for a monitor lock. A thread in the blocked state is waiting for a monitor lock to enter a synchronized block/method or reenter a synchronized block/method after calling Object.wait.

This is explained in the javadoc of `Object#notify()`:

> The awakened thread will not be able to proceed until the current thread relinquishes the lock on this object.

and `Object#wait()`

The thread then waits until it can re-obtain ownership of the monitor and resumes execution.

![Thread States](https://github.com/anwaaralikhan/multithreading/blob/master/usecases/pictures/thread_states.png)

### Answer 02

A thread is in **WAITING** state goes in **BLOCK** state, until it acquires monitor by notify and become **RUNNABLE**.

Same applies for **TIMEDWAITING**, It goes in **BLOCK** state, if monitor is hold by some other thread,even though specified time has passed.(your diagram need to be corrected)




https://stackoverflow.com/questions/35938395/difference-between-thread-state-blocked-and-waiting?noredirect=1&lq=1
https://stackoverflow.com/questions/15680422/difference-between-wait-and-blocked-thread-states
