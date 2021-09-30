package expo.modules.location.exceptions;

import org.unimodules.core.errors.CodedException;
import org.unimodules.core.interfaces.CodedThrowable;

public class LocationSettingsUnsatisfiedException extends CodedException implements CodedThrowable {
    public String getCode() {
        return "E_LOCATION_SETTINGS_UNSATISFIED";
    }

    public LocationSettingsUnsatisfiedException() {
        super("Location request failed due to unsatisfied device settings.");
    }
}
