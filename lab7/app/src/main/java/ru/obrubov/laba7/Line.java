package ru.obrubov.laba7;

public class Line {
    private static final int INIT_COORDINATE = -1;

    private float startX;
    private float startY;
    private float finishX;
    private float finishY;

    Line() {
        startX = INIT_COORDINATE;
        startY = INIT_COORDINATE;
        finishX = INIT_COORDINATE;
        finishY = INIT_COORDINATE;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getFinishX() {
        return finishX;
    }

    public float getFinishY() {
        return finishY;
    }

    public boolean isReadyToDraw() {
        return startX != INIT_COORDINATE && startY != INIT_COORDINATE;
    }

    public void setNextCoordinates(float nextX, float nextY) {
        if(finishX == nextX && finishY == nextY) {
            return;
        }
        startX = finishX;
        startY = finishY;
        finishX = nextX;
        finishY = nextY;
    }
}
