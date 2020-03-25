package com.wyw.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.wyw.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * 不可变对象
 * 通过Collections.unmodifiableXXX实现
 */
@Slf4j
@ThreadSafe
public class ImmutableExample2 {
    private static Map<Integer,Integer> map = Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        //非法，无法被修改，put操作会抛出异常
        map.put(1,3);
    }

}
