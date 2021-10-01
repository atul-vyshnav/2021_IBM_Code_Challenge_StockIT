package org.apache.commons.p029io.input;

/* renamed from: org.apache.commons.io.input.TailerListener */
public interface TailerListener {
    void fileNotFound();

    void fileRotated();

    void handle(Exception exc);

    void handle(String str);

    void init(Tailer tailer);
}
