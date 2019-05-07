package com.hellobrothers.youkucaidan;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

public class Tools {

    public static void hideView(ViewGroup view) {
        hideView(view, 0);
    }

    public static void showView(ViewGroup view) {
        showView(view, 0);
    }

    public static void hideView(ViewGroup view, int startOffset) {
        RotateAnimation ra = new RotateAnimation(0, 180, view.getWidth()/2, view.getHeight());
        ra.setDuration(500);//设置动画持续时间
        ra.setFillAfter(true);//动画停留在播放完成状态
        ra.setStartOffset(startOffset);
        view.startAnimation(ra);
        for (int i = 0; i < view.getChildCount(); i++){
            View children = view.getChildAt(i);
            children.setEnabled(false);
        }
    }

    public static void showView(ViewGroup view, int startOffset) {
        RotateAnimation ra = new RotateAnimation(180, 0, view.getWidth()/2, view.getHeight());
        ra.setDuration(500);//设置动画持续时间
        ra.setFillAfter(true);//动画停留在播放完成状态
        ra.setStartOffset(startOffset);
        view.startAnimation(ra);
        for (int i = 0; i < view.getChildCount(); i++){
            View children = view.getChildAt(i);
            children.setEnabled(true);
        }
    }
}
