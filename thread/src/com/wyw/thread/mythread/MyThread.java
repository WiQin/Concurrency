package com.wyw.thread.mythread;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/03/07
 */
public class MyThread extends Thread {

    @Override
    public synchronized void start() {
        for (int i = 0;i<20;i++){
            System.out.println("my:"+i);
        }
    }
}
