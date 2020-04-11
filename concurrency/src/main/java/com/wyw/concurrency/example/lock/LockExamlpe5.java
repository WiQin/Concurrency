package com.wyw.concurrency.example.lock;


import com.wyw.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock
 *
 *
 *
 * @author wyw
 * @date 2020/03/14
 */
@ThreadSafe
@Slf4j
public class LockExamlpe5 {
    //1000个请求
    public static int clientTotal = 1000;
    //并发线程数
    public static int threadTotal = 50;

    public static int count= 0;

    private final static StampedLock lock = new StampedLock();


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
                    add();
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
        log.info("count:{}",count);


    }

    private static void add(){
        //返回一个stamp值
        long stamp = lock.writeLock();
        try {
            count++;
        } finally {
            //释放时需要带上stamp
            lock.unlock(stamp);
        }
    }
}
