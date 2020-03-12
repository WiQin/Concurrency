package com.wyw.thread_safe;

/**
 * 同步方法
 *
 * @author wyw
 * @date 2020/03/07
 */
public class Ticket2 implements Runnable{
    int ticket= 100;

    @Override
    public void run() {
        while (true){
            sellTicket();
        }
    }

    /**
     * 锁对象 是 谁调用这个方法 就是谁
     * 隐含 锁对象 就是 this
     *
     */
    private synchronized void sellTicket(){
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
