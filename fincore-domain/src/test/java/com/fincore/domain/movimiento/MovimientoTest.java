package com.fincore.domain.movimiento;

import com.fincore.domain.shared.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MovimientoTest {

    @Test
    void should_compare_only_by_id() {

        var m1 = new Movimiento(
                "MOV-1",
                "CTA-1",
                Direccion.CREDIT,
                new Money(
                        new BigDecimal("100.00"),
                        Currency.getInstance("MXN")
                ),
                Instant.now()
        );

        var m2 = new Movimiento(
                "MOV-1",
                "CTA-999",
                Direccion.DEBIT,
                new Money(
                        new BigDecimal("500.00"),
                        Currency.getInstance("USD")
                ),
                Instant.now()
        );

        assertEquals(
                m1,
                m2
        );
    }



}