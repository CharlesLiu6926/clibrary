package org.charlesliu.c.app.log;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class CLog {
    private static final String C_LOG_PACKAGE;
    static {
        String className = CLog.class.getName();
        C_LOG_PACKAGE = className.substring(0, className.lastIndexOf(".") + 1);
    }
    public static void i(Object... contents) { log(CLogType.I, contents); }
    public static void it(String tag, Object... contents) { log(CLogType.I, tag, contents); }
    public static void a(Object... contents) { log(CLogType.A, contents); }
    public static void at(String tag, Object... contents) { log(CLogType.A, tag, contents); }
    public static void d(Object... contents) { log(CLogType.D, contents); }
    public static void dt(String tag, Object... contents) { log(CLogType.D, tag, contents); }
    public static void w(Object... contents) { log(CLogType.W, contents); }
    public static void wt(String tag, Object... contents) { log(CLogType.W, tag, contents); }
    public static void e(Object... contents) { log(CLogType.E, contents); }
    public static void et(String tag, Object... contents) { log(CLogType.E, tag, contents); }
    public static void v(Object... contents) { log(CLogType.V, contents); }
    public static void vt(String tag, Object... contents) { log(CLogType.V, tag, contents); }
    public static void log(@CLogType.Type int type, Object... contents) {
        log(type, CLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }
    public static void log(@CLogType.Type int type, @NotNull String tag, @NotNull Object... contents) {
        log(CLogManager.getInstance().getConfig(), type, tag, contents);
    }
    public static void log(@NotNull CLogConfig config, @CLogType.Type int type, @NotNull String tag, @NotNull Object... contents) {
        if (!config.enable()) return;
        StringBuilder sb = new StringBuilder();
        if (config.includeThread()) {
            sb.append(CLogConfig.C_LOG_THREAD_FORMATTER.format(Thread.currentThread())).append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            sb.append(CLogConfig.C_LOG_STACK_TRACE_FORMATTER.format(CStackTraceUtil.getCropRealStackTrace(new Throwable().getStackTrace(), C_LOG_PACKAGE, config.stackTraceDepth())));
        }
        String body = parseBody(config, contents);
        sb.append(body);
        List<CLogPrinter> printers = config.printers() == null ? CLogManager.getInstance().getPrinters() : Arrays.asList(config.printers());
        if (printers.size() == 0) return;
        for (CLogPrinter printer: printers) {
            printer.print(config, type, tag, sb.toString());
        }
    }

    private static String parseBody(@NotNull CLogConfig config, @NotNull Object... contents) {
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        StringBuilder sb = new StringBuilder();
        for (Object content : contents) {
            sb.append(content.toString()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
