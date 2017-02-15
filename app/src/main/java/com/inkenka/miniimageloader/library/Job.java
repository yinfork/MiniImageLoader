package com.inkenka.miniimageloader.library;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

/**
 * 图片请求任务类
 */
public abstract class Job implements Runnable {


    abstract protected void init();
    abstract protected void runWrapped();
    abstract protected void cancel();




    @Override
    public void run() {
        runWrapped();
    }

}
