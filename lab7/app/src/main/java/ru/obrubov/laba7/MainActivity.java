package ru.obrubov.laba7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends SurfaceView {
        private Path path;
        private SurfaceHolder surfaceHolder;
        private Random random;
        private Paint paint;



        public DrawView(Context context) {
            super(context);
            surfaceHolder = getHolder();
            random = new Random();
            paint = new Paint();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int actionMask = event.getActionMasked();
//            int pointerIndex = event.getActionIndex();
//            int pointerId = event.getPointerId(pointerIndex);

            switch (actionMask) {
                case MotionEvent.ACTION_DOWN:
//                case MotionEvent.ACTION_POINTER_DOWN:
                    path = new Path();
                    paint.setStrokeWidth(10);
                    paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    paint.setStyle(Paint.Style.STROKE);
//                    drawInfoTransfer.addMultiTouchPaint(pointerId, paint);
                    path.moveTo(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
//                    drawInfoTransfer.setEvent(event);
//                    break;
//                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_UP:
//                    drawInfoTransfer.removeMultiTouchPaint(pointerId);
                    path.lineTo(event.getX(), event.getY());
                    break;
            }

            if(path != null) {
                Canvas canvas = surfaceHolder.lockCanvas();
//                canvas.drawColor(Color.GREEN);
                canvas.drawPath(path, paint);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            return true;
        }
    }
}
