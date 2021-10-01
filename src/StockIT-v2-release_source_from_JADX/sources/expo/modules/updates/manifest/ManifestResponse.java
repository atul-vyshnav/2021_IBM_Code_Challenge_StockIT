package expo.modules.updates.manifest;

import okhttp3.C3401Response;

public class ManifestResponse {
    private C3401Response mResponse;

    public ManifestResponse(C3401Response response) {
        this.mResponse = response;
    }

    public String header(String str) {
        return this.mResponse.header(str);
    }
}
