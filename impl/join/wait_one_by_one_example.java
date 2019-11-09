// Java program to explain the 
// concept of joining a thread. 
import java.io.*; 


/***
Joining Threads in Java

The join method allows one thread to wait for the completion of another. If t is a Thread object whose thread is currently executing,
t.join();
causes the current thread to pause execution until t's thread terminates. 

java.lang.Thread class provides the join() method which allows one thread to wait until another thread completes its execution. 

If t is a Thread object whose thread is currently executing, then t.join() will make sure that t is terminated before the next instruction is executed by the program.
***/
  
// Creating thread by creating the 
// objects of that class 
class ThreadJoining extends Thread 
{ 
    @Override
    public void run() 
    { 
        for (int i = 0; i < 2; i++) 
        { 
            try
            { 
                Thread.sleep(500); 
                System.out.println("Current Thread: "
                        + Thread.currentThread().getName()); 
            } 
  
            catch(Exception ex) 
            { 
                System.out.println("Exception has" + 
                                " been caught" + ex); 
            } 
            System.out.println(i); 
        } 
    } 
} 
  
class GFG 
{ 
    public static void main (String[] args) 
    { 
  
        // creating two threads 
        ThreadJoining t1 = new ThreadJoining(); 
        ThreadJoining t2 = new ThreadJoining(); 
        ThreadJoining t3 = new ThreadJoining(); 
  
        // thread t1 starts 
        t1.start(); 
  
        // starts second thread after when 
        // first thread t1 has died. 
        try
        { 
            System.out.println("Current Thread: "
                  + Thread.currentThread().getName()); 
            t1.join(); 
        } 
  
        catch(Exception ex) 
        { 
            System.out.println("Exception has " + 
                                "been caught" + ex); 
        } 
  
        // t2 starts 
        t2.start(); 
  
        // starts t3 after when thread t2 has died. 
        try
        { 
            System.out.println("Current Thread: "
                 + Thread.currentThread().getName()); 
            t2.join(); 
        } 
  
        catch(Exception ex) 
        { 
            System.out.println("Exception has been" + 
                                    " caught" + ex); 
        } 
  
        t3.start(); 
    } 
} 

/***
output:

Current Thread: main
Current Thread: Thread-0
0
Current Thread: Thread-0
1
Current Thread: main
Current Thread: Thread-1
0
Current Thread: Thread-1
1
Current Thread: Thread-2
0
Current Thread: Thread-2
1
In the above example we can see clearly second thread t2 starts after first thread t1 has died and t3 will start its execution after second thread t2 has died.

Reference https://www.geeksforgeeks.org/joining-threads-in-java/
***/
