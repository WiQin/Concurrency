package com.wyw.thread_state;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/03/07
 */
public class TimedWaiting extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if ((i) % 10 == 0) {
                System.out.println("‐‐‐‐‐‐‐" + i);
            }
            System.out.print(i);
            try {
                Thread.sleep(1000);
                System.out.print(" 线程睡眠1秒！\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new TimedWaiting().start();
    }

}
