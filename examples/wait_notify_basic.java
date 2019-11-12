public class myThread extends Thread{
     @override
     public void run(){
        while(true){
           threadCondWait();// Circle waiting...
           //bla bla bla bla
        }
     }
     public synchronized void threadCondWait(){
        while(myCondition){
           wait();//Comminucate with notify()
        }
     }

}
public class myAnotherThread extends Thread{
     @override
     public void run(){
        //Bla Bla bla
        notify();//Trigger wait() Next Step
     }

}
