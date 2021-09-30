package org.apache.logging.log4j.message;

import com.amplitude.api.Constants;
import java.io.Serializable;

public class StructuredDataId implements Serializable {

    /* renamed from: AT */
    private static final String f1336AT = "@";
    private static final int MAX_LENGTH = 32;
    public static final StructuredDataId META = new StructuredDataId("meta", (String[]) null, new String[]{"sequenceId", "sysUpTime", Constants.AMP_TRACKING_OPTION_LANGUAGE});
    public static final StructuredDataId ORIGIN = new StructuredDataId("origin", (String[]) null, new String[]{"ip", "enterpriseId", "software", "swVersion"});
    public static final int RESERVED = -1;
    public static final StructuredDataId TIME_QUALITY = new StructuredDataId("timeQuality", (String[]) null, new String[]{"tzKnown", "isSynced", "syncAccuracy"});
    private static final long serialVersionUID = 9031746276396249990L;
    private final int enterpriseNumber;
    private final String name;
    private final String[] optional;
    private final String[] required;

    protected StructuredDataId(String str, String[] strArr, String[] strArr2) {
        int i;
        if (str == null) {
            i = -1;
        } else if (str.length() <= 32) {
            i = str.indexOf(f1336AT);
        } else {
            throw new IllegalArgumentException(String.format("Length of id %s exceeds maximum of %d characters", new Object[]{str, 32}));
        }
        if (i > 0) {
            this.name = str.substring(0, i);
            this.enterpriseNumber = Integer.parseInt(str.substring(i + 1));
        } else {
            this.name = str;
            this.enterpriseNumber = -1;
        }
        this.required = strArr;
        this.optional = strArr2;
    }

    public StructuredDataId(String str, int i, String[] strArr, String[] strArr2) {
        if (str == null) {
            throw new IllegalArgumentException("No structured id name was supplied");
        } else if (str.contains(f1336AT)) {
            throw new IllegalArgumentException("Structured id name cannot contain an '@'");
        } else if (i > 0) {
            this.name = str;
            this.enterpriseNumber = i;
            if (i >= 0) {
                str = str + f1336AT + i;
            }
            if (str.length() <= 32) {
                this.required = strArr;
                this.optional = strArr2;
                return;
            }
            throw new IllegalArgumentException("Length of id exceeds maximum of 32 characters: " + str);
        } else {
            throw new IllegalArgumentException("No enterprise number was supplied");
        }
    }

    public StructuredDataId makeId(StructuredDataId structuredDataId) {
        return structuredDataId == null ? this : makeId(structuredDataId.getName(), structuredDataId.getEnterpriseNumber());
    }

    public StructuredDataId makeId(String str, int i) {
        String[] strArr;
        if (i <= 0) {
            return this;
        }
        String str2 = this.name;
        String[] strArr2 = null;
        if (str2 != null) {
            strArr2 = this.required;
            String str3 = str2;
            strArr = this.optional;
            str = str3;
        } else {
            strArr = null;
        }
        return new StructuredDataId(str, i, strArr2, strArr);
    }

    public String[] getRequired() {
        return this.required;
    }

    public String[] getOptional() {
        return this.optional;
    }

    public String getName() {
        return this.name;
    }

    public int getEnterpriseNumber() {
        return this.enterpriseNumber;
    }

    public boolean isReserved() {
        return this.enterpriseNumber <= 0;
    }

    public String toString() {
        if (isReserved()) {
            return this.name;
        }
        return this.name + f1336AT + this.enterpriseNumber;
    }
}
