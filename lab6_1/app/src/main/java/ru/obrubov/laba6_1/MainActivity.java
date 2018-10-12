package ru.obrubov.laba6_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

        public DrawView(Context context) {
            super(context);
            paint = new Paint();
            rect = new Rect();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);
            canvas.drawPoint(50, 50, paint);
            canvas.drawLine(100, 100, 500, 50, paint);
            canvas.drawCircle(100, 200, 50, paint);
            canvas.drawRect(200, 150, 400, 200, paint);

            rect.set(250, 300, 350, 500);
            canvas.drawRect(rect, paint);
        }
    }
}
