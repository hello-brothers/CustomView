package demo.lh.com.myviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

public class MyViewPager extends ViewGroup {


    private GestureDetector gestureDetector;
    private int currentIndex;
    private Scroller scroller;
    private OnPagerChangListener mOnPagerChangListener;
    private float downX;
    private float downY;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        scroller = new Scroller(context);
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                Toast.makeText(context, "长按", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i("---", distanceX + "");
                scrollBy((int) distanceX, 0);
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Toast.makeText(context, "双击", Toast.LENGTH_SHORT).show();
                return super.onDoubleTap(e);
            }
        });
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        for (int j = 0; j < getChildCount(); j++) {
            Log.i("-----", "size = " + getChildCount());
            View childView = getChildAt(j);
            childView.layout(j * getWidth(), 0, (j + 1) * getWidth(), getHeight());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false;
        gestureDetector.onTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX = Math.abs(endX - downX);
                float distanceY = Math.abs(endY - downY);
                if (distanceX > distanceY && distanceX > 5){
                    result = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return result;
    }

    float startX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                int tempIndex = currentIndex;
                if (endX - startX > getWidth() / 2) {
                    //上一个页面
                    tempIndex--;
                } else if (startX - endX > getWidth() / 2) {
                    //show next
                    tempIndex++;
                }
                scrollToPager(tempIndex);
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i <getChildCount(); i++){
            View childAt = getChildAt(i);
            childAt.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }

        currentIndex = tempIndex;
        if (mOnPagerChangListener != null){
            mOnPagerChangListener.onScrollToPager(currentIndex);
        }
//        scrollTo(currentIndex * getWidth(), 0);
        int distanceX = currentIndex * getWidth() - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {
//        super.computeScroll();
        if (scroller.computeScrollOffset()){
            float currentX = scroller.getCurrX();
            scrollTo((int) currentX, 0);
            invalidate();
        }
    }

    public void setOnPagerChangListener(OnPagerChangListener onPagerChangListener){
        mOnPagerChangListener = onPagerChangListener;
    }
}
