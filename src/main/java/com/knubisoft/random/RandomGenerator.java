package com.knubisoft.random;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomGenerator {
    private final Random random = new Random();


    public Integer setInt(){
        return random.nextInt();
    }

    public Double setDouble(){
        return random.nextDouble();
    }

    public Long setLong(){
       return random.nextLong();
    }

    public Float setFloat(){
        return random.nextFloat();
    }

    public Boolean setBoolean(){
        return random.nextBoolean();
    }

    public String setString(){
        return RandomStringUtils.random(5);
    }
}
