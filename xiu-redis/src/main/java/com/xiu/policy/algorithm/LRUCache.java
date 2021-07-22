package com.xiu.policy.algorithm;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 最近最长时间未被使用
 * <p>
 * https://leetcode-cn.com/problems/lru-cache/
 *
 * @author yuesheng
 * @date 2021-07-22 11:19
 * @since 1.0
 */
public class LRUCache {

    private final Map<Integer, CacheObject> cache = new HashMap<>();

    private Integer HEAD;

    private Integer TAIL;

    private final int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        CacheObject cacheObject = cache.get(key);
        if (cache.size() > 1 && !Objects.equals(HEAD, key)) {

            if (Objects.equals(TAIL, key)) {
                cache.get(cacheObject.getPre()).setNext(null);
                TAIL = cacheObject.getPre();
            } else {
                cache.get(cacheObject.getPre()).setNext(cacheObject.getNext());
                cache.get(cacheObject.getNext()).setPre(cacheObject.getPre());
            }

            cacheObject.setPre(null);
            cacheObject.setNext(HEAD);

            CacheObject headCacheObject = cache.get(HEAD);
            headCacheObject.setPre(key);
            HEAD = key;
        }
        return cacheObject.getValue();
    }

    public void put(int key, int value) {
        // 存在缓存，替换value
        if (cache.containsKey(key)) {
            cache.get(key).setValue(value);
            CacheObject cacheObject = cache.get(key);
            if (cache.size() > 1 && !Objects.equals(HEAD, key)) {

                if (Objects.equals(TAIL, key)) {
                    cache.get(cacheObject.getPre()).setNext(null);
                    TAIL = cacheObject.getPre();
                } else {
                    cache.get(cacheObject.getPre()).setNext(cacheObject.getNext());
                    cache.get(cacheObject.getNext()).setPre(cacheObject.getPre());
                }

                cacheObject.setPre(null);
                cacheObject.setNext(HEAD);

                CacheObject headCacheObject = cache.get(HEAD);
                headCacheObject.setPre(key);
                HEAD = key;
            }
            return;
        }
        if (cache.size() < capacity) {
            CacheObject cacheObject = new CacheObject();
            cacheObject.setValue(value);
            cache.put(key, cacheObject);

            if (Objects.nonNull(HEAD)) {
                CacheObject tailCacheObject = cache.get(HEAD);
                tailCacheObject.setPre(key);

                cacheObject.setNext(HEAD);
                HEAD = key;
                return;
            }
            HEAD = key;
            TAIL = key;
            return;
        }

        CacheObject tailCacheObject = cache.get(TAIL);
        cache.remove(TAIL);
        if (Objects.nonNull(tailCacheObject.getPre())) {
            cache.get(tailCacheObject.getPre()).setNext(null);
            TAIL = tailCacheObject.getPre();
        } else {
            HEAD = null;
            TAIL = null;
        }

        CacheObject cacheObject = new CacheObject();
        cacheObject.setValue(value);
        if (Objects.nonNull(HEAD)) {
            cacheObject.setNext(HEAD);
            cache.get(HEAD).setPre(key);
        } else {
            TAIL = key;
        }

        HEAD = key;
        cache.put(key, cacheObject);
    }

    @Data
    public class CacheObject {
        Integer value;
        Integer pre;
        Integer next;
    }

    public static void main(String[] args) {
//        LRUCache lRUCache = new LRUCache(2);
//        lRUCache.put(1, 1); // 缓存是 {1=1}
//        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
//        System.out.println(lRUCache.get(1));    // 返回 1
//        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
//        System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
//        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
//        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
//        System.out.println(lRUCache.get(3));    // 返回 3
//        System.out.println(lRUCache.get(4));    // 返回 4

        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(2, 1); // 缓存是 {1=1}
        lRUCache.put(1, 1); // 缓存是 {1=1, 2=2}
        lRUCache.put(2, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lRUCache.put(4, 1); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
        System.out.println(lRUCache.get(2));    // 返回 3



    }
}
