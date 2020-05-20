package com.example.redis.other;

public class TestClassLock {
    private static Object lock = new Object();
    /**
     * 锁住静态变量
     * @throws InterruptedException
     */
    public void lockStaticObjectField() throws InterruptedException{
        synchronized (lock){
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10*1000);
        }
    }

    /**
     * 锁住静态方法
     * @throws InterruptedException
     */
    public static synchronized void methodLock() throws InterruptedException{
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(10*1000);
    }

    /**
     * 锁住 xxx.class
     * @throws InterruptedException
     */
    public void lockClass() throws InterruptedException{
        synchronized (TestClassLock.class){
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10*1000);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i <5 ; i++) {
            new Thread(()->{
                TestClassLock classLock = new TestClassLock();
                try {
                    classLock.methodLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            },"Thread-"+i).start();
        }
    }
}
