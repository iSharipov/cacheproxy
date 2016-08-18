package com.isharipov;

import com.isharipov.proxy.CacheProxy;
import com.isharipov.service.HardWorkService;
import com.isharipov.service.HardWorkServiceImpl;

import java.util.Date;

/**
 * Created by Илья on 18.08.2016.
 */
public class Main {
    public static void main(String[] args) {
        HardWorkService hardWorkService = (HardWorkService) CacheProxy.newInstance(new HardWorkServiceImpl());
        //From Memory Cache
        System.out.println(hardWorkService.doHardWork("Hello", 5, new Date()));
        System.out.println(hardWorkService.doHardWork("Hello", 5, new Date()));
    }
}
