package demo.lh.com.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.img);
    }

    public void move(View view) {
        //第一种方式
        /*ObjectAnimator rotationX = ObjectAnimator.ofFloat(img, "translationX", 0, img.getWidth());
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(img, "translationY", 0, img.getHeight());
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.playTogether(rotationX, rotationY);
        set.start();*/

        //第二种方式
        img.animate().translationX(img.getWidth())
                .translationY(img.getHeight())
                .setDuration(300)
                .setInterpolator(new BounceInterpolator())
                .start();
    }
}
