package ru.obrubov.laba7;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Finger {
    private List<Point> points;
    private Paint paint;

    Finger() {
        this.points = new ArrayList<>();
        Random random = new Random();
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        paint.setStyle(Paint.Style.STROKE);
        this.paint = paint;
    }

    public List<Point> getPoints() {
        return points;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setNextPoint(float nextX, float nextY) {
        int intNextX = (int) nextX;
        int intNextY = (int) nextY;
        if(points.size() > 0 && points.get(points.size() - 1).equals(intNextX, intNextY)) {
            return;
        }
        points.add(new Point(intNextX, intNextY));
    }

    public boolean isReadyToDraw() {
        return points.size() >= 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Finger finger = (Finger) o;
        return Objects.equals(points, finger.points) &&
                Objects.equals(paint, finger.paint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(points, paint);
    }
}
