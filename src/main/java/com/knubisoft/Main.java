package com.knubisoft;


import com.knubisoft.model.GenericClass;
import com.knubisoft.model.X;
import com.knubisoft.service.Populate;
import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Main {

    private static final Populate generator = new Populate();


    @SneakyThrows
    public static void main(String[] args) {
        Map<String, Integer> result = new LinkedHashMap<>();
        Integer integer = null;
        String str = null;
        Double doubles = 0.0;
        X x = new X();
        List<String> stringList = new ArrayList<>();
        Set<Float> set = new LinkedHashSet<>();
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        Deque<Integer> deque = new ArrayDeque<>();

        System.out.println(generator.populate
                (generator.unpackGenericClass(new GenericClass<>(x) {}.getType())));
    }
}
