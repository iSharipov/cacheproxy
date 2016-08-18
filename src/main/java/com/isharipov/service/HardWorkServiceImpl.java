package com.isharipov.service;

import java.util.Date;

/**
 * Created by Илья on 18.08.2016.
 */
public class HardWorkServiceImpl implements HardWorkService {
    @Override
    public double doHardWork(String item, double value, Date date) {
        return value * value;
    }
}
