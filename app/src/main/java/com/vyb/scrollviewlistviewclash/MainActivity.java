package com.vyb.scrollviewlistviewclash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public List<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyListView listView = (MyListView) findViewById(R.id.list_view);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if(i % 2 == 0){
                list.add(R.mipmap.pic1);
            }else {
                list.add(R.mipmap.pic2);
            }
        }
        listView.setParentScrollView(scrollView);
        listView.setAdapter(new MyAdapter());


    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Integer getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image = new ImageView(parent.getContext());
            image.setImageResource(getItem(position));
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return image;
        }
    }
}
