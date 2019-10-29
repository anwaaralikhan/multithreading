
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
