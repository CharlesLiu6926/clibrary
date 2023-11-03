package org.charlesliu.c.app.log;

public abstract class CLogConfig {
    static int MAX_LEN = 512;
    static CLogStackTraceFormatter C_LOG_STACK_TRACE_FORMATTER = new CLogStackTraceFormatter();
    static CLogThreadFormatter C_LOG_THREAD_FORMATTER = new CLogThreadFormatter();
    public String getGlobalTag() {
        return "CLog";
    }
    public int stackTraceDepth() {
        return 5;
    }
    public boolean includeThread() {
        return false;
    }
    public JsonParser injectJsonParser() {
        return null;
    }

    public CLogPrinter[] printers() {
        return null;
    }
    public interface JsonParser {
        String toJson(Object o);
    }
    public boolean enable() {
        return true;
    }
}
