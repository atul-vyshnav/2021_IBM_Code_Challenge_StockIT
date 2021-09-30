package expo.modules.location.exceptions;

import org.unimodules.core.errors.CodedException;
import org.unimodules.core.interfaces.CodedThrowable;

public class LocationRequestRejectedException extends CodedException implements CodedThrowable {
    public String getCode() {
        return "E_LOCATION_REQUEST_REJECTED";
    }

    public LocationRequestRejectedException(Exception exc) {
        super("Location request has been rejected: " + exc.getMessage());
    }
}
