package com.wyw.thread.myrunnable;

/**
 * 多线程的第二种方法
 * 实现Runnable接口
 *
 * @author wyw
 * @date 2020/03/07
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println(Thread.currentThread().getName()+" "+i);
        }
    }
}
