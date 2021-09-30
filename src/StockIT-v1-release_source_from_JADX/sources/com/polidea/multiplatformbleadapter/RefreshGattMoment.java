package com.polidea.multiplatformbleadapter;

public enum RefreshGattMoment {
    ON_CONNECTED("OnConnected");
    
    final String name;

    private RefreshGattMoment(String str) {
        this.name = str;
    }

    public static RefreshGattMoment getByName(String str) {
        for (RefreshGattMoment refreshGattMoment : values()) {
            if (refreshGattMoment.name.equals(str)) {
                return refreshGattMoment;
            }
        }
        return null;
    }
}
