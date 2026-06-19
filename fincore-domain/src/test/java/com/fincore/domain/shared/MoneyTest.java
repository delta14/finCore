package com.fincore.domain.shared;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    @Test
    void should_consider_same_amount_equal_even_with_different_scale() {

        var a = new Money(
                new BigDecimal("10.0"),
                Currency.getInstance("USD")
        );

        var b = new Money(
                new BigDecimal("10.00"),
                Currency.getInstance("USD")
        );

        assertEquals(a, b);
    }

}