package com.wyw.concurrency.example.thread_local;

/**
 * 存储希望绑定到线程上的信息
 */
public class RequestHolder {

    /**
     * ThreadLocal要存储的信息的对象
     * 模拟，使用Long类型，实际业务中可能是一个业务对象
     *
     */
    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(Long id){
        //默认取当前线程id  使用Map保证线程封闭
        //key：当前线程对象  value；业务对象，在这里是id
        requestHolder.set(id);
    }

    //取出信息
    public static Long getId(){
        //传入当前线程id（key）,得到业务对象（value,在这里是Long类型的id）
        return requestHolder.get();
    }

    //移除变量
    //ThreadLocal会一直伴随着项目，只有项目重新启动在其中存储的信息才会被释放
    public static void remove(){
        requestHolder.remove();
    }
}
