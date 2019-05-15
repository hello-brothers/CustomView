package demo.lh.com.quickindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyIndex extends View {
    private String[] words = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int itemWidth;
    private int itemHeigth;
    private Paint paint;
    private int touchIndex = -1;
    private OnIndexChangeListener listener;

    public MyIndex(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTextSize(28);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = getMeasuredWidth();
        itemHeigth = getMeasuredHeight()/26;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i <words.length; i++){
            if (i == touchIndex){
                paint.setColor(Color.GRAY);
            }else {
                paint.setColor(Color.BLACK);
            }
            String word = words[i];
            Rect rect = new Rect();
            paint.getTextBounds(word, 0, 1, rect);
            int wordWidth = rect.width();
            int wordHeight = rect.height();
            float wordX = (itemWidth - wordWidth) / 2;
            float wordY = (itemHeigth + wordHeight)/2 +i * itemHeigth;
            canvas.drawText(word, wordX, wordY, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float touchY = event.getY();
                int index = (int) (touchY/itemHeigth);
                if (index!=touchIndex){
                    touchIndex = index;
                    if (listener!=null && touchIndex < words.length){
                        listener.onIndexchange(words[touchIndex]);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                invalidate();
                break;
        }
        return true;
    }
    public interface OnIndexChangeListener{
        void onIndexchange(String word);
    }
    public void setOnIndexChangeListener(OnIndexChangeListener listener){
        this.listener = listener;
    }
}
