package org.unimodules.interfaces.permissions;

public enum PermissionsStatus {
    GRANTED(PermissionsResponse.GRANTED_KEY),
    UNDETERMINED("undetermined"),
    DENIED("denied");
    
    private String status;

    private PermissionsStatus(String str) {
        this.status = str;
    }

    public String getStatus() {
        return this.status;
    }
}
