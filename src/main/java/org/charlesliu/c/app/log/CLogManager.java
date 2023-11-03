package org.charlesliu.c.app.log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CLogManager {
    private CLogConfig config;
    private ArrayList<CLogPrinter> printers = new ArrayList<>();
    private static CLogManager instance;

    private CLogManager(CLogConfig config, CLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static CLogManager getInstance() {
        return instance;
    }

    public static void init(@NotNull CLogConfig config, CLogPrinter... printer) {
        instance = new CLogManager(config, printer);
    }

    public List<CLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(CLogPrinter printer) {
        this.printers.add(printer);
    }

    public void removePrinter(CLogPrinter printer) {
        if (this.printers != null) {
            this.printers.remove(printer);
        }
    }

    public CLogConfig getConfig() {
        return config;
    }
}
