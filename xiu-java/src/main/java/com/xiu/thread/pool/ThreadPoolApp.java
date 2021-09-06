package com.xiu.thread.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * TODO
 *
 * @author yuesheng
 * @date 2021-08-17 20:11
 * @since 1.0
 */
public class ThreadPoolApp {

    public static void main(String[] args) {
//        XiuThreadPoolV1 threadPoolV1 = new XiuThreadPoolV1(10, 100);
//        IntStream.range(0, 20).forEach(i -> threadPoolV1.execute(() -> System.out.println(Thread.currentThread().getName())));


        Map<String, String> map = new HashMap();
        map.put("yuesheng_123", "123");
    }
}
