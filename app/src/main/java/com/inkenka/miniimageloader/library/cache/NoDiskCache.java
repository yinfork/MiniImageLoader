package com.inkenka.miniimageloader.library.cache;


import android.graphics.Bitmap;

/**
 * 当DiskLruCache由于存储空间等原因无法构建Cache时,则采用NoDiskCache
 */
public class NoDiskCache implements DiskCache<Bitmap> {

    @Override
    public Bitmap get(String key) {
        return null;
    }

    @Override
    public void put(String key, Writer writer) {

    }

}
