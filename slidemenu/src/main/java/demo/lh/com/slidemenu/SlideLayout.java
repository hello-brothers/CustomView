package demo.lh.com.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

public class SlideLayout extends FrameLayout {

    private View contentView;
    private View menuView;
    private int viewHeight;
    private int menuWidth;
    private int contentWidth;
    private float startX;
    private float startY;
    private float downX;
    private float downY;
    private Scroller scroller;
    public onStateChangeListener onStateChangeListener;

    public SlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        contentView.layout(0, 0, contentWidth, viewHeight);
        menuView.layout(contentWidth, 0, contentWidth+menuWidth, viewHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                downX = event.getX();
                downY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                float eventX = event.getX();
                float eventY = event.getY();
                float distanceX = eventX - startX;

                int toScroll = (int) (getScrollX() - distanceX);
                if (toScroll<0){
                    toScroll = 0;
                }else if (toScroll > menuWidth){
                    toScroll = menuWidth;
                }
                scrollTo(toScroll, getScrollY());
                startY = event.getY();
                startX = event.getX();
                float DX = Math.abs(eventX - downX);
                float DY = Math.abs(eventY - downY);
                if (DX > DY && DX >8){
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:

                if (getScrollX() < menuWidth/2){
                    //关闭
                    closeMenu();
                }else {
                    //打开
                    openMenu();

                }
                break;
        }
        return true;
    }

    public void closeMenu() {
        int distanceX = 0 - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        invalidate();
        if (onStateChangeListener != null){
            onStateChangeListener.onClose(this);
        }

    }

    public void openMenu() {
        int distanceX = menuWidth - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        invalidate();
        if (onStateChangeListener != null){
            onStateChangeListener.onOpen(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            int currX = scroller.getCurrX();
            scrollTo(currX, 0);
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
         super.onInterceptTouchEvent(ev);
         boolean intercept = false;
         switch (ev.getAction()){
             case MotionEvent.ACTION_DOWN:
                 startX = ev.getX();
                 startY = ev.getY();
                 if (onStateChangeListener != null){
                     onStateChangeListener.onDown(this);
                 }
                 break;
             case MotionEvent.ACTION_MOVE:
                 float touchX = ev.getX();
                 if (Math.abs(touchX-startX )> 8){
                     intercept = true;
                 }
                 break;
             case MotionEvent.ACTION_UP:
                 break;
         }
         return intercept;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }
    public interface onStateChangeListener{

        void onClose(SlideLayout slideLayout);

        void onDown(SlideLayout slideLayout);

        void onOpen(SlideLayout slideLayout);
    }

    public void setOnStateChangeListener(SlideLayout.onStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }
}
