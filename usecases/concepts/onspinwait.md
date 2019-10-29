
## SpinWait

### While learning Java 9 I came across a new method of Thread class, called onSpinWait​. According to javadocs, this method is used for this:

> Indicates that the caller is momentarily unable to progress, until the occurrence of one or more actions on the part of other activities.


#### Example 1
Let's understand from a example, which does not completely highlight its purpose but will help in understanding the concept.
let's say you have a program that checks for new emails and notifies the user:

```
while(true) {
    while(!newEmailArrived()) {
    }
    makeNotification();
}
```

This piece of code will execute millions of times a seconds; spinning over and over, using precious of electricity and CPU power. A common way of doing this would be to wait a few seconds on each iteration.

```
while(true) {
    while(!newEmailArrived()) {
        try {
            Thread.sleep(5000);
        } catch(InterruptedException e) {
        }
    }
    makeNotification();
}
```
This does a very good job. But in cases where you have to work instantly a sleep may be out of question.
Java 9 tries to solve this issue by introducing this new method:



## Difference Between

From the documentation of Thread#onSpinWait:
> The runtime may take action to improve the performance of invoking spin-wait loop constructions.

Thread#sleep does not do this, but rather releases the processor to another runnable thread until its sleep time has elapsed.

If I were you, I would redesign your system to use interrupts (events) rather than polling (busy waiting), as that would result in a better performance boost than either Thread#sleep or Thread#onSpinWait.



https://stackoverflow.com/questions/51215310/thread-sleep-vs-thread-onspinwait

```
while(true) {
    while(!newEmailArrived()) {
        Thread.onSpinWait();
    }
    makeNotification();
}
```
This will work exactly the same as without the method call, but the system is free to lower the process priority; slowing the cycle or reduce electricity on this loop when its resources are needed for other more important things.


#### Example 2

For a real-world example, say you wanted to implement asynchronous logging, where threads that want to log something, don't want to wait for their log message to get "published" (say written to a file), just so long as it eventually does (because they've got real work to do.)

```
Producer(s):
concurrentQueue.push("Log my message")
```
And say, you decide on having a dedicated consumer thread, that is solely responsible for actually writing log messages to a file:

```
(Single)Consumer
while (concurrentQueue.isEmpty())
{
    //what should I do?
}
writeToFile(concurrentQueue.popHead());
```
//loop
The issue is what to do in inside the while block? Java has not provided ideal solutions: you could do a Thread.sleep(), but for how long and that's heavyweight; or a Thread.yield(), but that's unspecified, or you could use a lock or mutex*, but that's often too heavyweight and slows the producers down as well (and vitiates the stated purpose of asynchronous logging).

What you really want is to say to the runtime, "I anticipate that I won't be waiting too long, but I'd like to minimize any overhead in waiting/negative effects on other threads". That's where Thread.onSpinWait() comes in.

As a response above indicated, on platforms that support it (like x86), onSpinWait() gets intrinsified into a PAUSE instruction, which will give you the benefits that you want. So:

```
(Single)Consumer
while (concurrentQueue.isEmpty())
{
    Thread.onSpinWait();
}
writeToFile(concurrentQueue.popHead());
```
//loop
It's been shown empirically that this can improve latency of "busy-waiting" style loops.

I also want to clarify, that it is not just useful in implementing "spin-locks" (although it's definitely useful in such a circumstance); the code above does not require a lock (spin or otherwise) of any kind.

If you want to get into the weeds, you can't do better than Intel's specs

*For clarity, the JVM is incredibly smart in attempting to minimize the cost of mutexes, and will use lightweight locks initially, but that's another discussion.





When blocking a thread, there are a few strategies to choose from: spin, wait() / notify(), or a combination of both. Pure spinning on a variable is a very low latency strategy but it can starve other threads that are contending for CPU time. On the other hand, wait() / notify() will free up the CPU for other threads but can cost thousands of CPU cycles in latency when descheduling/scheduling threads.

So how can we avoid pure spinning as well as the overhead associated with descheduling and scheduling the blocked thread?

Thread.yield() is a hint to the thread scheduler to give up its time slice if another thread with equal or higher priority is ready. This avoids pure spinning but doesn't avoid the overhead of rescheduling the thread.

The latest addition is Thread.onSpinWait() which inserts architecture-specific instructions to hint the processor that a thread is in a spin loop. On x86, this is probably the PAUSE instruction, on aarch64, this is the YIELD instruction.

What's the use of these instructions? In a pure spin loop, the processor will speculatively execute the loop over and over again, filling up the pipeline. When the variable the thread is spinning on finally changes, all that speculative work will be thrown out due to memory order violation. What a waste!

A hint to the processor could prevent the pipeline from speculatively executing the spin loop until prior memory instructions are committed. In the context of SMT (hyperthreading), this is useful as the pipeline will be freed up for other hardware threads.

shareimprove this answer
edited May 9 at 13:16
answered May 9 at 11:48

Eric
59733 silver badges88 bronze badges
3
So onSpinWait() is the right thing if we expect the other thread to already run on a different CPU (core) fulfilling the condition whereas yield() is the right thing if we expect the other thread not having CPU time. Unfortunately, we can’t know, so the example code shown in the question uses some random based heuristic to decide when to invoke which method. 

https://stackoverflow.com/questions/56056711/threadyield-vs-threadonspinwait
