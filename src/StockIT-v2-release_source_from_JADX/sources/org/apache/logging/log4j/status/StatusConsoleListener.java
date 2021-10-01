package org.apache.logging.log4j.status;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.logging.log4j.Level;

public class StatusConsoleListener implements StatusListener {
    private String[] filters;
    private Level level;
    private final PrintStream stream;

    public StatusConsoleListener(Level level2) {
        this(level2, System.out);
    }

    public StatusConsoleListener(Level level2, PrintStream printStream) {
        this.level = Level.FATAL;
        if (printStream != null) {
            this.level = level2;
            this.stream = printStream;
            return;
        }
        throw new IllegalArgumentException("You must provide a stream to use for this listener.");
    }

    public void setLevel(Level level2) {
        this.level = level2;
    }

    public Level getStatusLevel() {
        return this.level;
    }

    public void log(StatusData statusData) {
        if (!filtered(statusData)) {
            this.stream.println(statusData.getFormattedStatus());
        }
    }

    public void setFilters(String... strArr) {
        this.filters = strArr;
    }

    private boolean filtered(StatusData statusData) {
        if (this.filters == null) {
            return false;
        }
        String className = statusData.getStackTraceElement().getClassName();
        for (String startsWith : this.filters) {
            if (className.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }

    public void close() throws IOException {
        if (this.stream != System.out && this.stream != System.err) {
            this.stream.close();
        }
    }
}
