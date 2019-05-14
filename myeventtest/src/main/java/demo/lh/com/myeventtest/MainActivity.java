package demo.lh.com.myeventtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listView3;
    private ListView listView2;
    private ListView listView1;
    private int[] imgs = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();

    }

    private void initEvent() {
        listView1.setAdapter(new MyAdapter());
        listView2.setAdapter(new MyAdapter());
        listView3.setAdapter(new MyAdapter());


    }

    private void initView() {
        listView1 = findViewById(R.id.list1);
        listView2 = findViewById(R.id.list2);
        listView3 = findViewById(R.id.list3);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 500;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v;
            if (view == null){
                v = View.inflate(getApplicationContext(), R.layout.list_item, null);
            }else {
                v = view;
            }
            ImageView img = v.findViewById(R.id.img);
            int resid = (int) (Math.random()*4);
            int res = imgs[resid];
            img.setBackgroundResource(res);
            return v;
        }
    }
}


