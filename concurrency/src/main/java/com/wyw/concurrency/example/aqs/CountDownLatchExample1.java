package com.wyw.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/04/11
 */
@Slf4j
public class CountDownLatchExample1 {

    private static final int threadNum = 200;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);

        for (int i = 0;i<threadNum;i++){
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //countDownLatch减一
                    countDownLatch.countDown();
                }
            });
        }
        //确保执行完成后才执行后面的程序
        countDownLatch.await();
        doSomething();
        exec.shutdown();
    }

    private static void test(int threadNum) throws Exception {
        Thread.sleep(100);
        log.info("{}",threadNum);
        Thread.sleep(100);
    }

    private static void doSomething(){
        System.out.println("roger that");
    }
}

