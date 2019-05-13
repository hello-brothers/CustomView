package demo.lh.com.myviewpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    private MyViewPager myViewPager;
    private int[] imgIds = {R.drawable.a1,R.drawable.a2,R.drawable.a3,R.drawable.a4,R.drawable.a5,R.drawable.a6};
    private RadioGroup rg_main;
    private OnPagerChangListener OnPagerChangListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myViewPager = findViewById(R.id.my_viewpager);
        rg_main = findViewById(R.id.rg_main);

        //将图片添加到pager里面
        for (int i = 0; i <imgIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imgIds[i]);
            myViewPager.addView(imageView);
        }
        //动态添加RadioButtom
        for (int i = 0; i <myViewPager.getChildCount(); i++){
            RadioButton rb = new RadioButton(this);
            rb.setId(i);
            rg_main.addView(rb);
            if (i == 0){
                rb.setChecked(true);
            }
        }

        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                myViewPager.scrollToPager(i);
            }
        });

        OnPagerChangListener = new PagerListener();
        myViewPager.setOnPagerChangListener(OnPagerChangListener);

    }

    /**
     * 页面改变回调改变radiogroup的状态
     */
    class PagerListener implements OnPagerChangListener {

        @Override
        public void onScrollToPager(int position) {
            rg_main.check(position);
        }
    }



}
