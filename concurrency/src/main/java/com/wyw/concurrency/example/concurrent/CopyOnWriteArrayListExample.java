package com.wyw.concurrency.example.concurrent;

import com.wyw.concurrency.annotations.NotThreadSafe;
import com.wyw.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/03/28
 */
@Slf4j
@ThreadSafe
public class CopyOnWriteArrayListExample {

    //1000个请求
    public static int clientTotal = 1000;
    //并发线程数
    public static int threadTotal = 50;

    private static List<Integer> list = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //信号量,允许并发的线程数
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0;i<clientTotal;i++){
            final int count = i;
            executorService.execute(()->{
                //引入信号量
                try {
                    semaphore.acquire();
                    update(count);
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
        log.info("size:{}",list.size());


    }

    private static void update(int i){
        list.add(i);
    }
}
