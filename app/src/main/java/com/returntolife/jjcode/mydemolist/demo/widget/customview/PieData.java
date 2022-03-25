package com.returntolife.jjcode.mydemolist.demo.widget.customview;

import androidx.annotation.NonNull;

public class PieData {
    private String name;
    private float value;
    private float percentage;

    private int color;
    private int angle;

    public PieData(@NonNull String name, @NonNull float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
