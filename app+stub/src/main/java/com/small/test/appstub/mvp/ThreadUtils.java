package com.small.test.appstub.mvp;

public class ThreadUtils
{
    /**
     * 运行任务.以后再完善.
     * @param task 任务.
     */
    public static void run(Runnable task)
    {
        new Thread(task).start();
    }
    
    /**
     * 运行任务,指定任务的优先级，同时处理运行过程中产生的异常.
     */
    public static void run(final Runnable task, final int priority)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                task.run();
            }
        });
        thread.setPriority(priority);
        thread.start();
    }
}
