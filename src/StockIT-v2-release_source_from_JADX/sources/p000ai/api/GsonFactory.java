package p000ai.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Locale;
import p000ai.api.model.ResponseMessage;

/* renamed from: ai.api.GsonFactory */
public class GsonFactory {
    private static final GsonFactory DEFAULT_FACTORY = new GsonFactory();
    /* access modifiers changed from: private */
    public static final Gson DEFAULT_GSON = new GsonBuilder().create();
    private static final Gson PROTOCOL_GSON = new GsonBuilder().setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).toPattern()).registerTypeAdapter(ResponseMessage.class, new ResponseItemAdapter()).registerTypeAdapter(ResponseMessage.ResponseSpeech.class, new ResponseSpeechDeserializer()).create();

    public Gson getGson() {
        return PROTOCOL_GSON;
    }

    public static GsonFactory getDefaultFactory() {
        return DEFAULT_FACTORY;
    }

    /* renamed from: ai.api.GsonFactory$ResponseItemAdapter */
    private static class ResponseItemAdapter implements JsonDeserializer<ResponseMessage>, JsonSerializer<ResponseMessage> {
        private ResponseItemAdapter() {
        }

        public ResponseMessage deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            int asInt = jsonElement.getAsJsonObject().get("type").getAsInt();
            for (ResponseMessage.MessageType messageType : ResponseMessage.MessageType.values()) {
                if (messageType.getCode() == asInt) {
                    return (ResponseMessage) jsonDeserializationContext.deserialize(jsonElement, messageType.getType());
                }
            }
            throw new JsonParseException(String.format("Unexpected message type value: %d", new Object[]{Integer.valueOf(asInt)}));
        }

        public JsonElement serialize(ResponseMessage responseMessage, Type type, JsonSerializationContext jsonSerializationContext) {
            return jsonSerializationContext.serialize(responseMessage, responseMessage.getClass());
        }
    }

    /* renamed from: ai.api.GsonFactory$ResponseSpeechDeserializer */
    private static class ResponseSpeechDeserializer implements JsonDeserializer<ResponseMessage> {
        private ResponseSpeechDeserializer() {
        }

        public ResponseMessage.ResponseSpeech deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = (JsonObject) jsonElement;
                if (jsonObject.get("speech").isJsonPrimitive()) {
                    JsonArray jsonArray = new JsonArray();
                    jsonArray.add(jsonObject.get("speech"));
                    jsonObject.add("speech", jsonArray);
                }
            }
            return (ResponseMessage.ResponseSpeech) GsonFactory.DEFAULT_GSON.fromJson(jsonElement, type);
        }
    }
}
