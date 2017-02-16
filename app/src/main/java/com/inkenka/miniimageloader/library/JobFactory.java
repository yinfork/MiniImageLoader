package com.inkenka.miniimageloader.library;

import com.inkenka.miniimageloader.library.cache.MemoryLruCache;

import android.support.v4.util.Pools;

/**
 * 图片任务工厂类
 */
public class JobFactory {

    private final int JOB_POOL_SIZE = 5;

    final Pools.Pool<SingleImageJob> pool = FactoryPool.build(JOB_POOL_SIZE,
        new FactoryPool.Factory<SingleImageJob>() {
            @Override
            public SingleImageJob create() {
                return new SingleImageJob();
            }
        });


    public Job obtainJob(String url, MemoryLruCache memoryLruCache, MainThreadCallback callback){
        SingleImageJob result = pool.acquire();
        result.init(url, memoryLruCache, callback);
        return result;
    }
}
