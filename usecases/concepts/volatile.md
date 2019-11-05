
1. The volatile keyword in Java is only application to a variable and using volatile keyword with class and method is illegal.
2. volatile keyword in Java guarantees that value of the volatile variable will always be read from main memory and not from Thread's local cache.
3. In Java reads and writes are atomic for all variables declared using Java volatile keyword (including long and double variables).
4. Using the volatile keyword in Java on variables reduces the risk of memory consistency errors because any write to a volatile variable in Java establishes a happens-before relationship with subsequent reads of that same variable.
5. From Java 5 changes to a volatile variable are always visible to other threads. What's more, it also means that when a thread reads a volatile variable in Java, it sees not just the latest change to the volatile variable but also the side effects of the code that led up the change.
6. Reads and writes are atomic for reference variables are for most primitive variables (all types except long and double) even without the use of volatile keyword in Java.
7. An access to a volatile variable in Java never has a chance to block, since we are only doing a simple read or write, so unlike a synchronized block we will never hold on to any lock or wait for any lock.
8. Java volatile variable that is an object reference may be null.
9. Java volatile keyword doesn't mean atomic, its common misconception that after declaring volatile ++ will be atomic, to make the operation atomic you still need to ensure exclusive access using synchronized method or block in Java.
10. If a variable is not shared between multiple threads, you don't need to use volatile keyword with that variable.
11. You can not synchronize on the null object but your volatile variable in Java could be null.

12. From Java 5 writing into a volatile field has the same memory effect as a monitor release, and reading from a volatile field has the same memory effect as a monitor acquire
