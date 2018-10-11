package com.chengzi.framework;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ThreadLocalTest {

    @Test
    public void threadLocalTest() throws InterruptedException {
        int threads =1;
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        Innerclass innerclass = new Innerclass();
        for (int i=1; i<= threads; i++){
            new Thread(() -> {
                for(int j=0; j<1; j++){
                    innerclass.add(String.valueOf(j));
                    innerclass.print();
                }
                innerclass.set("hello world");
                countDownLatch.countDown();
            }, "thread - " + i).start();
        }
        countDownLatch.await();
    }

    private static class Innerclass{
        public void add(String newStr){
            StringBuilder str = Counter.counter.get();
            Counter.counter.set(str.append(newStr));
        }

        public void set(String words){
            Counter.counter.set(new StringBuilder(words));
            System.out.printf("Set, Thread name:%s , ThreadLocal hashcode:%s,  Instance hashcode:%s, Value:%s\n",
                Thread.currentThread().getName(),
                Counter.counter.hashCode(),
                Counter.counter.get().hashCode(),
                Counter.counter.get().toString());
        }

        public void print(){
            System.out.printf("Thread name:%s , ThreadLocal hashcode:%s, Instance hashcode:%s, Value:%s\n",
                Thread.currentThread().getName(),
                Counter.counter.hashCode(),
                Counter.counter.get().hashCode(),
                Counter.counter.get().toString());
        }
    }

    private static class Counter{
        private static ThreadLocal<StringBuilder> counter = new ThreadLocal<StringBuilder>(){
            @Override
            protected  StringBuilder initialValue(){
                return new StringBuilder();
            }
        };
    }
}
