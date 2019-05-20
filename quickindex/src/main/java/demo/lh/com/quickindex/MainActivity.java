package demo.lh.com.quickindex;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private MyIndex iv_word;
    private TextView tv_index;
    private Handler handler = new Handler();
    private ArrayList<Person> persons;
    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        persons = new ArrayList<>();
        persons.add(new Person("张晓飞"));
        persons.add(new Person("杨光福"));
        persons.add(new Person("胡继群"));
        persons.add(new Person("刘畅"));

        persons.add(new Person("钟泽兴"));
        persons.add(new Person("尹革新"));
        persons.add(new Person("安传鑫"));
        persons.add(new Person("张骞壬"));

        persons.add(new Person("温松"));
        persons.add(new Person("李凤秋"));
        persons.add(new Person("刘甫"));
        persons.add(new Person("娄全超"));
        persons.add(new Person("张猛"));

        persons.add(new Person("王英杰"));
        persons.add(new Person("李振南"));
        persons.add(new Person("孙仁政"));
        persons.add(new Person("唐春雷"));
        persons.add(new Person("牛鹏伟"));
        persons.add(new Person("姜宇航"));

        persons.add(new Person("刘挺"));
        persons.add(new Person("张洪瑞"));
        persons.add(new Person("张建忠"));
        persons.add(new Person("侯亚帅"));
        persons.add(new Person("刘帅"));

        persons.add(new Person("乔竞飞"));
        persons.add(new Person("徐雨健"));
        persons.add(new Person("吴亮"));
        persons.add(new Person("王兆霖"));

        persons.add(new Person("阿三"));
        persons.add(new Person("李博俊"));

        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person person, Person t1) {
                return person.getPinyin().compareTo(t1.getPinyin());
            }
        });
    }

    private void initEvent() {
        list_view.setAdapter(new MyAdapter());
        iv_word.setOnIndexChangeListener(new MyIndex.OnIndexChangeListener() {
            @Override
            public void onIndexchange(String word) {
                upDataWord(word);
                updataListView(word);
            }
        });
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        list_view = findViewById(R.id.list_view);
        iv_word = findViewById(R.id.iv_words);
        tv_index = findViewById(R.id.tv_index);
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return persons.size();
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
            MyViewHolder mvh;
            if (view == null){
                view = View.inflate(MainActivity.this, R.layout.item_main, null);
                mvh = new MyViewHolder();
                mvh.tv_name = view.findViewById(R.id.tv_name);
                mvh.tv_pinyin = view.findViewById(R.id.tv_pinyin);
                view.setTag(mvh);
            }else {
                mvh = (MyViewHolder) view.getTag();
            }

            String name = persons.get(i).getName();
            String pinyin = persons.get(i).getPinyin().substring(0, 1);
            mvh.tv_pinyin.setText(pinyin);
            mvh.tv_name.setText(name);
            if (i == 0){
                mvh.tv_pinyin.setVisibility(View.VISIBLE);
            }else {
                String prepinyin = persons.get(i - 1).getPinyin().substring(0, 1);
                if (pinyin.equals(prepinyin)){
                    mvh.tv_pinyin.setVisibility(View.GONE);
                }
            }

            return view;
        }
    }

    static class MyViewHolder{
        TextView tv_name;
        TextView tv_pinyin;
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

    private void updataListView(String word) {
        for (int i = 0; i < persons.size(); i++){
            String pinyin = persons.get(i).getPinyin().substring(0, 1);
            if (word.equals(pinyin)) {
                list_view.setSelection(i);
                return;
            }
        }
    }

}
