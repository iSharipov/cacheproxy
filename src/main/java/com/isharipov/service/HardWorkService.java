package com.isharipov.service;

import com.isharipov.annotation.Cache;

import java.util.Date;

/**
 * Created by Илья on 18.08.2016.
 */
public interface HardWorkService {
    @Cache(cacheType = Cache.CacheType.IN_MEMORY, fileNamePrefix = "data", zip = true, identityBy = {String.class, double.class})
    double doHardWork(String item, double value, Date date);
}
