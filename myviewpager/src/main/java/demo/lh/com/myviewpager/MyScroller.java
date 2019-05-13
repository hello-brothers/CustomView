package demo.lh.com.myviewpager;

import android.util.Log;

public class MyScroller {
    private float startX;
    private float startY;
    private int distanceX;
    private int distanceY;
    private long startTime;
    private long totalTime = 500;
    private float currentX;

    public float getCurrX() {
        return currentX;
    }

    private boolean isFinished;

    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = System.currentTimeMillis();
        this.isFinished = false;
    }

    public boolean computeScrollOffset(){
        if (isFinished){
            return false;
        }
        long endTime = System.currentTimeMillis();
        long passTime = endTime - startTime;
        Log.i("-time---", passTime + "");
        if (passTime<totalTime){
            long speed = distanceX/totalTime;
            long tempDistance = passTime*distanceX/totalTime;
            Log.i("-tempDistance---", tempDistance + "");

            currentX = tempDistance + startX;
        }else {
            currentX = distanceX +startX;
            isFinished = true;
        }
        return true;
    }


}
