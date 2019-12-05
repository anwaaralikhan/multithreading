
`If you're simply sharing a counter, consider using an AtomicInteger or another suitable class from the java.util.concurrent.atomic package`

```
public class Test {

    private final static AtomicInteger count = new AtomicInteger(0); 

    public void foo() {  
        count.incrementAndGet();
    }  
}
```


# Reference
`https://stackoverflow.com/questions/2120248/how-to-synchronize-a-static-variable-among-threads-running-different-instances-o`
