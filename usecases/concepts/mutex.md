### Answer 01
When you have a multi-threaded application, the different threads sometimes share a common resource, such as a variable or similar. This shared source often cannot be accessed at the same time, so a construct is needed to ensure that only one thread is using that resource at a time.

The concept is called **mutual exclusion** `(short Mutex)`, and is a way to ensure that only one thread is allowed inside that area, using that resource etc.

How to use them is language specific, but is often (if not always) based on a operating system mutex


### Answer 02
Mutexes are useful in situations where you need to enforce exclusive access to a resource accross multiple processes, where a regular lock won't help since it only works accross threads. Acts as gatekeeper!


https://stackoverflow.com/questions/34524/what-is-a-mutex?noredirect=1&lq=1
