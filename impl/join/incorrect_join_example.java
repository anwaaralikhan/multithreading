// Incorrect code
for (int i = 0; i < 3; i++)
{
     Thread t = new Thread(Task);
     t.Start();
     t.Join();
}

/*** Instead, you should start all three threads and then join all of them: ***/

List<Thread> threads = new List<Thread>();
for (int i = 0; i < 3; i++)
{
    Thread t = new Thread(Task);
    t.Start();
    threads.Add(t);
}
foreach (Thread t in threads)
{
    t.Join();
}
