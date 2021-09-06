package com.xiu.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

/**
 * 第一个版本线程池
 *
 * 1、包含一个阻塞队列和一个线程列表
 * 2、初始化时启动所有的线程
 * 3、执行任务死循环等待阻塞队列
 *
 * @author yuesheng
 * @date 2021-08-17 19:54
 * @since 1.0
 */
public class XiuThreadPoolV1 {

    BlockingQueue<Runnable> tasks;

    List<Thread> works;

    public XiuThreadPoolV1(int threadSize, int queueSize) {
        tasks = new ArrayBlockingQueue(queueSize);
        works = new ArrayList<>(threadSize);
        IntStream.rangeClosed(1, threadSize).forEach(i -> {
            XiuThread thread = new XiuThread(String.format("thread-pool-%s", i));
            works.add(thread);
            thread.start();
        });
    }

    public void execute(Runnable task) {
        try {
            tasks.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class XiuThread extends Thread {

        public XiuThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            Runnable take;
            while (true) {
                try {
                    take = tasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                take.run();
            }
        }
    }
}
