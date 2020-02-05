package com.jeefersan.imageasciiproject;

import android.graphics.Color;

public class Pixel {
    private int[] pixel;


    public Pixel() {
        this.pixel = new int[3];
    }

    public Pixel(Color color) {
        this.pixel = new int[3];

        this.pixel[0] = color.red(color.toArgb());
        this.pixel[1] = color.green(color.toArgb());
        this.pixel[2] = color.blue(color.toArgb());
    }

    public Pixel(int r, int g, int b) {
        this.pixel = new int[3];
        pixel[0] = r;
        pixel[1] = g;
        pixel[2] = b;
    }

    public int[] getPixel() {
        return pixel;
    }

}
