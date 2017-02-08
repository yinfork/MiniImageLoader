package com.inkenka.miniimageloader;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MiniImageLoaderExecutor extends ThreadPoolExecutor {

    private static MiniImageLoaderExecutor imageThreadPoolExecutor;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT * 4 + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 5 + 1;
    private static final long KEEP_ALIVE = 10L;

    //用于给线程池创建线程的线程工厂类
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "MiniImageLoader#" + mCount.getAndIncrement());    // 标记MiniImageLoader的线程名称
        }
    };

    private MiniImageLoaderExecutor(
        int corePoolSize,                   // 线程池维护线程的最少数量,包括空闲线程
        int maximumPoolSize,                // 线程池维护线程的最大数量
        long keepAliveTime,                 // 线程池维护线程所允许的空闲时间
        TimeUnit unit,                      // 线程池维护线程所允许的空闲时间的单位
        BlockingQueue<Runnable> workQueue,  // 线程池所使用的缓冲队列
        ThreadFactory threadFactory)        // 执行程序创建新线程时使用的工厂
    {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public static synchronized MiniImageLoaderExecutor getInstance() {
        if (imageThreadPoolExecutor == null) {
            imageThreadPoolExecutor = new MiniImageLoaderExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
                KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), sThreadFactory);
        }
        return imageThreadPoolExecutor;
    }
}
