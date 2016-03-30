package com.common.androidcore.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lifan 创建于 2013年10月25日 下午5:39:47
 * @version Ver 1.0 2013年10月25日 改订 线程池
 */
public class ThreadUtil {
    private static ThreadUtil threadUtil;
    private ThreadPoolExecutor pool;

    /**
     * 根据cpu核心数初始化线程池
     */
    private ThreadUtil() {
        if (pool == null) {
            pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(getCpuCoreNumber());
        }
    }

    /**
     * 单例获取ThreadUtil实例
     *
     * @return ThreadUtil实例
     */
    public static ThreadUtil getInstance() {
        if (threadUtil == null) {
            threadUtil = new ThreadUtil();
        }
        return threadUtil;
    }

    /**
     * 获得cpu核心数
     *
     * @return 核心数
     */
    private int getCpuCoreNumber() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 把具体业务操作交给线程池处理
     *
     * @param runnable 业务操作
     */
    public void start(Runnable runnable) {
        pool.submit(runnable);
    }

    public ThreadPoolExecutor getPool() {
        return pool;
    }
}
