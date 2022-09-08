package com.knubisoft.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;


@Getter
@Setter
@ToString
public class X {
    private String name;
    private Integer integer;
    private List<String> list;
    private Map<String, Integer> map;
    private Set<Float> set;
    private Queue<Boolean> queue;
}
