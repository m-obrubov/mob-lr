package ru.obrubov.laba7;

import android.graphics.Paint;

public class DrawInfo {
    private Paint paint;
    private Point oldPoint;
    private Point newPoint;

    DrawInfo(Point oldPoint, Point newPoint, Paint paint) {
        this.paint = paint;
        this.oldPoint = oldPoint;
        this.newPoint = newPoint;
    }

    public Paint getPaint() {
        return paint;
    }

    public Point getOldPoint() {
        return oldPoint;
    }

    public Point getNewPoint() {
        return newPoint;
    }
}
