package com.inkenka.miniimageloader.library;

import com.inkenka.miniimageloader.library.cache.MemoryLruCache;

/**
 * 图片任务工厂类
 */
public class JobFactory {

    public Job buildJob(String url, MemoryLruCache memoryLruCache, MainThreadCallback callback) {
        return new SingleImageJob(url, memoryLruCache, callback);
    }

}
