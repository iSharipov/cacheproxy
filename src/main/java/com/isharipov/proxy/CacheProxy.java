package com.isharipov.proxy;

import com.isharipov.Result;
import com.isharipov.annotation.Cache;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.isharipov.storage.Storage.getCacheStorage;

/**
 * Created by Илья on 18.08.2016.
 */
public class CacheProxy implements InvocationHandler {

    private Object object;

    private CacheProxy(Object object) {
        this.object = object;
    }

    public static Object newInstance(Object object) {
        return java.lang.reflect.Proxy.newProxyInstance(
                object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                new CacheProxy(object));
    }

    public static void main(String[] args) {
        Class[] classes = {String.class, Object.class};
        Class[] classe2 = {String.class};

        String s = Arrays.toString(classes).replace("[", "").replace("]", "").trim();
        String s2 = Arrays.toString(classe2).replace("[", "").replace("]", "").trim();
        System.out.println(s.contains(s2));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.isAnnotationPresent(Cache.class)) {
            for (Annotation cache : method.getDeclaredAnnotations()) {
                Cache annotation = method.getAnnotation(Cache.class);
                Class[] identities = Arrays.asList(annotation.identityBy()).contains(Cache.DEFAULT.class)
                        ? method.getParameterTypes() : annotation.identityBy();
                String key = annotation.key().isEmpty() ? method.getName() : annotation.key();

                String identitiesKey = Arrays.toString(identities).replace("[", "").replace("]", "").trim();
                if (annotation.cacheType().equals(Cache.CacheType.IN_MEMORY)) {
                    if (getCacheStorage().containsKey(key)) {
                        Result result = getCacheStorage().get(key);
                        for (Map.Entry<String, Double> entry : result.getResult().entrySet()) {
                            if (entry.getKey().contains(identitiesKey)) {
                                return entry.getValue();
                            }
                        }
                    } else {
                        Map<String, Double> res = new HashMap<>();
                        res.put(identitiesKey, (Double) invoke(method, args));
                        getCacheStorage().put(key, new Result(res));
                        return res.get(identitiesKey);
                    }
                } else {
                    String fileName = annotation.fileNamePrefix().isEmpty() ? method.getName() : annotation.fileNamePrefix();
                    if (annotation.zip()) {
                        File file = new File(fileName + ".zip");
                        file.exists();
                    }
                }
            }
        } else {
            return invoke(proxy, method, args);
        }
        return null;
    }

    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(object, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Impossible");
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}

