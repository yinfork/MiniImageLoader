package com.inkenka.miniimageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片请求任务类
 */
public class Job implements Runnable {

    private static final int MSG_COMPLETE = 1;

    private static final int MSG_EXCEPTION = 2;

    private static final int MSG_CANCELLED = 3;

    private String mUrl;

    private ImageHandler mImageHandler;


    public Job(String url, MainThreadCallback callback) {
        mUrl = url;
        mImageHandler = new ImageHandler(callback);
    }


    @Override
    public void run() {
        if (TextUtils.isEmpty(mUrl)) {
            return;
        }

        Message msg = mImageHandler.obtainMessage();

        try {
            Bitmap bitmap = requestImageFromUrl(mUrl);
            if (null != bitmap) {
                msg.what = MSG_COMPLETE;
                msg.obj = bitmap;
            } else {
                msg.what = MSG_EXCEPTION;
                msg.obj = "bitmap is null";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg.what = MSG_EXCEPTION;
            msg.obj = e.toString();
        } finally {
            mImageHandler.sendMessage(msg);
        }
    }


    private Bitmap requestImageFromUrl(String url) throws Exception {
        URL imgurl = null;
        Bitmap bitmap = null;

        HttpURLConnection urlConnection;
        imgurl = new URL(url);
        urlConnection = (HttpURLConnection)
            imgurl.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        InputStream is = urlConnection.getInputStream();
        bitmap = BitmapFactory.decodeStream(is);
        is.close();
        return bitmap;
    }

    interface MainThreadCallback {
        void onSuccessed(Bitmap bitmap);
        void onFailed(String errorMsg);
    }

    private static class ImageHandler extends Handler {

        MainThreadCallback mCallback;

        public ImageHandler(MainThreadCallback callback) {
            mCallback = callback;
        }

        @Override
        public void handleMessage(Message msg) {
            if (null != mCallback && null != msg) {
                switch (msg.what) {
                    case MSG_COMPLETE:
                        if (msg.obj instanceof Bitmap) {
                            Bitmap bitmap = (Bitmap) msg.obj;
                            mCallback.onSuccessed(bitmap);
                        }
                        break;
                    case MSG_EXCEPTION:
                        if (msg.obj instanceof String) {
                            mCallback.onFailed((String) msg.obj);
                        }
                        break;
                    case MSG_CANCELLED:
                        mCallback.onFailed("Cancel!");
                        break;
                }
            }
        }
    }
}
