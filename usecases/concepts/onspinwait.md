
## SpinWait

### While learning Java 9 I came across a new method of Thread class, called onSpinWait​. According to javadocs, this method is used for this:

> Indicates that the caller is momentarily unable to progress, until the occurrence of one or more actions on the part of other activities.


#### Example 1
Let's understand from a example, which does not completely highlight its purpose but will help in understanding the concept.
let's say you have a program that checks for new emails and notifies the user:

```
while(true) {
    while(!newEmailArrived()) {
    }
    makeNotification();
}
```

This piece of code will execute millions of times a seconds; spinning over and over, using precious of electricity and CPU power. A common way of doing this would be to wait a few seconds on each iteration.

```
while(true) {
    while(!newEmailArrived()) {
        try {
            Thread.sleep(5000);
        } catch(InterruptedException e) {
        }
    }
    makeNotification();
}
```
This does a very good job. But in cases where you have to work instantly a sleep may be out of question.

Java 9 tries to solve this issue by introducing this new method:

```
while(true) {
    while(!newEmailArrived()) {
        Thread.onSpinWait();
    }
    makeNotification();
}
```
This will work exactly the same as without the method call, but the system is free to lower the process priority; slowing the cycle or reduce electricity on this loop when its resources are needed for other more important things.
