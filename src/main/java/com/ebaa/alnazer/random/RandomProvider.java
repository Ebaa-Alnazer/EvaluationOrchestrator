package com.ebaa.alnazer.random;

import java.util.Random;

public class RandomProvider {
    private static Random random;

    private RandomProvider() {

    }

    public static int randomInt(final int MAX_EXCLUSIVE) {
        if (random == null ) {
            random = new Random();
        }
        return random.nextInt(MAX_EXCLUSIVE);
    }
}
