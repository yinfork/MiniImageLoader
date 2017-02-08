package com.inkenka.miniimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片框架API接口类
 */
public class MiniImageLoader {


    public void loadImage(final String url, final ImageView imageView){

        new Thread(new Job(url, new Job.MainThreadCallback() {
            @Override
            public void onSuccessed(Bitmap bitmap) {
                if(null != bitmap){
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailed(String errorMsg) {

            }
        })).start();
    }
}
