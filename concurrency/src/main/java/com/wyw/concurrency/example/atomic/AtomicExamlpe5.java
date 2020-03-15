package com.wyw.concurrency.example.atomic;


import com.wyw.concurrency.annotations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 线程原子性
 * AtomicXXXFieldUpdater
 * 原子性修改某个实例的字段（volatile修饰，非static）
 *
 * AtomicStampReference 解决CAS的ABA问题（其他线程在CAS操作时将变量的值由A改为B再改为A）
 * --变量更新时版本号+1
 *
 * AtomicLongArray--更新数组中某个索引的值
 * （参数多一个索引）
 *
 * @author wyw
 * @date 2020/03/14
 */
@ThreadSafe
@Slf4j
public class AtomicExamlpe5 {
    private static AtomicIntegerFieldUpdater<AtomicExamlpe5> updater
             = AtomicIntegerFieldUpdater.newUpdater(AtomicExamlpe5.class,"count");

    @Getter
    public volatile int count = 100;

    public static void main(String[] args) {
        AtomicExamlpe5 examlpe5 = new AtomicExamlpe5();
        if (updater.compareAndSet(examlpe5,100,120)){
            log.info("success1,{}",examlpe5.getCount());
        }

        if (updater.compareAndSet(examlpe5,100,120)){
            log.info("success2,{}",examlpe5.getCount());
        }else {
            log.info("fail,{}",examlpe5.getCount());
        }
    }

}
