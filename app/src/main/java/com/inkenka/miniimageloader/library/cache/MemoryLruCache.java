package com.inkenka.miniimageloader.library.cache;

import android.graphics.Bitmap;

/**
 * 基于内存的Lru缓存
 */
public interface MemoryLruCache {


    void put(String url, Bitmap bitmap);

    Bitmap get(String url);

    void clearMemory();

    void trimMemory(int level);
}
