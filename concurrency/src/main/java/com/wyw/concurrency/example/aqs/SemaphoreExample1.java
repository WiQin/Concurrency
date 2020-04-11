package com.wyw.concurrency.example.aqs;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 信号量
 * 场景：线程池调度两百个线程调用test方法
 *
 * @author wyw
 * @date 2020/04/11
 */
@Slf4j
public class SemaphoreExample1 {

    private static final int threadNum = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        //20代表允许的并发数
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0;i<threadNum;i++){
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    //获取一个许可
                    semaphore.acquire();
                    test(threadNum);
                    //释放许可
                    semaphore.release();
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

