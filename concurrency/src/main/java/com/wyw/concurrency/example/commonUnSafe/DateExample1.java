package com.wyw.concurrency.example.commonUnSafe;

import com.wyw.concurrency.annotations.NotThreadSafe;
import com.wyw.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/03/28
 */
@Slf4j
@ThreadSafe
public class DateExample1 {

//    private static SimpleDateFormat simpleDateFormat =
//            new SimpleDateFormat("yyyy-MM-dd");

    //1000个请求
    public static int clientTotal = 1000;
    //并发线程数
    public static int threadTotal = 50;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量,允许并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0;i<clientTotal;i++){
            executorService.execute(()->{
                //引入信号量
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.wait();
        //关闭线程池
        executorService.shutdown();

    }

    private static void update(){
        try {
            //需要声明为局部变量，否则会有线程安全问题
            SimpleDateFormat simpleDateFormat =
                    new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.parse("2020-03-28");
        } catch (ParseException e) {
            log.error("parse exception",e);
        }
    }

}
