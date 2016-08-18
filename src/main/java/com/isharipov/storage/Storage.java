package com.isharipov.storage;

import com.isharipov.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Илья on 18.08.2016.
 */
public class Storage {
    private static final Map<String, Result> cacheStorage = new HashMap<>();

    public static Map<String, Result> getCacheStorage() {
        return cacheStorage;
    }
}
