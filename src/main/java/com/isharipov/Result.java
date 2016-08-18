package com.isharipov;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Илья on 18.08.2016.
 */
public class Result implements Serializable {
    private final Map<String, Double> result;

    public Result(Map<String, Double> result) {
        this.result = result;
    }

    public Map<String, Double> getResult() {
        return result;
    }
}
