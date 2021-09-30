package p000ai.api.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/* renamed from: ai.api.model.ResponseMessage */
public abstract class ResponseMessage {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    @Expose
    private int type;

    protected ResponseMessage(MessageType messageType) {
        this.type = messageType.code;
    }

    /* renamed from: ai.api.model.ResponseMessage$MessageType */
    public enum MessageType {
        SPEECH(0, ResponseSpeech.class),
        CARD(1, ResponseCard.class),
        QUICK_REPLY(2, ResponseQuickReply.class),
        IMAGE(3, ResponseImage.class),
        PAYLOAD(4, ResponsePayload.class);
        
        /* access modifiers changed from: private */
        public final int code;
        private final Type type;

        private MessageType(int i, Type type2) {
            this.code = i;
            this.type = type2;
        }

        public int getCode() {
            return this.code;
        }

        public Type getType() {
            return this.type;
        }
    }

    /* renamed from: ai.api.model.ResponseMessage$ResponseSpeech */
    public static class ResponseSpeech extends ResponseMessage {
        @Expose
        public List<String> speech;

        public ResponseSpeech() {
            super(MessageType.SPEECH);
        }

        public List<String> getSpeech() {
            return this.speech;
        }

        public void setSpeech(List<String> list) {
            this.speech = list;
        }

        public void setSpeech(String... strArr) {
            setSpeech((List<String>) Arrays.asList(strArr));
        }
    }

    /* renamed from: ai.api.model.ResponseMessage$ResponseCard */
    public static class ResponseCard extends ResponseMessage {
        @Expose
        private List<Button> buttons;
        @Expose
        private String imageUrl;
        @Expose
        private String subtitle;
        @Expose
        private String title;

        public ResponseCard() {
            super(MessageType.CARD);
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getSubtitle() {
            return this.subtitle;
        }

        public void setSubtitle(String str) {
            this.subtitle = str;
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public void setImageUrl(String str) {
            this.imageUrl = str;
        }

        public List<Button> getButtons() {
            return this.buttons;
        }

        public void setButtons(List<Button> list) {
            this.buttons = list;
        }

        public void setButtons(Button... buttonArr) {
            setButtons((List<Button>) Arrays.asList(buttonArr));
        }

        /* renamed from: ai.api.model.ResponseMessage$ResponseCard$Button */
        public static class Button {
            @Expose
            private String postback;
            @Expose
            private String text;

            public Button(String str, String str2) {
                this.text = str;
                this.postback = str2;
            }

            public void setText(String str) {
                this.text = str;
            }

            public String getText() {
                return this.text;
            }

            public void setPostback(String str) {
                this.postback = str;
            }

            public String getPostback() {
                return this.postback;
            }
        }
    }

    /* renamed from: ai.api.model.ResponseMessage$ResponseQuickReply */
    public static class ResponseQuickReply extends ResponseMessage {
        @Expose
        private List<String> replies;
        @Expose
        private String title;

        public ResponseQuickReply() {
            super(MessageType.QUICK_REPLY);
        }

        public List<String> getReplies() {
            return this.replies;
        }

        public void setReplies(List<String> list) {
            this.replies = list;
        }

        public void setReplies(String... strArr) {
            setReplies((List<String>) Arrays.asList(strArr));
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getTitle() {
            return this.title;
        }
    }

    /* renamed from: ai.api.model.ResponseMessage$ResponseImage */
    public static class ResponseImage extends ResponseMessage {
        @Expose
        private String imageUrl;

        public ResponseImage() {
            super(MessageType.IMAGE);
        }

        public String getImageUrl() {
            return this.imageUrl;
        }

        public void setImageUrl(String str) {
            this.imageUrl = str;
        }
    }

    /* renamed from: ai.api.model.ResponseMessage$ResponsePayload */
    public static class ResponsePayload extends ResponseMessage {
        @Expose
        private JsonObject payload;

        public ResponsePayload() {
            super(MessageType.PAYLOAD);
        }

        public JsonObject getPayload() {
            return this.payload;
        }

        public void setPayload(JsonObject jsonObject) {
            this.payload = jsonObject;
        }
    }
}
