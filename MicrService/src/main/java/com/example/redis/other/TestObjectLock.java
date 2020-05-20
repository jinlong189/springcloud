package com.example.redis.other;

public class TestObjectLock {
    private Object lock = new Object();
    private volatile int num=100;

    /**
     * 锁住非静态变量
     * @throws InterruptedException
     */
    public void lockObjectField() throws InterruptedException{
        synchronized (lock){
            num--;
            System.out.println(Thread.currentThread().getName()+"==="+num);
            Thread.sleep(1000);
        }
    }

    /**
     * 锁住 this 对象 this 就是当前对象实例
     * @throws InterruptedException
     */
    public void lockThis() throws InterruptedException{
        synchronized (this){
            System.out.println(Thread.currentThread().getName());
            Thread.sleep(10*1000);
        }
    }

    /**
     * 直接锁住非静态方法
     * @throws InterruptedException
     */
    public synchronized void methodLock() throws InterruptedException{
        System.out.println(Thread.currentThread().getName());
        Thread.sleep(10*1000);
    }

    public static void main(String[] args) {
        for (int i = 0; i <5 ; i++) {
            new Thread(()->{
                TestObjectLock objectLock = new TestObjectLock();
                try {
                    objectLock.lockObjectField();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            },"Thread-"+i).start();
        }
    }
}
