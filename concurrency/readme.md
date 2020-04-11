# Java并发编程

* **并发：** 同时拥有两个或多个线程，如果程序在单核处理器上运行，多个线程将交替地换入或换出内存，
这些线程是同时存在的，每个线程都处于执行过程中的某个状态，如果运行在多核处理器上，程序中
的每个线程都将分配到一个处理器核上，因此可以同时进行。  
（多个线程操作相同资源，保证线程安全，合理使用资源）

* **高并发：** 高并发是互联网分布式系统架构设计中必须考虑的因素之一，它通常是指，通过设计保证系统
能够同时并行处理很多请求 
（服务能同时处理很多请求，提高程序性能）  

* 基础知识讲解与核心知识准备：
![](img/准备.jpg)

* 并发及并发的线程安全处理：
![](img/并发.jpg)

* 高并发处理：
![](img/知识技能.png)


##项目准备
###基于springboot
###注解准备，用于标识学习中线程（不）安全，（不）推荐的类或写法
###并发模拟工具 
 postman  Apache Bench  JMeter  代码模拟
###代码模拟
CountDownLatch（计数器） Semaphore(信号量)  线程池

##线程安全性
###原子性
 1.通过AtomicXXX实现，做自增操作时调用unsafe.getAndAddInt，进而调用compareAndSwapInt()实现，即CAS
 ((com.wyw.concurrency.example.atomic))
 2.锁  synchronized关键字  Lock接口（ReetrantLock）
 
###可见性
volatile(做状态标记)
 
##安全发布对象 
 
##线程安全策略
###不可变对象
保证对象在多个线程间的线程安全（避免并发）
###线程封闭  
--把对象封装到一个线程中  
1.Ad-hoc 线程封闭  
2.堆栈封闭--局部变量，无并发问题  
3.ThreadLocal线程封闭
3.1 RequestHolder  模拟希望绑定到线程上的信息（比如用户信息）
3.2 使用场景 
 3.2.1 add（）：进入到后端服务器，但是还没有进行实际业务处理时，调用该方法将相关信息写进去
    ==>使用filter拦截相关url,当前台访问url，在filter中将相关信息写入ThreadLocal，在实际处理时从ThreadLocal中取信息（get方法）(HttpFilter)
 3.2.3 remove（）: 接口处理完之后 ==> interceptor(HttpInterceptor)
3.3 需在Application启动类中定义filter与interceptor
3.4 创建Controller模拟（ThreadLocalController）

###线程不安全的类与写法
1.StringBuilder   但是StringBuffer是线程安全的（方法被synchronized修饰）
StringBuilder存在的原因：但是StringBuffer的方法在同一时间只有一个线程可以使用，性能损耗，
可以在方法内部定义StringBuilder，由于堆栈封闭，只有单个线程可以操作，性能会有所提升，计算更快。
2.SimpleDateFormat  ==> JodaTime
3.ArrayList.HashSet,HashMap
4.先检查，再执行  if(condition(a)){handle(a)}

###同步容器
1.ArrayList  -->  Vector,Stack
2.HashMap  -->  HashTable
3.Collection.synchronizedXXX

###并发容器 J.U.C
1.线程安全的集合
ArrayList--CopyOnWriteArrayList  适用于读多(源数据上，不需加锁)写少(需要加锁，复制多个副本操作)的场景  慎用  读写分离，最终一致性，使用时另外开辟空间
HashSet--CopyOnWriteArraySet  addAll  removeAll需要加锁  单个add,remove线程安全
TreeSet--ConcurrentSkipListSet   addAll  removeAll需要加锁  单个add,remove线程安全

HashMap--ConcurrentHahsMap

TreeMap--ConcurrentSkipListMap