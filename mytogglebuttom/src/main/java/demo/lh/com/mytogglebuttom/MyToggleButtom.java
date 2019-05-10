package demo.lh.com.mytogglebuttom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MyToggleButtom extends View implements View.OnClickListener {

    private Bitmap backgroundBitmap;
    private Bitmap slideBitmap;
    private int slideLeftMax;
    private int slideLeft;
    private Paint paint;
    private boolean isOpen = false;
    private float startX;
    /**
     * true:点击事件失效，滑动事件不失效
     * false:点击事件不失效，滑动事件失效
     */
    private boolean isEnable = true;

    /**
     * 将会用构造方法实现该类，如果没有就会崩溃
     * @param context
     * @param attrs
     */
    public MyToggleButtom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);//设置抗锯齿

        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.switch_background);
        slideBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.slide_button);
        slideLeftMax = backgroundBitmap.getWidth() - slideBitmap.getWidth();

        setOnClickListener(this);

    }


    /**
     * 视图的测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    /**
     * 绘制
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
        canvas.drawBitmap(slideBitmap, slideLeft, 0, paint);
    }

    @Override
    public void onClick(View view) {
        if (isEnable){
            isOpen = !isOpen;
            flushView();
        }

    }

    private void flushView() {
        if (isOpen){
            slideLeft = slideLeftMax;
        }else {
            slideLeft = 0;
        }
        invalidate();//会导致onDraw重新执行
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
         super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isEnable = true;
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float moveX = endX - startX;
                slideLeft += moveX;
                //屏蔽非法值
                if (slideLeft<0){
                    slideLeft = 0;
                }else if (slideLeft >slideLeftMax){
                    slideLeft = slideLeftMax;
                }
                invalidate();
                startX = event.getX();
                //取绝对值
                if (Math.abs(moveX) >2){
                    isEnable = false;

                }
                break;
            case MotionEvent.ACTION_UP:
                if (slideLeft > slideLeftMax/2 ){
                    isOpen = true;
                }else {
                    isOpen = false;
                }
                flushView();
                break;
        }
        return true;
    }


}
