package com.wyw.concurrency.example.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock  慎用
 *
 * 不将map直接暴露给外部，而是封装方法提供给外部使用
 * 使用ReentrantReadWriteLock，保证在没有任何读写锁时才可以进行写入操作
 * 实现悲观读取（读取很多，写入较少时，写锁会一直等待）
 *
 * @author wyw
 * @date 2020/03/14
 */
@Slf4j
public class LockExamlpe3 {

    private final Map<String,Data> map = new TreeMap<>();

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    //读组
    private final Lock readLock = lock.readLock();

    //写组
    private final Lock writeLock = lock.writeLock();

    //读取请求
    public Data get(String key){
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public Set<String> getAllKeys(){
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    public Data put(String key,Data value){
        writeLock.unlock();
        try {
            return map.put(key,value);
        } finally {
            writeLock.unlock();
        }
    }

    class Data{}


}
