package com.wyw.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/04/11
 */
@Slf4j
public class CyclicBarrierExample3 {

    //5个线程同步等待
    private static CyclicBarrier barrier = new CyclicBarrier(5,() -> {
        log.info("线程到达屏障时，优先执行该处Runnable");
    });

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0;i<10;i++){
            //10个线程
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(() -> {
                try {
                    race(threadNum);
                } catch (Exception e) {
                    log.error("{} error",threadNum);
                }
            });
        }
        executorService.shutdown();
    }

    private static void race(int threadNum) throws Exception{
        Thread.sleep(1000);
        log.info("{} is ready",threadNum);
        //await()方法，标识当前线程达到条件
        //等待2000ms
        try {
            barrier.await(2000,TimeUnit.MILLISECONDS);
        } catch (BrokenBarrierException e) {
            log.warn("BrokenBarrierException",e);
        } catch (TimeoutException e){
            log.warn("TimeoutException",e);
        }
        log.info("{} continue",threadNum);
    }
}
