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

public class MiniImageLoader {

    ImageMainThreadHandler mImageMainThreadHandler = new ImageMainThreadHandler();

    public void loadImage(final String url, final ImageView imageView){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = requestImageFromUrl(url);
                Targer targer = new Targer(bitmap,imageView);
                Message msg = mImageMainThreadHandler.obtainMessage();
                msg.obj = targer;
                mImageMainThreadHandler.sendMessage(msg);
            }
        }).start();
    }

    private Bitmap requestImageFromUrl(String url) {
        URL imgurl = null;
        Bitmap bitmap = null;

        HttpURLConnection urlConnection;
        try {
            imgurl = new URL(url);
            urlConnection = (HttpURLConnection)
                imgurl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream is = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static class Targer{
        public Targer(Bitmap bitmap, ImageView imageView) {
            mBitmap = bitmap;
            mImageView = imageView;
        }

        public Bitmap mBitmap;
        public ImageView mImageView;
    }

    private static class ImageMainThreadHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(null != msg && msg.obj instanceof Targer) {
                Targer targer = (Targer) msg.obj;

                Bitmap bitmap = targer.mBitmap;
                ImageView imageView = targer.mImageView;
                if(null != bitmap && null != imageView) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
