
#### Case 01
> If you're simply sharing a counter, consider using an AtomicInteger or another suitable class from the java.util.concurrent.atomic package`

```
public class Test {

    private final static AtomicInteger count = new AtomicInteger(0); 

    public void foo() {  
        count.incrementAndGet();
    }  
}
```


#### Case 02

```
public class Test {
    private volatile static int count = 0;
    private static final Object countLock = new Object();
    public static synchronized void incrementCount() {
        synchronized (countLock) {
            count++;
        }
    }
} 

```

##### Comments
- Dont synchonize on the class itself,  Any random bit of code could synchronize on Test.class and potentially spoil your day. 
- Also, class initialization runs with a lock on the class held, so if you've got crazy class initializers you can give yourself headaches. 
- volatile doesn't help for count++ because it's a read / modify / write sequence (Not Atomic). 
- If you want atomic counter,  java.util.concurrent.atomic.AtomicInteger is likely the right choice here.`


# Reference
`https://stackoverflow.com/questions/2120248/how-to-synchronize-a-static-variable-among-threads-running-different-instances-o`
