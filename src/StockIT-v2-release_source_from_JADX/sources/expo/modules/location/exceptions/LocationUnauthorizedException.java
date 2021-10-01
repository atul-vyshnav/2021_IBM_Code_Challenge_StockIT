package expo.modules.location.exceptions;

import org.unimodules.core.errors.CodedException;
import org.unimodules.core.interfaces.CodedThrowable;

public class LocationUnauthorizedException extends CodedException implements CodedThrowable {
    public String getCode() {
        return "E_LOCATION_UNAUTHORIZED";
    }

    public LocationUnauthorizedException() {
        super("Not authorized to use location services.");
    }
}
