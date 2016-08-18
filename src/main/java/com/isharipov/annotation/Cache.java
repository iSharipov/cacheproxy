package com.isharipov.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Илья on 18.08.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {


    CacheType cacheType() default CacheType.IN_MEMORY;

    Class[] identityBy() default DEFAULT.class;

    String fileNamePrefix() default "";

    String key() default "";

    boolean zip() default false;

    int listList() default 0;

    public enum CacheType {
        FILE, IN_MEMORY
    }

    static final class DEFAULT {
    }
}
