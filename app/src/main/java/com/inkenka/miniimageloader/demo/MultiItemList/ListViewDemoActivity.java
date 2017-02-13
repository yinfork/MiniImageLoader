package com.inkenka.miniimageloader.demo.MultiItemList;

import com.inkenka.miniimageloader.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewDemoActivity extends AppCompatActivity implements
    AdapterView.OnItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);

        ListView listView = (ListView) findViewById(R.id.list);

        List<ListItemData> list = new ArrayList<>();
        list.add(new ListItemData(ListItemData.TYPE_TEXT, "aaaaaa", null));
        list.add(new ListItemData(ListItemData.TYPE_ONE_PIC, "bbbbbb",
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"}));
        list.add(new ListItemData(ListItemData.TYPE_THREE_PIC, "cccccc",
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"}));
        list.add(new ListItemData(ListItemData.TYPE_ONE_PIC, "dddddd",
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"}));
        list.add(new ListItemData(ListItemData.TYPE_THREE_PIC, "eeeeee",
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"}));
        list.add(new ListItemData(ListItemData.TYPE_TEXT, "ffffff", null));
        list.add(new ListItemData(ListItemData.TYPE_TEXT, "gggggg", null));
        list.add(new ListItemData(ListItemData.TYPE_ONE_PIC, "hhhhhh",
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"}));
        list.add(new ListItemData(ListItemData.TYPE_THREE_PIC, "iiiiii",
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"}));
        list.add(new ListItemData(ListItemData.TYPE_ONE_PIC, "jjjjjj",
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"}));
        list.add(new ListItemData(ListItemData.TYPE_THREE_PIC, "kkkkkk",
            new String[]{"http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg"}));
        list.add(new ListItemData(ListItemData.TYPE_TEXT, "llllll", null));


        listView.setAdapter(new MultitypeAdapterImpl(list));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


}
