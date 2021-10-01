package org.unimodules.core.errors;

import org.unimodules.core.interfaces.CodedThrowable;

public class ModuleNotFoundException extends CodedException implements CodedThrowable {
    public String getCode() {
        return "E_MODULE_NOT_FOUND";
    }

    public ModuleNotFoundException(String str) {
        super("Module '" + str + "' not found. Are you sure all modules are linked correctly?");
    }
}
