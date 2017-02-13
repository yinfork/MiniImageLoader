package com.inkenka.miniimageloader.demo;

import com.inkenka.miniimageloader.R;
import com.inkenka.miniimageloader.library.MiniImageLoader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SingleImageDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview_demo);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        new MiniImageLoader().loadImage("http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",imageView);
    }

}
