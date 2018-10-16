package ru.obrubov.laba7;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.Objects;
import java.util.Random;

public class Finger {
    private Line line;
    private Paint paint;

    Finger() {
        this.line = new Line();
        Random random = new Random();
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        paint.setStyle(Paint.Style.STROKE);
        this.paint = paint;
    }

    public Line getLine() {
        return line;
    }

    public Paint getPaint() {
        return paint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Finger finger = (Finger) o;
        return Objects.equals(line, finger.line) &&
                Objects.equals(paint, finger.paint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, paint);
    }
}
