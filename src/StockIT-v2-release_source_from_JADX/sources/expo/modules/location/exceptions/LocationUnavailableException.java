package expo.modules.location.exceptions;

import org.unimodules.core.errors.CodedException;
import org.unimodules.core.interfaces.CodedThrowable;

public class LocationUnavailableException extends CodedException implements CodedThrowable {
    public String getCode() {
        return "E_LOCATION_UNAVAILABLE";
    }

    public LocationUnavailableException() {
        super("Location provider is unavailable. Make sure that location services are enabled.");
    }
}
