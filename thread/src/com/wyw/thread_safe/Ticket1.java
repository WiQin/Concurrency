package com.wyw.thread_safe;

/**
 * 同步代码块
 *
 * @author wyw
 * @date 2020/03/07
 */
public class Ticket1 implements Runnable{
    int ticket = 100;

    Object block = new Object();
    @Override
    public void run() {
        while (true){
            synchronized (block){
                if(ticket>0){//有票 可以卖
                    //出票操作
                    //使用sleep模拟一下出票时间
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        // TODO Auto‐generated catch block
                        e.printStackTrace();
                    }
                    //获取当前线程对象的名字
                    String name = Thread.currentThread().getName();
                    System.out.println(name+"正在卖:"+ticket--);
                }
            }
        }

    }
}
