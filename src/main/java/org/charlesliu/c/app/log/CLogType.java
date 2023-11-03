package org.charlesliu.c.app.log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CLogType {
    @IntDef({V, D, A, I, W, E})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {}
    public final static int V = Log.VERBOSE;
    public final static int D = Log.DEBUG;
    public final static int I = Log.INFO;
    public final static int W = Log.WARN;
    public final static int A = Log.ASSERT;
    public final static int E = Log.ERROR;
}
