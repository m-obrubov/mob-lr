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
        Rect rect;
        StringBuilder stringBuilder;

        public DrawView(Context context) {
            super(context);
            paint = new Paint();
            rect = new Rect(100, 200, 200, 300);
            stringBuilder = new StringBuilder();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(10);
            paint.setTextSize(30);

            stringBuilder
                    .append("width = ").append(canvas.getWidth())
                    .append(", height = ").append(canvas.getHeight());
            canvas.drawText(stringBuilder.toString(), 100, 100, paint);

            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(rect, paint);

            paint.setStyle(Paint.Style.STROKE);
            rect.offset(150, 0);
            canvas.drawRect(rect, paint);

            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            rect.offset(150, 0);
            canvas.drawRect(rect, paint);
        }
    }
}
