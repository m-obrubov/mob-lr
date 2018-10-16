package ru.obrubov.laba7;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;

class DrawView extends SurfaceView {
    private static final int BACK_COLOR = Color.WHITE;
    private static final int MAX_LINES = 30;

    private SurfaceHolder surfaceHolder;
    private Map<Integer, Finger> fingers;
    private int linesCount;

    public DrawView(Context context) {
        super(context);
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
                linesCount++;
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
            Line line = fingers.get(pointerId).getLine();
            line.setNextCoordinates(event.getX(pointerId), event.getY(pointerId));
        }
    }

    private void paint() {
        Canvas canvas = surfaceHolder.lockCanvas();
//        if(linesCount >= MAX_LINES) {
//            canvas.drawColor(BACK_COLOR);
//            linesCount = 0;
//        }
        for(Finger drawFinger : fingers.values()) {
            Line line = drawFinger.getLine();
            if(line.isReadyToDraw()) {
                canvas.drawLine(
                        line.getStartX(),
                        line.getStartY(),
                        line.getFinishX(),
                        line.getFinishY(),
                        drawFinger.getPaint());
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
