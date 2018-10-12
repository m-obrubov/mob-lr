package ru.obrubov.laba6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends SurfaceView implements SurfaceHolder.Callback {

        private DrawThread drawThread;

        public DrawView(Context context) {
            super(context);
            getHolder().addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            drawThread = new DrawThread(getHolder());
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
                } catch (InterruptedException ignored) {

                }
            }
        }

        class DrawThread extends Thread {
            private boolean isRunning = false;
            private SurfaceHolder surfaceHolder;

            DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
            }

            void setRunning(boolean running) {
                isRunning = running;
            }

            @Override
            public void run() {
                Canvas canvas;
                Paint paint = new Paint();
                paint.setTextSize(50f);
                paint.setColor(Color.BLACK);
                while(isRunning) {
                    canvas = null;
                    try {
                        canvas = surfaceHolder.lockCanvas(null);
                        if(canvas == null) {
                            continue;
                        }
                        canvas.drawColor(Color.GREEN);
                        canvas.drawText(new Date().toString(), 0, getWidth() / 2, paint);
                    } finally {
                        if(canvas != null) {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                    }
                }
            }
        }
    }


}
