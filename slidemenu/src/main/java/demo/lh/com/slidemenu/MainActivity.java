package demo.lh.com.slidemenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list_main;
    private ArrayList<MyBean> myBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        list_main.setAdapter(new MyAdapter());
    }

    private void initData() {
        myBeans = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            myBeans.add(new MyBean("content" +i));
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        list_main = findViewById(R.id.list_main);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return myBeans.size();
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
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            ViewHolder vh;
            if (convertView == null){
                convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
                vh = new ViewHolder();
                vh.contentView = convertView.findViewById(R.id.item_content);
                vh.menuView = convertView.findViewById(R.id.item_menu);
                convertView.setTag(vh);
            }else {
                vh = (ViewHolder) convertView.getTag();
            }
            final MyBean myBean = myBeans.get(i);
            String content = myBean.getContent();
            vh.contentView.setText(content);
            vh.contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyBean myBean1 = myBeans.get(i);
                    Toast.makeText(MainActivity.this, myBean1.getContent(), Toast.LENGTH_SHORT).show();
                }
            });

            vh.menuView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyBean myBean1 = myBeans.get(i);
                    SlideLayout slideLayout = (SlideLayout) view.getParent();
                    slideLayout.closeMenu();
                    myBeans.remove(myBean1);
                    notifyDataSetChanged();

                }
            });
            SlideLayout slideLayout = (SlideLayout) convertView;
            slideLayout.setOnStateChangeListener(new MyStateChangeListener());
            return convertView;
        }
    }
    static class ViewHolder{
        TextView contentView;
        TextView menuView;
    }

    private SlideLayout slideLayout;
    class MyStateChangeListener implements SlideLayout.onStateChangeListener {

        @Override
        public void onClose(SlideLayout layout) {
            Log.i("listener", "onClose");

            if (slideLayout == layout){
                slideLayout = null;
            }
        }

        @Override
        public void onDown(SlideLayout layout) {
            Log.i("listener", "onDown");
            if (slideLayout != null && slideLayout != layout){
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onOpen(SlideLayout layout) {
            Log.i("listener", "open");
            slideLayout = layout;
        }
    }
}
