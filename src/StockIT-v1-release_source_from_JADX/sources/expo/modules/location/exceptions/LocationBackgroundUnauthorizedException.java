package expo.modules.location.exceptions;

import org.unimodules.core.errors.CodedException;
import org.unimodules.core.interfaces.CodedThrowable;

public class LocationBackgroundUnauthorizedException extends CodedException implements CodedThrowable {
    public String getCode() {
        return "E_LOCATION_BACKGROUND_UNAUTHORIZED";
    }

    public LocationBackgroundUnauthorizedException() {
        super("Not authorized to use background location services.");
    }
}
