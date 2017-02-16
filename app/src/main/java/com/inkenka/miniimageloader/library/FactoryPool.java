package com.inkenka.miniimageloader.library;

import android.support.v4.util.Pools;
import android.util.Log;

/**
 * 拥有对象池的工厂类
 */
public class FactoryPool<T> implements Pools.Pool<T> {

    private final String TAG = "FactoryPool";

    public interface Factory<T> {
        T create();
    }

    private final Factory<T> factory;
    private final Pools.Pool<T> pool;

    public static <T> Pools.Pool<T> build(int size, Factory<T> factory) {
        return new FactoryPool<>(new Pools.SimplePool<T>(size), factory);
    }

    FactoryPool(Pools.Pool<T> pool, Factory<T> factory) {
        this.pool = pool;
        this.factory = factory;
    }

    @Override
    public T acquire() {
        T result = pool.acquire();
        if (result == null) {
            // 若超出pool的容量,则直接生成
            result = factory.create();
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Created new " + result.getClass());
            }
        }
        return result;
    }

    @Override
    public boolean release(T instance) {
        return pool.release(instance);
    }
}
