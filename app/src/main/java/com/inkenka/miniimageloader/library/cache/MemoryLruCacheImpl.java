package com.inkenka.miniimageloader.library.cache;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

public class MemoryLruCacheImpl implements MemoryLruCache{

    private static LruCache<String,Bitmap> mLruCache;

    public MemoryLruCacheImpl(int size){
        mLruCache = new LruCache<>(size);
    }


    @Override
    public void put(String url, Bitmap bitmap) {
        if(!TextUtils.isEmpty(url)) {
            mLruCache.put(url, bitmap);
        }
    }

    @Override
    public Bitmap get(String url) {
        return mLruCache.get(url);
    }

    @Override
    public void clearMemory() {

    }

    @Override
    public void trimMemory(int level) {

    }
}
