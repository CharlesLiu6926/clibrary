package org.charlesliu.c.app.log;

public class CStackTraceUtil {
    public static StackTraceElement[] getCropRealStackTrace(StackTraceElement[] stackTraceElements, String ignorePackage, int maxDepth) {
        return cropStackTrace(getRealStackTrace(stackTraceElements, ignorePackage), maxDepth);
    }
    private static StackTraceElement[] getRealStackTrace(StackTraceElement[] stackTraceElements, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTraceElements.length;
        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = stackTraceElements[i].getClassName();
            if (className.startsWith(ignorePackage)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStackTrace = new StackTraceElement[realDepth];
        System.arraycopy(stackTraceElements, ignoreDepth, realStackTrace, 0, realDepth);
        return realStackTrace;
    }
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] stackTraceElements, int maxDepth) {
        int realDepth = stackTraceElements.length;
        if (maxDepth > 0) {
            realDepth = Math.min(maxDepth, realDepth);
        }
        StackTraceElement[] realStackTraceElement = new StackTraceElement[realDepth];
        System.arraycopy(stackTraceElements, 0, realStackTraceElement, 0, realDepth);
        return realStackTraceElement;
    }
}
