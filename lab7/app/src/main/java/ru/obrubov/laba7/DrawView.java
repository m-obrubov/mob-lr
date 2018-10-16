package ru.obrubov.laba7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class DrawView extends SurfaceView {
    private static final int BACK_COLOR = Color.WHITE;

    private SurfaceHolder surfaceHolder;
    private Random random;
    private Map<Integer, Finger> fingers;

    public DrawView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        random = new Random();
        fingers = new HashMap<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionMask = event.getActionMasked();
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (actionMask) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                fingers.put(pointerId, new Finger());
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                fingers.remove(pointerId);
                break;
        }
        fillPointerCoords(event);
        paint();
        return true;
    }

    private void fillPointerCoords(MotionEvent event) {
        for(int pointerId : fingers.keySet()) {
            Path path = fingers.get(pointerId).getPath();
            if(path.isEmpty()) {
                path.moveTo(event.getX(pointerId), event.getY(pointerId));
            } else {
                path.lineTo(event.getX(pointerId), event.getY(pointerId));
            }
        }
    }

    private void paint() {
        Canvas canvas = surfaceHolder.lockCanvas();
        for(Finger drawFinger : fingers.values()) {
            Path path = drawFinger.getPath();
            if(path != null) {
                canvas.drawPath(path, drawFinger.getPaint());
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
