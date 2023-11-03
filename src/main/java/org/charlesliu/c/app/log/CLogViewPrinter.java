package org.charlesliu.c.app.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.charlesliu.c.library.R;

import java.util.ArrayList;

public class CLogViewPrinter implements CLogPrinter {
    RecyclerView recyclerView;
    CViewPrinterProvider provider;
    LogAdapter adapter;

    public CLogViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        this.recyclerView = new RecyclerView(activity);
        provider = new CViewPrinterProvider(rootView, recyclerView);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public CViewPrinterProvider getProvider() {
        return provider;
    }

    @Override
    public void print(@NonNull CLogConfig config, int level, String tag, @NonNull String printString) {
        adapter.addLog(new CLogMo(System.currentTimeMillis(), level, tag, printString));
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {
        LayoutInflater inflater;
        ArrayList<CLogMo> logs = new ArrayList<>();
        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        public void addLog(CLogMo logMo) {
            logs.add(logMo);
            notifyItemInserted(logs.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.clog_item, parent, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            CLogMo cLogMo = logs.get(position);
            int color = getHighlightColor(cLogMo.level);
            holder.tagView.setTextColor(color);
            holder.messageView.setTextColor(color);
            holder.tagView.setText(cLogMo.getFlattened());
            holder.messageView.setText(cLogMo.log);
        }

        private int getHighlightColor(int level) {
            int highlightColor;
            switch (level) {
                case CLogType.V:
                    highlightColor = 0xffbbbbbb;
                    break;
                case CLogType.D:
                    highlightColor = 0xffffffff;
                    break;
                case CLogType.I:
                    highlightColor = 0xff6a8759;
                    break;
                case CLogType.W:
                    highlightColor = 0xffbbb529;
                    break;
                case CLogType.E:
                    highlightColor = 0xffff6b68;
                    break;
                default:
                    highlightColor = 0xffffff00;
                    break;
            }
            return highlightColor;
        }

        @Override
        public int getItemCount() {
            return logs.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tagView;
        TextView messageView;
        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }
}
