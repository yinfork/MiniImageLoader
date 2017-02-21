package com.inkenka.miniimageloader.library;

import com.inkenka.miniimageloader.library.cache.DiskCache;
import com.inkenka.miniimageloader.library.cache.DiskLruCacheWrapper;
import com.inkenka.miniimageloader.library.cache.MemoryLruCache;
import com.inkenka.miniimageloader.library.cache.MemoryLruCacheImpl;
import com.inkenka.miniimageloader.library.cache.NoDiskCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * 图片框架API接口类
 */
public class MiniImageLoader {

    //获取分配给该应用的最大内存
    public static int MAX_MEMORY =(int) (Runtime.getRuntime().maxMemory() / 1024);
    //lruChache能获取的缓存大小为整个应用内存的八分之一
    public  static  int MEMORY_CACHE_SIZE =MAX_MEMORY/8;

    private static volatile MiniImageLoader mInstance;

    private MemoryLruCache mMemoryLruCache;
    private JobFactory mJobFactory;

    private DiskCache mDiskCache;

    public static MiniImageLoader get(@NonNull Context context) {
        if (mInstance == null) {
            synchronized (MiniImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new MiniImageLoader(context);
                }
            }
        }

        return mInstance;
    }

    private MiniImageLoader(Context context){
        this.mMemoryLruCache =  new MemoryLruCacheImpl(MEMORY_CACHE_SIZE);
        this.mJobFactory = new JobFactory();

        DiskCache diskCache = DiskLruCacheWrapper.build(context.getApplicationContext());
        if(null == diskCache){
            diskCache = new NoDiskCache();
        }
        this.mDiskCache = diskCache;
    }

    public void loadImage(final String url, final ImageView imageView){
        if(null == imageView) return;

        Job job = mJobFactory.obtainJob(url, mMemoryLruCache, mDiskCache, new MainThreadCallback() {
            @Override
            public void onSuccessed(Bitmap bitmap) {
                if (null != bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailed(String errorMsg) {

            }
        });

        MiniImageLoaderExecutor.getInstance().execute(job);

    }
}
