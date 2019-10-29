A thread is in WAITING state goes in BLOCK state,until it acquires monitor by notify and become RUNNABLE.

Same applies for TIMEDWAITING,it goes in BLOCK state,if monitor is hold by some other thread,even though specified time has passed.(your diagram need to be corrected)
![alt test](https://github.com/anwaaralikhan/multithreading/blob/master/usecases/pictures/thread_states.png)

