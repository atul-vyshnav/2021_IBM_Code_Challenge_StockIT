package org.unimodules.interfaces.barcodescanner;

import java.util.List;

public class BarCodeScannerResult {
    private List<Integer> mCornerPoints;
    private int mReferenceImageHeight;
    private int mReferenceImageWidth;
    private int mType;
    private String mValue;

    public BarCodeScannerResult(int i, String str, List<Integer> list, int i2, int i3) {
        this.mType = i;
        this.mValue = str;
        this.mCornerPoints = list;
        this.mReferenceImageHeight = i2;
        this.mReferenceImageWidth = i3;
    }

    public int getType() {
        return this.mType;
    }

    public String getValue() {
        return this.mValue;
    }

    public List<Integer> getCornerPoints() {
        return this.mCornerPoints;
    }

    public void setCornerPoints(List<Integer> list) {
        this.mCornerPoints = list;
    }

    public int getReferenceImageHeight() {
        return this.mReferenceImageHeight;
    }

    public void setReferenceImageHeight(int i) {
        this.mReferenceImageHeight = i;
    }

    public int getReferenceImageWidth() {
        return this.mReferenceImageWidth;
    }

    public void setReferenceImageWidth(int i) {
        this.mReferenceImageWidth = i;
    }
}
