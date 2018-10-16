package ru.obrubov.laba7;

import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.HashMap;
import java.util.Map;

public class DrawInfoTransfer {
    private Map<Integer, Paint> multiTouchPaints;
    private MotionEvent event;

    DrawInfoTransfer() {
        multiTouchPaints = new HashMap<>();
    }

    public void addMultiTouchPaint(int pointerId, Paint paint) {
        multiTouchPaints.put(pointerId, paint);
    }

    public void removeMultiTouchPaint(int pointerId) {
        multiTouchPaints.remove(pointerId);
    }

    public Paint getMultiTouchPaint(Integer key) {
        return multiTouchPaints.get(key);
    }

    public MotionEvent getEvent() {
        return event;
    }

    public void setEvent(MotionEvent event) {
        this.event = event;
    }
}
