package demo.lh.com.myeventtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float startX = event.getX();
        if (startX<getWidth()/3){
            View childAt = getChildAt(0);
            childAt.dispatchTouchEvent(event);
        }else if (startX>getWidth()/3 && startX <getWidth()*2/3){
            if (event.getY()<getHeight()/2){
                View childAt = getChildAt(1);

                childAt.dispatchTouchEvent(event);
            }else if (event.getY() > getHeight()/2){
                for (int i = 0; i < getChildCount(); i++){
                    View childAt = getChildAt(i);
                    childAt.dispatchTouchEvent(event);
                }
            }
        }else if (startX > getWidth() *2/3){
            View childAt = getChildAt(2);
            childAt.dispatchTouchEvent(event);
        }
        return true;
    }
}
