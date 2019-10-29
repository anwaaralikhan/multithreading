### Blocked v Waiting

A thread goes to wait state once it calls wait() on an Object. This is called Waiting State. Once a thread reaches waiting state, it will need to wait till some other thread calls notify() or notifyAll() on the object.

Once this thread is notified, it will not be runnable. It might be that other threads are also notified (using notifyAll()) or the first thread has not finished his work, so it is still blocked till it gets its chance. This is called Blocked State. A Blocked state will occur whenever a thread tries to acquire lock on object and some other thread is already holding the lock.

Once other threads have left and its this thread chance, it moves to Runnable state after that it is eligible pick up work based on JVM threading mechanism and moves to run state.
