package com.wyw.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.wyw.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 不可变对象
 * 通过final修饰
 */
@Slf4j
@NotThreadSafe
public class ImmutableExample1 {
    private static final int a = 1;
    private static final String b = "test";
    private static final Map<Integer,Integer> map = Maps.newHashMap();

    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
    }

    public static void main(String[] args) {
//        a =2;
//        b = "example";
        map.put(1,3);
    }

    /**
     * 可以使用final修饰方法参数
     * @param a
     */
    private void test(final int a){
//        a = 3;
    }
}
