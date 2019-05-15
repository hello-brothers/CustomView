package demo.lh.com.quickindex;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyIndex iv_word;
    private TextView tv_index;

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_word = findViewById(R.id.iv_words);
        tv_index = findViewById(R.id.tv_index);
        iv_word.setOnIndexChangeListener(new MyIndex.OnIndexChangeListener() {
            @Override
            public void onIndexchange(String word) {
                upDataWord(word);
            }
        });
    }

    private void upDataWord(String word) {
        tv_index.setVisibility(View.VISIBLE);
        tv_index.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_index.setVisibility(View.GONE);
            }
        }, 500);
    }
}
