
It's not atomic because it's a multiple-step operation at the machine code level. That is, longs and doubles are longer than the processor's word length.

Java long and double are not atomic in 32 bit machines, but atomic in 64 bit machines with some of the 64 bit JVMs. why its dependant on machine bit length? Because 32 bit machine needs two writes for long(as long is 64 bit). 

Just to clarify the situation for Java, doubles and longs are not read or written to atomically unless they're declared volatile


**17.7 Non-atomic Treatment of double and long**

[...]
For the purposes of the Java programming language memory model, a single write to a non-volatile long or double value is treated as two separate writes: one to each 32-bit half. This can result in a situation where a thread sees the first 32 bits of a 64 bit value from one write, and the second 32 bits from another write. Writes and reads of volatile long and double values are always atomic. Writes to and reads of references are always atomic, regardless of whether they are implemented as 32 or 64 bit values.
https://stackoverflow.com/questions/517532/writing-long-and-double-is-not-atomic-in-java?noredirect=1&lq=1

