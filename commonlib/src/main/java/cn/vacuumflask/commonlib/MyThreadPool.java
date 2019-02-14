package cn.vacuumflask.commonlib;


import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
public class MyThreadPool {

    private ThreadPoolExecutor executor;
    private static final String TAG = "MyThreadPool";

    public static MyThreadPool getInstance() {

        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final MyThreadPool INSTANCE = new MyThreadPool();
    }

    //构造方法私有化
    private MyThreadPool() {
        /**
         * 给corePoolSize赋值：当前设备可用处理器核心数*2 + 1,能够让cpu的效率得到最大程度执行（有研究论证的）
         */
//        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;//核心线程池的数量，同时能够执行的线程数量
        int corePoolSize = 3;//核心线程池的数量，同时能够执行的线程数量
        Log.i(TAG, "线程数：" + corePoolSize);
        int maximumPoolSize = corePoolSize;//最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
        long keepAliveTime = 1;//存活时间
        TimeUnit unit = TimeUnit.SECONDS;//时间单位

        executor = new ThreadPoolExecutor(
                corePoolSize, //当某个核心任务执行完毕，会依次从缓冲队列中取出等待任务
                maximumPoolSize,//5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                keepAliveTime,//表示的是maximumPoolSize当中等待任务的存活时间
                unit,//表示的是maximumPoolSize当中等待任务的存活时间单位
                new LinkedBlockingQueue<Runnable>(),//缓冲队列，用于存放等待任务，Linked的先进先出
                Executors.defaultThreadFactory(),//创建线程的工厂
                new ThreadPoolExecutor.AbortPolicy());//用来对超出maximumPoolSize的任务的处理策略
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        if (runnable == null) return;
        executor.execute(runnable);
    }

    /**
     * 移除任务
     */
    public void remove(Runnable runnable) {
        if (runnable == null) return;
        executor.remove(runnable);
    }


}

