



## Why RACE CONDITION is a BUG
A race condition is a kind of bug, that happens only with certain temporal conditions.

Example: Imagine you have two threads, A and B.
In Thread A:
```
if( object.a != 0 )
    object.avg = total / object.a
```  
In Thread B:
```
object.a = 0
```
If `thread A` is preempted just after having check that `object.a` is not null, `B` will do `a = 0`, and when thread A will gain the processor, it will do a `divide by zero`.

This bug only happen when thread A is preempted just after the if statement, it's very rare, but it can happen.
