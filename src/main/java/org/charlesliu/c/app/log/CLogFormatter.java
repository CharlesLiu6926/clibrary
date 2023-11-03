package org.charlesliu.c.app.log;

public interface CLogFormatter<T> {
    String format(T t);
}
