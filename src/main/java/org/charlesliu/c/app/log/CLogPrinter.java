package org.charlesliu.c.app.log;

import org.jetbrains.annotations.NotNull;

public interface CLogPrinter {
    void print(@NotNull CLogConfig config, int level, String tag, @NotNull String printString);
}
