package com.inkenka.miniimageloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        new MiniImageLoader().loadImage("https://avatars1.githubusercontent.com/u/18612038?v=3&s=460",imageView);
    }
}
