package org.charlesliu.c.app.log;

public class CLogStackTraceFormatter implements CLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] stackTraceElements) {
        StringBuilder sb = new StringBuilder(128);
        if (stackTraceElements == null || stackTraceElements.length == 0) {
            return "\n";
        } else if (stackTraceElements.length == 1) {
            sb.append("\t-").append(stackTraceElements[0].toString()).append("\n");
            return sb.toString();
        } else {
            for (int i = 0, len = stackTraceElements.length; i < len; i++) {
                if (i != len - 1) {
                    sb.append("\t├").append(stackTraceElements[i]).append("\n");
                } else {
                    sb.append("\t└").append(stackTraceElements[i]).append("\n");
                }
            }
            return sb.toString();
        }
    }
}
