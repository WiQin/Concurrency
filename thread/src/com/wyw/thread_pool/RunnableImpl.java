package com.wyw.thread_pool;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/03/08
 */
public class RunnableImpl implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"创建了一个新的线程");
    }
}
