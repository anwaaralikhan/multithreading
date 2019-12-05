public class Test {

    private final static AtomicInteger count = new AtomicInteger(0); 

    public void foo() {  
        count.incrementAndGet();
    }  
}
