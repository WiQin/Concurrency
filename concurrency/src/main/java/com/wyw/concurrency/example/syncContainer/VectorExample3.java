package com.wyw.concurrency.example.syncContainer;

import com.wyw.concurrency.annotations.NotThreadSafe;

import java.util.List;
import java.util.Vector;

/**
 * ${Description}
 *
 * @author wyw
 * @date 2020/03/28
 */
@NotThreadSafe
public class VectorExample3 {

    private static List<Integer> vector = new Vector();

    public static void main(String[] args) {
        for (int i = 0;i<=vector.size();i++){
            vector.add(i);
        }

        Thread thread = new Thread(){
            public void run(){
                for (int i = 0;i<=vector.size();i++){
                    vector.remove(i);
                }
            }

        };

        Thread thread2 = new Thread(){
            public void run(){
                for (int i = 0;i<=vector.size();i++){
                    vector.get(i);
                }
            }

        };

        thread.start();
        thread2.start();
    }
}
