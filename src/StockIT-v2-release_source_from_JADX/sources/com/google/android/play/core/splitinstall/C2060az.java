package com.google.android.play.core.splitinstall;

import android.util.Log;
import com.amplitude.api.Constants;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: com.google.android.play.core.splitinstall.az */
final class C2060az {

    /* renamed from: a */
    private final XmlPullParser f951a;

    /* renamed from: b */
    private final C2066g f952b = new C2066g();

    C2060az(XmlPullParser xmlPullParser) {
        this.f951a = xmlPullParser;
    }

    /* renamed from: a */
    private final String m998a(String str) {
        for (int i = 0; i < this.f951a.getAttributeCount(); i++) {
            if (this.f951a.getAttributeName(i).equals(str)) {
                return this.f951a.getAttributeValue(i);
            }
        }
        return null;
    }

    /* renamed from: b */
    private final void m999b() throws IOException, XmlPullParserException {
        int i = 1;
        while (i != 0) {
            int next = this.f951a.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final C2067h mo34343a() {
        String a;
        while (this.f951a.next() != 1) {
            try {
                if (this.f951a.getEventType() == 2) {
                    if (this.f951a.getName().equals("splits")) {
                        while (this.f951a.next() != 3) {
                            if (this.f951a.getEventType() == 2) {
                                if (!this.f951a.getName().equals("module") || (a = m998a("name")) == null) {
                                    m999b();
                                } else {
                                    while (this.f951a.next() != 3) {
                                        if (this.f951a.getEventType() == 2) {
                                            if (this.f951a.getName().equals(Constants.AMP_TRACKING_OPTION_LANGUAGE)) {
                                                while (this.f951a.next() != 3) {
                                                    if (this.f951a.getEventType() == 2) {
                                                        if (this.f951a.getName().equals("entry")) {
                                                            String a2 = m998a("key");
                                                            String a3 = m998a("split");
                                                            m999b();
                                                            if (!(a2 == null || a3 == null)) {
                                                                this.f952b.mo34353a(a, a2, a3);
                                                            }
                                                        } else {
                                                            m999b();
                                                        }
                                                    }
                                                }
                                            } else {
                                                m999b();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        m999b();
                    }
                }
            } catch (IOException | IllegalStateException | XmlPullParserException e) {
                Log.e("SplitInstall", "Error while parsing splits.xml", e);
                return null;
            }
        }
        return this.f952b.mo34352a();
    }
}
