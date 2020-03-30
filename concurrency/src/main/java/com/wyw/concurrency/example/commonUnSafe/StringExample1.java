package com.wyw.concurrency.example.commonUnSafe;

import com.wyw.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * ${Description}
 *
 *
 * 5000次请求，最大线程数200
 * 调用5000次update方法，每次在字符串后加1
 * @author wyw
 * @date 2020/03/28
 */
@Slf4j
@NotThreadSafe
public class StringExample1 {

    //1000个请求
    public static int clientTotal = 1000;
    //并发线程数
    public static int threadTotal = 50;

    private static StringBuilder str = new StringBuilder();

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
        log.info("size:{}",str.length());


    }

    private static void update(){
        str.append("1");
    }

}
