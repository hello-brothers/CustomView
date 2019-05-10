package demo.lh.com.popupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText et_text;
    private ImageView img_arrow;
    private PopupWindow popupWindow;
    private ListView listView;
    private ArrayList<String> contents;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        contents = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            contents.add("-----this is -----" + i);
        }

    }

    private void initEvent() {
        img_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow == null) {
                    popupWindow = new PopupWindow();
                    popupWindow.setContentView(listView);
                    int height = DensityUtil.px2dip(MainActivity.this, 200);
                    popupWindow.setHeight(height);
                    popupWindow.setWidth(et_text.getWidth());
                    popupWindow.setFocusable(true);
                }
                popupWindow.showAsDropDown(et_text, 0, 0);
            }
        });
        myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = contents.get(position);
                et_text.setText(content);
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        et_text = findViewById(R.id.et_text);
        img_arrow = findViewById(R.id.img_arrow);

        listView = new ListView(this);
    }

    private class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return contents.size();
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
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_main, null);
                viewHolder = new ViewHolder();
                viewHolder.tx_content = convertView.findViewById(R.id.tv_content);
                viewHolder.img_delete = convertView.findViewById(R.id.img_delete);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final String msg = contents.get(position);
            viewHolder.tx_content.setText(msg);

            viewHolder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contents.remove(msg);
                    myAdapter.notifyDataSetInvalidated();
                }
            });
            return convertView;
        }
    }

    private class ViewHolder {
        TextView tx_content;
        ImageView img_delete;
    }
}
