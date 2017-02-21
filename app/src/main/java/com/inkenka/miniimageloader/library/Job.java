package com.inkenka.miniimageloader.library;

import com.inkenka.miniimageloader.library.cache.DiskCache;
import com.inkenka.miniimageloader.library.cache.MemoryLruCache;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

/**
 * 图片请求任务类
 */
public abstract class Job implements Runnable {


    abstract protected void init(String url,
        MemoryLruCache memoryLruCache,
        DiskCache<Bitmap> diskCache,
        int reqWidth,
        int reqHeight,
        MainThreadCallback callback);

    abstract protected void runWrapped();

    abstract protected void cancel();


    @Override
    public void run() {
        runWrapped();
    }

}
