public class TestJoin {
	public static void main(String[] args) {
        JoinItself j = new JoinItself();
        System.out.println(j.isAlive());
        j.start();
        System.out.println(j.isAlive());
        System.out.println("Thread started ...");
    }
}
class JoinItself extends Thread {
    public void run() {
        System.out.println("Inside the run method ");
        System.out.println(Thread.currentThread().isAlive());
        for(int i=0;i<5;i++) {
            try {
            	System.out.println(" Value :" +i);
                System.out.println("Joining itself ...");
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/***

The program will freeze, will not proceed as you call join method inside the method itself.

Output ::

false
true
Thread started ...
Inside the run method 
true
 Value :0
Joining itself ...

***/
