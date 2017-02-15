package com.inkenka.miniimageloader.demo;

import com.inkenka.miniimageloader.R;
import com.inkenka.miniimageloader.demo.MultiItemList.ListViewDemoActivity;
import com.inkenka.miniimageloader.demo.SingleImageView.SingleImageDemoActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final static String TAG = "MainActivity";

    private final static String DEMO_IMAGEVIEW = "ImageView Demo";
    private final static String DEMO_LISTVIEW = "ListView Demo";

    String[] mDemos = new String[]{
        DEMO_IMAGEVIEW,
        DEMO_LISTVIEW
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, mDemos);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if(position >= mDemos.length) return;

        Intent intent = new Intent();
        String demoName = mDemos[position];
        switch (demoName) {
            case DEMO_IMAGEVIEW:
                intent.setClass(MainActivity.this, SingleImageDemoActivity.class);
                break;
            case DEMO_LISTVIEW:
                intent.setClass(MainActivity.this, ListViewDemoActivity.class);
                break;
        }

        // 判断该Intent是否能被解析再启动
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        Log.i(TAG, "Open " + demoName);
    }
}
