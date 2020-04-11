package com.wyw.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * 场景：线程池调度两百个线程调用test方法
 *
 * @author wyw
 * @date 2020/04/11
 */
@Slf4j
public class SemaphoreExample2 {

    private static final int threadNum = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        //3代表允许的并发数
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0;i<threadNum;i++){
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    //一次获取3个许可
                    semaphore.acquire(3);
                    test(threadNum);
                    //释放许可  （也可以单个释放）
                    semaphore.release(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        log.info("{}",threadNum);
        Thread.sleep(1000);
    }
}

