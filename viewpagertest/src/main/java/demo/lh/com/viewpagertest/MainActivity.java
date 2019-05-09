package demo.lh.com.viewpagertest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout point_group;
    private TextView tv_text;
    private ArrayList<ImageView> imageViews;
    private int prePosition = 0;
    private final int[] images = new int[]{R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e};
    private final String[] imageTitles = new String[]{"尚硅谷拔河争霸赛",
            "凝聚你我，放飞梦想",
            "抱歉没座位了",
            "7月就业名单全部曝光",
            "平均起薪1111元"};
    private static final String TAG = MainActivity.class.getSimpleName();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int item = viewPager.getCurrentItem();
            viewPager.setCurrentItem(item+1);
            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };
    private boolean isDragging = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        viewPager.setAdapter(new MyAdapter());
        viewPager.setCurrentItem(Integer.MAX_VALUE/2 - Integer.MAX_VALUE/2%imageViews.size());
        handler.sendEmptyMessageDelayed(0, 3000);

        viewPager.addOnPageChangeListener(new MyAdapterChangeListener());
    }

    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < images.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(images[i]);
            //添加到集合
            imageViews.add(imageView);

            //添加点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i == 0){
                point.setEnabled(true);
            }else {
                params.leftMargin = 8;
                point.setEnabled(false);
            }
            point.setLayoutParams(params);
            point_group.addView(point);

        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        point_group = findViewById(R.id.ll_point_group);
        tv_text = findViewById(R.id.title);
    }

    class MyAdapterChangeListener implements  ViewPager.OnPageChangeListener{

        /**
         *
         * @param i 当前位置
         * @param v 滑动页面的百分比
         * @param i1 在页面上滑动的像素
         */
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        /**
         * 当某个页面被选中的回调
         * @param i
         */
        @Override
        public void onPageSelected(int i) {
            int realPosition = i%imageViews.size();
            tv_text.setText(imageTitles[realPosition]);
            point_group.getChildAt(prePosition).setEnabled(false);
            point_group.getChildAt(realPosition).setEnabled(true);
            prePosition = realPosition;
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            if (i == ViewPager.SCROLL_STATE_DRAGGING){
                isDragging = true;
                handler.removeCallbacksAndMessages(null);
            }else if (i == ViewPager.SCROLL_STATE_SETTLING){

            }else if (i == ViewPager.SCROLL_STATE_IDLE && isDragging){
                isDragging = false;
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0, 4000);
            }
        }
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.i(TAG, "instantiateItem == " + position);
            ImageView imageView = imageViews.get(position%imageViews.size());
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_CANCEL:

                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0, 4000);
                            break;
                    }
                    return false;
                }
            });
            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag()%imageViews.size();
                    String imageTitle = imageTitles[position];
                    Toast.makeText(MainActivity.this, imageTitle, Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            Log.i(TAG, "destroyItem == " + position);
            container.removeView((View) object);
        }
    }
}
