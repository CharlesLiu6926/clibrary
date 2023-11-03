package org.charlesliu.c.app.log;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.charlesliu.c.app.util.CDisplayUtil;

public class CViewPrinterProvider {
    private FrameLayout rootView;
    private View floatingView;
    private boolean  isOpen;
    private FrameLayout logView;
    private RecyclerView recyclerView;

    public CViewPrinterProvider(FrameLayout rootView, RecyclerView recyclerView) {
        this.rootView = rootView;
        this.recyclerView = recyclerView;
    }

    private static final String TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW";
    private static final String TAG_LOG_VIEW = "TAG_LOG_VIEW";
    public void showFloatingView() {
        if (rootView.findViewWithTag(TAG_FLOATING_VIEW) != null) return;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
        View floatingView = genFloatingView();
        floatingView.setTag(TAG_FLOATING_VIEW);
        floatingView.setBackgroundColor(Color.BLACK);
        floatingView.setAlpha(0.8f);
        layoutParams.bottomMargin = CDisplayUtil.dp2px(100, rootView.getResources());
        rootView.addView(genFloatingView(), layoutParams);
    }

    public void hideFloatingView() {
        rootView.removeView(genFloatingView());
    }

    private View genFloatingView() {
        if (floatingView != null) return floatingView;
        TextView textView = new TextView(rootView.getContext());
        textView.setText("CLog");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOpen) {
                    showLogView();
                }
            }
        });
        return floatingView = textView;
    }

    private void showLogView() {
        if (rootView.findViewWithTag(TAG_LOG_VIEW) != null) return;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, CDisplayUtil.dp2px(160, rootView.getResources()));
        params.gravity = Gravity.BOTTOM;
        View logview = genLogView();
        logview.setTag(TAG_LOG_VIEW);
        logview.setBackgroundColor(Color.BLACK);
        logview.setAlpha(0.8f);
        rootView.addView(genLogView(), params);
        isOpen = true;
    }

    private View genLogView() {
        if (logView != null) return logView;
        FrameLayout logView = new FrameLayout(rootView.getContext());
        logView.setBackgroundColor(Color.BLACK);
        logView.addView(recyclerView);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.TOP | Gravity.END;
        TextView textView = new TextView(rootView.getContext());
        textView.setText("CloseLog");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLogView();
            }
        });
        logView.addView(textView, params);
        return this.logView = logView;
    }

    private void closeLogView() {
        isOpen = false;
        rootView.removeView(genLogView());
    }
}
