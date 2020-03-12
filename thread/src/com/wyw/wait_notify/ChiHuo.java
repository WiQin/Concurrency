package com.wyw.wait_notify;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/03/08
 */
public class ChiHuo extends Thread{

    private BaoZi bz;

    public ChiHuo(String name,BaoZi bz){
        super(name);
        this.bz = bz;
    }
    @Override
    public void run() {
        while(true){
            synchronized (bz){
                //没包子
                if(bz.flag == false){
                    try {
                        bz.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("吃货正在吃"+bz.pi+bz.xian+"包子");
                bz.flag = false;
                bz.notify();
            }
        }
    }


}
