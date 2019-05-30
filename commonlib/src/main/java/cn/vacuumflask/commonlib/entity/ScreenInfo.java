package cn.vacuumflask.commonlib.entity;

public class ScreenInfo {
    private float width;//像素单位
    private float height;//像素单位
    private float densityDpi;

    public ScreenInfo() {
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDensityDpi() {
        return densityDpi;
    }

    public void setDensityDpi(float densityDpi) {
        this.densityDpi = densityDpi;
    }

    @Override
    public String toString() {
        return "ScreenInfo{" +
                "width=" + width +
                ", height=" + height +
                ", densityDpi=" + densityDpi +
                '}';
    }
}
