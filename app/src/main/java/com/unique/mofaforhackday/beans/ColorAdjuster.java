package com.unique.mofaforhackday.beans;

/**
 * Created by ldx on 2014/9/13.
 */
public class ColorAdjuster {

    private int Brightness;
    private int Contrast;
    private int Saturation;

    private int Hue;
    private int RedRate;
    private int GreenRate;
    private int BlueRate;

    public int getBrightness() {
        return Brightness;
    }

    public void setBrightness(int brightness) {
        Brightness = brightness;
    }

    public int getContrast() {
        return Contrast;
    }

    public void setContrast(int contrast) {
        Contrast = contrast;
    }

    public int getSaturation() {
        return Saturation;
    }

    public void setSaturation(int saturation) {
        Saturation = saturation;
    }

    public int getHue() {
        return Hue;
    }

    public void setHue(int hue) {
        Hue = hue;
    }

    public int getRedRate() {
        return RedRate;
    }

    public void setRedRate(int redRate) {
        RedRate = redRate;
    }

    public int getGreenRate() {
        return GreenRate;
    }

    public void setGreenRate(int greenRate) {
        GreenRate = greenRate;
    }

    public int getBlueRate() {
        return BlueRate;
    }

    public void setBlueRate(int blueRate) {
        BlueRate = blueRate;
    }
}
