package com.wyw.concurrency.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
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
public class ImmutableExample3 {
    private static final ImmutableList<Integer> list =
            ImmutableList.of(1,2,3);

    private static final ImmutableSet set = ImmutableSet.copyOf(list);

    private static final ImmutableMap map =
            ImmutableMap.of(1,2,3,4);

    private static final ImmutableMap map2 =
            ImmutableMap.builder().put(1,2).put(3,4).build();



    public static void main(String[] args) {
        //废弃方法，会抛异常
        list.add(4);
        set.add(4);

        map.put(5,6);
        map2.put(5,6);

        //正常
        map2.get(3);
    }

}
