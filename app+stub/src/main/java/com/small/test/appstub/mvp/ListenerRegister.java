package com.small.test.appstub.mvp;

import android.os.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zhongruan on 2016/12/8.
 */

public class ListenerRegister {
    private Lock lock = new ReentrantLock();

    private List<IMessageListener> listeners = new ArrayList<IMessageListener>();

    private static ListenerRegister instance;

    private ListenerRegister (){

    }

    public static synchronized ListenerRegister getInstance() {
        if (instance == null) {
            instance = new ListenerRegister();
        }
        return instance;
    }

    public void registListener(IMessageListener messageListener)
    {
        lock.lock();
        try
        {
            listeners.add(messageListener);
        }
        finally
        {
            lock.unlock();
        }
    }

    public void clearAll()
    {
        lock.lock();
        try
        {
            listeners.clear();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void clearListeners()
    {
        lock.lock();
        try
        {
            listeners.clear();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void unregistListener(IMessageListener messageListener)
    {
        lock.lock();
        try
        {
            listeners.remove(messageListener);
        }
        finally
        {
            lock.unlock();
        }
    }

    public void dispatchMessage(Message message)
    {
        lock.lock();
        try
        {
            for (IMessageListener messageListener : listeners)
            {
                ThreadUtils.run(new MessageRunnable(message, messageListener));
            }
        }
        finally
        {
            lock.unlock();
        }
    }

    private class MessageRunnable implements Runnable
    {
        private Message message;

        private IMessageListener messageListener;

        public MessageRunnable(Message message, IMessageListener messageListener)
        {
            super();
            this.message = message;
            this.messageListener = messageListener;
        }

        @Override
        public void run()
        {
            messageListener.onMessageReceive(message);
        }

    }
}
