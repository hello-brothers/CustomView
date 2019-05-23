package demo.lh.com.wavetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class WaveView extends View {
    ArrayList<Circle> circles = new ArrayList<>();
    private boolean isRunning = false;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flushData();
            invalidate();
            if (isRunning){
                sendEmptyMessageDelayed(0, 50);
            }
        }
    };
    public static double minDistance = 20;

    private void flushData() {
        for (int i = 0; i < circles.size(); i++){
            Circle circle = circles.get(i);
            circle.radius += 5;

            int alpha = circle.paint.getAlpha();
            if (alpha == 0){
                circles.remove(i);
            }
            alpha = alpha -5 < 0 ? 0 : alpha -5;

            circle.paint.setAlpha(alpha);
            circle.paint.setStrokeWidth(circle.radius/3);


        }
        if (circles.size() == 0){
            isRunning = false;
        }
    }

    private int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < circles.size(); i++){
            Circle circle = circles.get(i);
            canvas.drawCircle(circle.x, circle.y, circle.radius/3, circle.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float touchX = event.getX();
                float touchY = event.getY();
                idFirstaddCircle(touchX, touchY);
                break;
        }
        invalidate();
        return true;
    }

    private void idFirstaddCircle(float touchX, float touchY) {
        if (circles.size() == 0){
            addCircle(touchX, touchY);
            isRunning = true;
            handler.sendEmptyMessage(0);
        }else {
            Circle circle = circles.get(circles.size() - 1);
            float x = circle.x;
            float y = circle.y;
            if (Math.sqrt(Math.pow((x-touchX), 2)+ Math.pow((x-touchX), 2)) > minDistance){
                addCircle(touchX, touchY);
            }
        }

    }
    private void addCircle(float touchX, float touchY){
        Circle circle = new Circle();
        circle.x = touchX;
        circle.y = touchY;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(colors[(int) (Math.random() *4)]);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circle.radius/3);
        circle.paint = paint;
        circles.add(circle);
    }

    class Circle{
        public float radius;
        public Paint paint;
        public float x;
        public float y;

    }
}
