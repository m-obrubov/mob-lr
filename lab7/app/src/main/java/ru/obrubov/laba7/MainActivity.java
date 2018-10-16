package ru.obrubov.laba7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
        DrawView drawView = new DrawView(this);
        drawView.setOnTouchListener(drawView);
        setContentView(drawView);
    }

    class DrawView extends SurfaceView implements View.OnTouchListener, SurfaceHolder.Callback {

        private DrawThread drawThread;
        private DrawInfoTransfer drawInfoTransfer;
        private Random random;

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);

            random = new Random();
            drawInfoTransfer = new DrawInfoTransfer();
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int actionMask = event.getActionMasked();
            int pointerIndex = event.getActionIndex();
            int pointerId = event.getPointerId(pointerIndex);
            switch (actionMask) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    Paint paint = new Paint();
                    paint.setStrokeWidth(10);
                    paint.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    drawInfoTransfer.addMultiTouchPaint(pointerId, paint);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawInfoTransfer.setEvent(event);
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    drawInfoTransfer.removeMultiTouchPaint(pointerId);
                    break;
            }
            return false;
        }

        @Override
        public boolean performClick() {
            return super.performClick();
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder(), this.drawInfoTransfer);
            drawThread.setRunning(true);
            drawThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            boolean retry = true;
            drawThread.setRunning(false);
            while(retry) {
                try {
                    drawThread.join();
                    retry = false;
                } catch (InterruptedException ignored) { }
            }
        }

        class DrawThread extends Thread {
            private boolean isRunning = false;
            private SurfaceHolder surfaceHolder;
            private DrawInfoTransfer drawInfoTransfer;
            private MotionEvent oldMotionEvent;
            private Map<Integer, Point> pointerCoords;

            DrawThread(SurfaceHolder surfaceHolder, DrawInfoTransfer drawInfoTransfer) {
                this.surfaceHolder = surfaceHolder;
                this.drawInfoTransfer = drawInfoTransfer;
                this.pointerCoords = new HashMap<>();
            }

            void setRunning(boolean running) {
                isRunning = running;
            }

            @Override
            public void run() {
                Canvas canvas;
                while (isRunning) {
                    canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        if (canvas == null) {
                            continue;
                        }
                        DrawInfo drawInfo = getDrawInfo();
                        if(drawInfo != null) {
                            canvas.drawLine(
                                    drawInfo.getOldPoint().getX(),
                                    drawInfo.getOldPoint().getY(),
                                    drawInfo.getNewPoint().getX(),
                                    drawInfo.getNewPoint().getY(),
                                    drawInfo.getPaint()
                            );
                        }
                    } finally {
                        if (canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }

            private DrawInfo getDrawInfo() {
                MotionEvent event = drawInfoTransfer.getEvent();
                if(oldMotionEvent == null || event.equals(oldMotionEvent)) {
                    oldMotionEvent = event;
                    return null;
                }
                DrawInfo result = null;
                for(Integer pointerId : pointerCoords.keySet()) {
                    Point savedPoint = pointerCoords.get(pointerId);
                    if(event.getX(pointerId) != savedPoint.getX() || event.getY(pointerId) != savedPoint.getY()) {
                        Point newPoint = new Point(event.getX(pointerId), event.getY(pointerId));
                        pointerCoords.put(pointerId, newPoint);
                        result = new DrawInfo(savedPoint, newPoint, drawInfoTransfer.getMultiTouchPaint(pointerId));
                        break;
                    }
                }
                oldMotionEvent = drawInfoTransfer.getEvent();
                return result;
            }
        }
    }
}
