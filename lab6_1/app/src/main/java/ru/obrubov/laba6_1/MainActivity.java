package ru.obrubov.laba6_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends View {
        Paint paint;
        RectF rectF;

        float[] points;
        float[] points1;

        public DrawView(Context context) {
            super(context);
            paint = new Paint();
            rectF = new RectF(700, 100, 800, 150);

            points = new float[] {100, 50, 150, 100, 150, 200, 50, 200, 50, 100};
            points1 = new float[] {300, 200, 600, 200, 300, 300, 600, 300, 400, 100, 400, 400, 500, 100, 500, 400};
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);
            canvas.drawPoints(points, paint);
            canvas.drawLines(points1, paint);

            paint.setColor(Color.GREEN);
            canvas.drawRoundRect(rectF, 20, 20, paint);

            rectF.offset(0, 150);
            canvas.drawOval(rectF, paint);

            rectF.offsetTo(900, 100);
            rectF.inset(0, -25);
            canvas.drawArc(rectF, 90, 270, true, paint);

            rectF.offset(0, 150);
            canvas.drawArc(rectF, 90, 270, false, paint);

            paint.setStrokeWidth(3);
            canvas.drawLine(150, 450, 150, 600, paint);

            paint.setColor(Color.BLUE);
            paint.setTextSize(30);
            canvas.drawText("Text Left",150, 500, paint);

            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Text Center",150, 525, paint);

            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Text Right",150, 550, paint);
        }
    }
}
