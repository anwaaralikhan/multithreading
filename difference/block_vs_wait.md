### Blocked v Waiting

A thread goes to wait state once it calls `wait()` on an `Object`. This is called `Waiting State`. Once a thread reaches waiting state, it will need to wait till some other thread calls `notify()` or `notifyAll()` on the object.

Once this thread is notified, it will not be runnable. It might be that other threads are also notified (using `notifyAll()`) or the first thread has not finished his work, so it is still blocked till it gets its chance. This is called `Blocked State`. 

> A Blocked state will occur whenever a thread tries to acquire lock on object and some other thread is already holding the lock.

Once other threads have left and its this thread chance, it moves to Runnable state after that it is eligible pick up work based on JVM threading mechanism and moves to run state.


#### Answer 02

The difference is relatively simple.

In the `BLOCKED` state, a thread is about to enter a synchronized block, but there is another thread currently running inside a synchronized block on the same object. The first thread must then wait for the second thread to exit its block.

also Your thread is in runnable state of thread life cycle and trying to obtain object lock.


In the `WAITING` state, a thread is waiting for a signal from another thread. This happens typically by calling Object.wait(), or Thread.join(). The thread will then remain in this state until another thread calls Object.notify(), or dies.
also Your thread is in waiting state of thread life cycle and waiting for notify signal to come in runnable state of thread.


 - is it correct to say that only a thread itself can make it go into wait? Can Thread-B ever make Thread-A go to WAIT state? `YES`


Java8 doc for Thread.State says, `...These states are virtual machine states which do not reflect any operating system thread states.` In other words, the JVM does not care about the difference between a thread that is running Java code, a thread that is waiting for a system call to return, or a thread that is waiting for a time slice. Those are all just RUNNABLE as far as the JVM is concerned.
