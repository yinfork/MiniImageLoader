package com.inkenka.miniimageloader.library;

import com.inkenka.miniimageloader.library.cache.MemoryLruCache;
import com.inkenka.miniimageloader.library.net.NetworkManager;

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
public class SingleImageJob extends Job{

    private static final int MSG_COMPLETE = 1;
    private static final int MSG_EXCEPTION = 2;
    private static final int MSG_CANCELLED = 3;

    private String mUrl;
    private ImageHandler mImageHandler;
    private MemoryLruCache mMemoryLruCache;


    public SingleImageJob(String url, MemoryLruCache memoryLruCache, MainThreadCallback callback) {
        mUrl = url;
        mMemoryLruCache = memoryLruCache;
        mImageHandler = new ImageHandler(callback);
    }


    @Override
    protected void init() {

    }

    @Override
    protected void runWrapped() {
        if (TextUtils.isEmpty(mUrl)) {
            return;
        }


        Message msg = mImageHandler.obtainMessage();

        try {
            Bitmap bitmap = null;

            if(null != mMemoryLruCache){
                bitmap = mMemoryLruCache.get(mUrl);
            }
            if(null == bitmap) {
                bitmap = NetworkManager.requestImageFromUrl(mUrl);

                mMemoryLruCache.put(mUrl,bitmap);
            }

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

    @Override
    protected void cancel() {

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
