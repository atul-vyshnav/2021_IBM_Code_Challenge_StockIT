package org.apache.logging.log4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public final class PropertiesUtil {
    private static final PropertiesUtil LOG4J_PROPERTIES = new PropertiesUtil("log4j2.component.properties");
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final Properties props;

    public PropertiesUtil(Properties properties) {
        this.props = properties;
    }

    static Properties loadClose(InputStream inputStream, Object obj) {
        Properties properties = new Properties();
        if (inputStream != null) {
            try {
                properties.load(inputStream);
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("Unable to close {}", obj, e);
                }
            } catch (IOException e2) {
                LOGGER.error("Unable to read {}", obj, e2);
                try {
                    inputStream.close();
                } catch (IOException e3) {
                    LOGGER.error("Unable to close {}", obj, e3);
                }
            } catch (Throwable th) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    LOGGER.error("Unable to close {}", obj, e4);
                }
                throw th;
            }
        }
        return properties;
    }

    public PropertiesUtil(String str) {
        Properties properties = new Properties();
        for (URL next : LoaderUtil.findResources(str)) {
            InputStream inputStream = null;
            try {
                inputStream = next.openStream();
                properties.load(inputStream);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        LOGGER.error("Unable to close {}", next.toString(), e);
                    }
                }
            } catch (IOException e2) {
                LOGGER.error("Unable to read {}", next.toString(), e2);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        LOGGER.error("Unable to close {}", next.toString(), e3);
                    }
                }
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e4) {
                        LOGGER.error("Unable to close {}", next.toString(), e4);
                    }
                }
                throw th;
            }
        }
        this.props = properties;
    }

    public static PropertiesUtil getProperties() {
        return LOG4J_PROPERTIES;
    }

    public String getStringProperty(String str) {
        String str2;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException unused) {
            str2 = null;
        }
        return str2 == null ? this.props.getProperty(str) : str2;
    }

    public int getIntegerProperty(String str, int i) {
        String str2;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException unused) {
            str2 = null;
        }
        if (str2 == null) {
            str2 = this.props.getProperty(str);
        }
        if (str2 != null) {
            try {
                return Integer.parseInt(str2);
            } catch (Exception unused2) {
            }
        }
        return i;
    }

    public long getLongProperty(String str, long j) {
        String str2;
        try {
            str2 = System.getProperty(str);
        } catch (SecurityException unused) {
            str2 = null;
        }
        if (str2 == null) {
            str2 = this.props.getProperty(str);
        }
        if (str2 != null) {
            try {
                return Long.parseLong(str2);
            } catch (Exception unused2) {
            }
        }
        return j;
    }

    public String getStringProperty(String str, String str2) {
        String stringProperty = getStringProperty(str);
        return stringProperty == null ? str2 : stringProperty;
    }

    public boolean getBooleanProperty(String str) {
        return getBooleanProperty(str, false);
    }

    public boolean getBooleanProperty(String str, boolean z) {
        String stringProperty = getStringProperty(str);
        return stringProperty == null ? z : "true".equalsIgnoreCase(stringProperty);
    }

    public static Properties getSystemProperties() {
        try {
            return new Properties(System.getProperties());
        } catch (SecurityException e) {
            LOGGER.error("Unable to access system properties.", (Throwable) e);
            return new Properties();
        }
    }
}
