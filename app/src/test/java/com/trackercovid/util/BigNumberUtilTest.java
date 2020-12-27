package com.trackercovid.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BigNumberUtilTest {

    @Test
    public void format() {
        // arrange
        long[] numbers = {150376, 1496864};
        String[] expected = {"150k", "1.4M"};

        // act
        for (int i = 0; i < numbers.length; i++) {
            long n = numbers[i];
            String formatted = BigNumberUtil.format(n);
            // assert
            assertEquals(expected[i], formatted);
        }
    }
}