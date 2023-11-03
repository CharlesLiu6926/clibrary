package org.charlesliu.c.app.log;

import android.util.Log;

import androidx.annotation.NonNull;

public class CLogConsolePrinter implements CLogPrinter {
    @Override
    public void print(@NonNull CLogConfig config, int level, String tag, @NonNull String printString) {
        int len = printString.length();
        int countOfLen = len / CLogConfig.MAX_LEN;
        if (countOfLen == 0) {
            Log.println(level, tag, printString);
        } else {
            int index = 0;
            for (int i = 0; i < countOfLen; i++) {
                Log.println(level, config.getGlobalTag(), printString.substring(index, index + CLogConfig.MAX_LEN));
                index += CLogConfig.MAX_LEN;
            }
            if (len != index) {
                Log.println(level, config.getGlobalTag(), printString.substring(index, len));
            }
        }
    }
}