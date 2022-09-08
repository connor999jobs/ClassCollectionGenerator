package com.knubisoft.service;

import com.knubisoft.model.GenericClass;
import com.knubisoft.random.RandomGenerator;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

public class Populate {
    Map<Class<?>, Supplier<Object>> generator = new LinkedHashMap<>();
    RandomGenerator random = new RandomGenerator();

    {
        generator.put(Integer.class, () -> random.setInt());
        generator.put(Boolean.class, () -> random.setBoolean());
        generator.put(Double.class, () -> random.setDouble());
        generator.put(String.class, () -> random.setString());
        generator.put(Float.class, () -> random.setFloat());
        generator.put(Long.class, () -> random.setLong());
    }


    @SneakyThrows
    public Object populate(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            Object parameterizedCollection = getParameterizedCollection(parameterizedType);
            if (parameterizedCollection != null) {
                return parameterizedCollection;
            }
        }
        if (isSimpleType(type)) {
            return generator.get(type).get();
        } else {
            return generateCustomClassInstance((Class) type);
        }
    }

    public Type unpackGenericClass(Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        return parameterizedType.getRawType().equals(GenericClass.class) ?
                parameterizedType.getActualTypeArguments()[0] : type;
    }


    @SneakyThrows
    private Object getParameterizedCollection(ParameterizedType parameterizedType)  {
        Type rawType = parameterizedType.getRawType();
        if (List.class.isAssignableFrom((Class<?>) rawType)) {
            return generateList(parameterizedType);
        }
        if (Set.class.isAssignableFrom((Class<?>) rawType)) {
            return generateSet(parameterizedType);
        }
        if (Map.class.isAssignableFrom((Class<?>) rawType)) {
            return generateMap(parameterizedType);
        }
        if (Queue.class.isAssignableFrom((Class<?>) rawType)) {
            return generateQueue(parameterizedType);
        }
        if (Deque.class.isAssignableFrom((Class<?>) rawType)) {
            return generateDeque(parameterizedType);
        }
        else {
            throw new ClassNotFoundException(rawType + "is not supported");
        }
    }


    @SneakyThrows
    private String generateCustomClassInstance(Class type) {
        Class<?> cls = Class.forName(type.getName());
        Field[] fields = cls.getDeclaredFields();
        Object instance = cls.getDeclaredConstructor().newInstance();
        for (Field f : fields) {
            f.setAccessible(true);
            f.set(instance, populate(f.getGenericType()));
        }
        return instance.toString();
    }

    private Map<Object, Object> generateMap(ParameterizedType parameterizedType) {
        Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
        Type[] nestedMapTypes = parameterizedType.getActualTypeArguments();
        for (int i = 0; i < 5; i++) {
            resultMap.put(populate(nestedMapTypes[0]), populate(nestedMapTypes[1]));
        }
        return resultMap;
    }

    private List<Object> generateList(ParameterizedType parameterizedType) {
        Type[] types = parameterizedType.getActualTypeArguments();
        List<Object> resultList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            resultList.add(populate(types[0]));
        }
        return resultList;
    }

    private Queue<Object> generateQueue(ParameterizedType parameterizedType) {
        Type[] types = parameterizedType.getActualTypeArguments();
        Queue<Object> resultQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 5; i++) {
            resultQueue.add(populate(types[0]));
        }
        return resultQueue;
    }

    private Deque<Object> generateDeque(ParameterizedType parameterizedType) {
        Type[] types = parameterizedType.getActualTypeArguments();
        Deque<Object> resultDequeue = new ArrayDeque<>();
        for (int i = 0; i < 5; i++) {
            resultDequeue.add(populate(types[0]));
        }
        return resultDequeue;
    }

    private Set<Object> generateSet(ParameterizedType parameterizedType) {
        Type[] types = parameterizedType.getActualTypeArguments();
        Set<Object> resultSet = new LinkedHashSet<>();
        for (int i = 0; i < 5; i++) {
            resultSet.add(populate(types[0]));
        }
        return resultSet;
    }

    private boolean isSimpleType(Object x) {
        return generator.containsKey(x);
    }


}
