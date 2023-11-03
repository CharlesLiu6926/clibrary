package org.charlesliu.c.app.log;

public class CLogThreadFormatter implements CLogFormatter<Thread> {
    @Override
    public String format(Thread thread) {
        return "Thread: " + Thread.currentThread().getName();
    }
}
