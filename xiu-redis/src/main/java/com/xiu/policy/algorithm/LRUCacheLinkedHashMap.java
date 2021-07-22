package com.xiu.policy.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author yuesheng
 * @date 2021-07-22 15:14
 * @since 1.0
 */
public class LRUCacheLinkedHashMap extends LinkedHashMap<Integer, Integer> {

    private int capacity;

    public LRUCacheLinkedHashMap(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
