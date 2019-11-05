
### Answer 1
It's not atomic because it's a multiple-step operation at the machine code level. That is, longs and doubles are longer than the processor's word length.

### Answer 2
Java long and double are not atomic in 32 bit machines, but atomic in 64 bit machines with some of the 64 bit JVMs. why its dependant on machine bit length? Because 32 bit machine needs two writes for long(as long is 64 bit). 

### Answer 3
Just to clarify the situation for Java, doubles and longs are not read or written to atomically unless they're declared volatile

**BUT**

**17.7 Non-atomic Treatment of double and long**
> For the purposes of the Java programming language memory model, a single write to a non-volatile long or double value is treated as two separate writes: one to each 32-bit half. This can result in a situation where a thread sees the first 32 bits of a 64 bit value from one write, and the second 32 bits from another write. Writes and reads of volatile long and double values are always atomic. Writes to and reads of references are always atomic, regardless of whether they are implemented as 32 or 64 bit values.

That is, the "entire" variable is protected by the volatile modifier, not just the two parts. This tempts me to claim that it's even more important to use volatile for longs than it is for ints since not even a read is atomic for non-volatile longs/doubles.


### Answer 4
**volatile** serves multiple purposes:

guarantees atomic writes to double/long
guarantees that when a thread A sees change in volatile variable made by thread B, thread A can also see all other changes made by thread B before the change to volatile variable (think setting the number of used cells in array after setting the cells themselves).
prevents compiler optimization based on assumption that only one thread can change the variable (think tight loop while (l != 0) {}.




On the specification
Here's the relevant section reproduced here for quick reference:

JLS 17.7 Non-atomic Treatment of double and long
Some implementations may find it convenient to divide a single write action on a 64-bit long or double value into two write actions on adjacent 32 bit values. For efficiency's sake, this behavior is implementation specific; Java virtual machines are free to perform writes to long and double values atomically or in two parts.

For the purposes of the Java programming language memory model, a single write to a non-volatile long or double value is treated as two separate writes: one to each 32-bit half. This can result in a situation where a thread sees the first 32 bits of a 64 bit value from one write, and the second 32 bits from another write. Writes and reads of volatile long and double values are always atomic. Writes to and reads of references are always atomic, regardless of whether they are implemented as 32 or 64 bit values.

VM implementors are encouraged to avoid splitting their 64-bit values where possible. Programmers are encouraged to declare shared 64-bit values as volatile or synchronize their programs correctly to avoid possible complications.


https://stackoverflow.com/questions/517532/writing-long-and-double-is-not-atomic-in-java?noredirect=1&lq=1

https://stackoverflow.com/questions/3463658/are-64-bit-assignments-in-java-atomic-on-a-32-bit-machine?noredirect=1&lq=1
