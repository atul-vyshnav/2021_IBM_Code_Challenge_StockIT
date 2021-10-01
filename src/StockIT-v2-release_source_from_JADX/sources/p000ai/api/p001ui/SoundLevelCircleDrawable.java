package p000ai.api.p001ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* renamed from: ai.api.ui.SoundLevelCircleDrawable */
public class SoundLevelCircleDrawable extends Drawable {
    public static final int CENTER_COLOR_DEF = -889815;
    public static final int HALO_COLOR_DEF = Color.argb(16, 0, 0, 0);
    private static final float INITIAL_VALUE = 2.5f;
    private static final float MAX_VALUE = 10.0f;
    private static final float MIN_VALUE = 0.5f;
    private final Rect bounds;
    private final float circleCenterX;
    private final float circleCenterY;
    private boolean drawCenter;
    private boolean drawSoundLevel;
    private float maxMicLevel;
    private final float maxRadius;
    private float minMicLevel;
    private final float minRadius;
    private final Paint paintIndicatorCenter;
    private final Paint paintIndicatorHalo;
    private float smoothedLevel;

    public int getOpacity() {
        return -3;
    }

    public void setAlpha(int i) {
    }

    public void setColorFilter(ColorFilter colorFilter) {
    }

    private static Paint newColorPaint(int i) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(i);
        return paint;
    }

    public SoundLevelCircleDrawable() {
        this((Params) null);
    }

    public SoundLevelCircleDrawable(Params params) {
        int i;
        int i2;
        this.minMicLevel = MIN_VALUE;
        this.maxMicLevel = MAX_VALUE;
        this.drawSoundLevel = false;
        this.smoothedLevel = INITIAL_VALUE;
        this.bounds = new Rect();
        this.drawCenter = false;
        if (params != null) {
            this.maxRadius = params.maxRadius;
            this.minRadius = params.minRadius;
            this.circleCenterX = params.circleCenterX;
            this.circleCenterY = params.circleCenterY;
            i2 = params.centerColor;
            i = params.haloColor;
        } else {
            this.maxRadius = -1.0f;
            this.minRadius = -1.0f;
            this.circleCenterX = -1.0f;
            this.circleCenterY = -1.0f;
            i2 = CENTER_COLOR_DEF;
            i = HALO_COLOR_DEF;
        }
        this.paintIndicatorHalo = newColorPaint(i);
        this.paintIndicatorCenter = newColorPaint(i2);
    }

    public void draw(Canvas canvas) {
        if (this.drawSoundLevel || this.drawCenter) {
            canvas.save();
            try {
                if (this.maxRadius < 0.0f || this.circleCenterX < 0.0f || this.circleCenterY < 0.0f) {
                    canvas.getClipBounds(this.bounds);
                }
                canvas.drawColor(0);
                float f = (this.smoothedLevel - this.minMicLevel) / (this.maxMicLevel - this.minMicLevel);
                float width = this.maxRadius < 0.0f ? ((float) this.bounds.width()) / 2.0f : this.maxRadius;
                float f2 = this.minRadius < 0.0f ? 0.5777778f * width : this.minRadius;
                float f3 = 0.8f * f2;
                float f4 = f3 + ((width - f3) * f);
                float width2 = this.circleCenterX < 0.0f ? ((float) this.bounds.width()) / 2.0f : this.circleCenterX;
                float height = this.circleCenterY < 0.0f ? ((float) this.bounds.height()) / 2.0f : this.circleCenterY;
                if (this.drawSoundLevel) {
                    canvas.drawCircle(width2, height, f4, this.paintIndicatorHalo);
                }
                if (this.drawCenter || this.drawSoundLevel) {
                    canvas.drawCircle(width2, height, f2, this.paintIndicatorCenter);
                }
            } finally {
                canvas.restore();
            }
        }
    }

    public boolean setDrawSoundLevel(boolean z) {
        if (this.drawSoundLevel == z) {
            return false;
        }
        this.drawSoundLevel = z;
        if (!z) {
            return true;
        }
        this.minMicLevel = MIN_VALUE;
        this.maxMicLevel = MAX_VALUE;
        this.smoothedLevel = INITIAL_VALUE;
        return true;
    }

    public void setDrawCenter(boolean z) {
        this.drawCenter = z;
    }

    public void setSoundLevel(float f) {
        float abs = Math.abs(f);
        float f2 = this.minMicLevel;
        if (abs < f2) {
            this.minMicLevel = (f2 + abs) / 2.0f;
        }
        float f3 = this.maxMicLevel;
        if (abs > f3) {
            this.maxMicLevel = (f3 + abs) / 2.0f;
        }
        float f4 = (this.smoothedLevel * 0.8f) + (abs * 0.2f);
        this.smoothedLevel = f4;
        float f5 = this.maxMicLevel;
        if (f4 > f5) {
            this.smoothedLevel = f5;
            return;
        }
        float f6 = this.minMicLevel;
        if (f4 < f6) {
            this.smoothedLevel = f6;
        }
    }

    public float getMinRadius() {
        return this.minRadius;
    }

    /* renamed from: ai.api.ui.SoundLevelCircleDrawable$Params */
    public static class Params {
        /* access modifiers changed from: private */
        public final int centerColor;
        public final float circleCenterX;
        public final float circleCenterY;
        /* access modifiers changed from: private */
        public final int haloColor;
        public final float maxRadius;
        public final float minRadius;

        public Params(float f, float f2, float f3, float f4, int i, int i2) {
            this.maxRadius = f;
            this.minRadius = f2;
            this.circleCenterX = f3;
            this.circleCenterY = f4;
            this.centerColor = i;
            this.haloColor = i2;
        }
    }
}
