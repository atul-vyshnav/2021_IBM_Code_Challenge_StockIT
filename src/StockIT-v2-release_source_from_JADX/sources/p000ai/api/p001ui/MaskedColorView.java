package p000ai.api.p001ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;
import p000ai.api.C0001R;

/* renamed from: ai.api.ui.MaskedColorView */
public class MaskedColorView extends ImageView {
    private ColorStateList colorStateList = null;

    public MaskedColorView(Context context) {
        super(context, (AttributeSet) null);
    }

    public MaskedColorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 16842866);
        configure(attributeSet);
    }

    public MaskedColorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        configure(attributeSet);
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        setColorFilter(getCurrentColor(getDrawableState()), PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= 11) {
            jumpDrawablesToCurrentState();
        }
    }

    public void setColorStateList(ColorStateList colorStateList2) {
        this.colorStateList = colorStateList2;
    }

    private int getCurrentColor(int[] iArr) {
        ColorStateList colorStateList2 = this.colorStateList;
        if (colorStateList2 == null) {
            return -65281;
        }
        return colorStateList2.getColorForState(iArr, colorStateList2.getDefaultColor());
    }

    private void configure(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C0001R.styleable.MaskedColorView);
            try {
                ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(C0001R.styleable.MaskedColorView_mainColor);
                if (colorStateList2 != null) {
                    this.colorStateList = colorStateList2;
                }
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
    }

    /* access modifiers changed from: protected */
    public String getDebugState() {
        StringBuilder sb = new StringBuilder();
        sb.append("====\ncsl is ");
        sb.append(this.colorStateList != null ? "NOT" : "");
        sb.append(" null");
        return sb.toString();
    }
}
