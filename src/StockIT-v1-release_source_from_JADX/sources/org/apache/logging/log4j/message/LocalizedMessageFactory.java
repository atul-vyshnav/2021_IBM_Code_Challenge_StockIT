package org.apache.logging.log4j.message;

import java.util.ResourceBundle;

public class LocalizedMessageFactory extends AbstractMessageFactory {
    private static final long serialVersionUID = 1;
    private final String baseName;
    private final ResourceBundle resourceBundle;

    public LocalizedMessageFactory(ResourceBundle resourceBundle2) {
        this.resourceBundle = resourceBundle2;
        this.baseName = null;
    }

    public LocalizedMessageFactory(String str) {
        this.resourceBundle = null;
        this.baseName = str;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public ResourceBundle getResourceBundle() {
        return this.resourceBundle;
    }

    public Message newMessage(String str, Object... objArr) {
        ResourceBundle resourceBundle2 = this.resourceBundle;
        if (resourceBundle2 == null) {
            return new LocalizedMessage(this.baseName, str, objArr);
        }
        return new LocalizedMessage(resourceBundle2, str, objArr);
    }
}
