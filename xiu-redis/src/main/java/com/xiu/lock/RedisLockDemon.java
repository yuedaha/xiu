package com.xiu.lock;

import org.redisson.Redisson;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * TODO
 *
 * @author yuesheng
 * @date 2021-07-28 15:02
 * @since 1.0
 */
public class RedisLockDemon {


    public static void main(String[] args) {
        RedissonClient redisson = Redisson.create(new Config());
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("");
    }
}
