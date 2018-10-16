package ru.obrubov.laba7;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Objects;
import java.util.Random;

public class Finger {
    private Path path;
    private Paint paint;

    Finger() {
        this.path = new Path();
        Random random = new Random();
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        paint.setStyle(Paint.Style.STROKE);
        this.paint = paint;
    }

    public Path getPath() {
        return path;
    }

    public Paint getPaint() {
        return paint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Finger finger = (Finger) o;
        return Objects.equals(path, finger.path) &&
                Objects.equals(paint, finger.paint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, paint);
    }
}
