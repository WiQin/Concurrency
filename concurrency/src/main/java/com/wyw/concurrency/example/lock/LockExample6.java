package com.wyw.concurrency.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的使用
 *
 * @author wyw
 * @date 2020/04/11
 */
@Slf4j
public class LockExample6 {

    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        new Thread(() -> {//线程1
            try {
                //1、线程加入aqs等待队列中
                reentrantLock.lock();
                log.info("wait signal"); // 1
                //2、线程从队列中移除，然后加入到condtion的队列中
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //6、线程1被唤醒后，继续执行
            log.info("get signal"); // 4
            //7、线程1释放锁，执行完毕
            reentrantLock.unlock();
        }).start();

        new Thread(() -> {//线程2
            //3、线程1释放锁，2被唤醒，加入aqs等待队列
            reentrantLock.lock();
            log.info("get lock"); // 2
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //4、发送信号，condition队列中的线程1被取出，加入到aqs等待队列中，但并未被唤醒
            condition.signalAll();
            log.info("send signal ~ "); // 3
            //5、线程2释放锁，线程1被唤醒
            reentrantLock.unlock();
        }).start();
    }
}
