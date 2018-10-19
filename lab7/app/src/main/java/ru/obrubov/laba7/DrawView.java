package ru.obrubov.laba7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DrawView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private Map<Integer, Finger> fingers;

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
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
            case MotionEvent.ACTION_MOVE:
                fillPointerCoordinates(event);
                paint();
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                fingers.remove(pointerId);
                break;
        }
        return true;
    }

    private void fillPointerCoordinates(MotionEvent event) {
        for(int pointerId : fingers.keySet()) {
            fingers.get(pointerId).setNextPoint(event.getX(pointerId), event.getY(pointerId));
        }
    }

    private void paint() {
        Canvas canvas = surfaceHolder.lockCanvas();
        for(Finger finger : fingers.values()) {
            if(finger.isReadyToDraw()) {
                List<Point> points = finger.getPoints();
                for(int i = 1; i < points.size(); i++) {
                    canvas.drawLine(
                            points.get(i - 1).x,
                            points.get(i - 1).y,
                            points.get(i).x,
                            points.get(i).y,
                            finger.getPaint());
                }
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
