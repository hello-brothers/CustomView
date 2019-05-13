package demo.lh.com.myattribute;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyAttributeView extends View {

    private String name;
    private int age;
    private Bitmap bitmap;

    public MyAttributeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性有三种方法
        //1、用空间命名获取属性
        String my_name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_name");
        String my_age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_age");
        String my_gb = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_bg");
        Log.i("--attribute--", "my_name = " + my_name + "my_age = " + my_age +"my_bg = " + my_gb);

        //2、通过遍历属性集合得到
        for (int i = 0; i <attrs.getAttributeCount(); i++){
            Log.i("---attribute--", attrs.getAttributeName(i) + "-" + attrs.getAttributeValue(i));
        }

        //3、通过系统工具获取
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyAttributeView);
        for (int i = 0; i < typedArray.getIndexCount(); i++){
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.MyAttributeView_my_name:
                    name = typedArray.getString(i);
                    break;
                case R.styleable.MyAttributeView_my_age:
                    age = typedArray.getInt(i, 0);
                    break;
                case R.styleable.MyAttributeView_my_bg:
                    Drawable bg = typedArray.getDrawable(i);
                    BitmapDrawable bd = (BitmapDrawable) bg;
                    bitmap = bd.getBitmap();
                    break;
            }
        }
        //记得回收
        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Log.i("---", name + age);
        canvas.drawText(name + "-" + age, 50, 50, paint);
        canvas.drawBitmap(bitmap, 0, 50, paint);
    }
}
