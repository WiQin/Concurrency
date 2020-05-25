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

####同步器  AQS
提供了基于FIFO的队列

Sync queue:双向链表，同步队列（head节点,tail节点）
Condition queue:单项链表，非必需，可能有多个
   
利用int类型标识状态（state变量）  
使用方法：继承，子类通过继承并实现其方法来管理状态（acquire和release）  
可以同时实现排它锁和共享锁模式  
#####同步组件
1.CountDownLatch   计数器 
同步辅助类，可以完成阻塞当前线程的功能   使用给定的计数器进行初始化，计数器操作为原子操作  
调用await()方法的线程处于阻塞，直到其他线程调用countdown()方法，每次调用计数器的值减一，当计数器值为0时，所有调用await方法阻塞的线程继续执行  
计数器不会被重置，所以该操作只能执行一次  

使用场景：
程序执行需要等某个条件完成后才能进行后续操作时 如某个主任务需要等所有子任务执行完成后才能继续执行  
支持等待指定时间，超出时间后，会执行后续程序，单线程不会立即关闭，而是等当前线程执行完毕后再关闭

2.Semaphore 信号量  可控制并发访问的线程个数 
控制某个资源可被同时访问的个数  
方法：acquire()--获取许可，若获取不到则等待
     release()--操作完成后释放许可
使用场景：仅能提供有限访问的资源（数据库连接数）  

3.CyclicBarrier  循环屏障  同步辅助类  
允许一组线程相互等待，直到到达某个公共屏障点，所有线程全部准备就绪后才能各自执行后续操作
await(): 等待，计数器加1  计数器达到初始值后，线程被唤醒，继续执行
计数器可可以reset()方法重置，循环使用  

与CountDownLatch区别：
1).CountDownLatch不可循环
2).CountDownLatch主要用于一个或多个线程等待其他线程的场景；CyclicBarrier用于多个线程间相互等待

使用场景：多线程计算数据，最后合并计算结果

4.ReentrantLock    可重复入锁
java中锁：synchronized修饰；  JUC中提供的锁（ReentrantLock）

与synchronized区别：
1).可重入性  同一个
2).synchronized依赖JVM实现，ReentrantLock依赖JDK实现
3).synchronized更便利，由编译器保证加锁和释放；ReentrantLock需要手动加锁和释放
   
ReentrantLock独有的功能：
1).ReentrantLock更灵活  可以指定公平锁（先等待的线程先获得锁）还是非公平锁；
2).提供一个Condition类，可以分组唤醒需要唤醒的线程
3).提供中断等待锁的线程的机制  lock.lockInterruptibly();  

ReentrantReadWriteLock:  
没有任何读写锁的情况下才能取得写入的锁，可用于实现悲观读取

StampedLock：  版本+模式
三种模式：写，读，乐观读（程序采取读取数据后查看是否遭到写入执行的变更，再采取后续措施）

锁的使用选择：
1.少量竞争者：synchronized
2.竞争者不少，线程增长趋势可预估：ReentrantLock

4.FutureTask

###多线程并发扩展
####死锁
两个或两个以上线程因争夺线程而互相等待  
死锁产生的必要条件：  
1、互斥条件：进程对资源进行排他性使用
2、请求和保持：进程已经保持至少一个资源，又提出了新的资源请求，该资源又被其他线程占用，请求进程阻塞，但又对自己占有资源保持不放
3、不剥夺条件：进程已获得的资源不能被剥夺，只能在使用完时自己释放


