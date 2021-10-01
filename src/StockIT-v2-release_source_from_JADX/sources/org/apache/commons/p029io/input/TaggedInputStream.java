package org.apache.commons.p029io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.UUID;
import org.apache.commons.p029io.TaggedIOException;

/* renamed from: org.apache.commons.io.input.TaggedInputStream */
public class TaggedInputStream extends ProxyInputStream {
    private final Serializable tag = UUID.randomUUID();

    public TaggedInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public boolean isCauseOf(Throwable th) {
        return TaggedIOException.isTaggedWith(th, this.tag);
    }

    public void throwIfCauseOf(Throwable th) throws IOException {
        TaggedIOException.throwCauseIfTaggedWith(th, this.tag);
    }

    /* access modifiers changed from: protected */
    public void handleIOException(IOException iOException) throws IOException {
        throw new TaggedIOException(iOException, this.tag);
    }
}
