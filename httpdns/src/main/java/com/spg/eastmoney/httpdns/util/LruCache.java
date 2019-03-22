package com.spg.eastmoney.httpdns.util;

import java.util.LinkedHashMap;

/**
 * Created by bailong on 15/6/18.
 */
public final class LruCache<K, V> extends LinkedHashMap<K, V> {
    private int size;

    public LruCache() {
        this(256);
    }

    public LruCache(int size) {
        super(size, 1.0f, true);
        this.size = size;
    }

    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return this.size() > this.size;
    }
}
