package ru.obrubov.laba6_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

        public DrawView(Context context) {
            super(context);
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(10);
            paint.setTextSize(30);

            int centerX = canvas.getWidth() / 2;
            int centerY = canvas.getHeight() / 2;
            int startDegree = 0;
            int anglesCount = 6;
            int radius = 300;
            float arcAngle = ((float) Math.PI *2) / anglesCount;

            canvas.translate(centerX, centerY);
            canvas.rotate(startDegree);

            Path path = new Path();
            for(int i = 0; i <= anglesCount; i++) {
                path.lineTo(radius * (float) Math.cos(arcAngle * i), radius * (float) Math.sin(arcAngle * i));
            }
            canvas.drawPath(path, paint);
        }
    }
}
