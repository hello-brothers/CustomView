package com.hellobrothers.youkucaidan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout level3;
    private RelativeLayout level2;
    private RelativeLayout level1;

    private ImageView channel1;
    private ImageView channel2;
    private ImageView channel3;
    private ImageView channel4;
    private ImageView channel5;
    private ImageView channel6;
    private ImageView channel7;
    private ImageView imgSearch;
    private ImageView imgMyyouku;
    private ImageView imgMenu;
    private ImageView imgHome;

    /**
     * 是否显示第三圆环
     * true:显示
     * false:不显示
     */
    private boolean isShowLevel3 = true;

    /**
     * 是否显示第二圆环
     * true:显示
     * false:不显示
     */
    private boolean isShowLevel2 = true;

    /**
     * 是否显示第一圆环
     * true:显示
     * false:不显示
     */
    private boolean isShowLevel1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);

        channel1 = (ImageView) findViewById(R.id.channel1);
        channel2 = (ImageView) findViewById(R.id.channel2);
        channel3 = (ImageView) findViewById(R.id.channel3);
        channel4 = (ImageView) findViewById(R.id.channel4);
        channel5 = (ImageView) findViewById(R.id.channel5);
        channel6 = (ImageView) findViewById(R.id.channel6);
        channel7 = (ImageView) findViewById(R.id.channel7);
        imgSearch = (ImageView) findViewById(R.id.img_search);
        imgMyyouku = (ImageView) findViewById(R.id.img_myyouku);
        imgMenu = (ImageView) findViewById(R.id.img_menu);
        imgHome = (ImageView) findViewById(R.id.img_home);

        MyOnClickListener myOnClickListener = new MyOnClickListener();
        //设置点击事件
        imgHome.setOnClickListener(myOnClickListener);
        imgMenu.setOnClickListener(myOnClickListener);

    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_home:
                    //如果二级三级菜单全部显示，则全部隐藏
                    if (isShowLevel2){
                        isShowLevel2 = false;
                        Tools.hideView(level2);
                        if (isShowLevel3){
                            isShowLevel3 = false;
                            Tools.hideView(level3, 200);

                        }
                    }else {
                        //如果二三级菜单全部隐藏，则显示二级菜单
                        isShowLevel2 = true;
                        Tools.showView(level2);
                    }
                    break;
                case R.id.img_menu:
                    if (isShowLevel3){
                        isShowLevel3 = false;
                        Tools.hideView(level3);
                    }else {
                        isShowLevel3 = true;
                        Tools.showView(level3);
                    }
                    break;
                case R.id.level3:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU){
            if (isShowLevel1){
                isShowLevel1 = false;
                Tools.hideView(level1);
                if (isShowLevel2) {
                    isShowLevel2 = false;
                    Tools.hideView(level2, 200);
                    if (isShowLevel3){
                        isShowLevel3 = false;
                        Tools.hideView(level3, 400);
                    }
                }
            }else {
                isShowLevel1 = true;
                Tools.showView(level1);
                isShowLevel2 = true;
                Tools.showView(level2, 200);
            }
        }
        return true;
    }
}
