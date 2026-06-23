package com.fincore.application.cuenta;

import com.fincore.domain.cuenta.Cuenta;
import com.fincore.domain.movimiento.Direccion;
import com.fincore.domain.shared.Money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class DepositarUseCaseTest {

    @Test
    void should_deposit_and_register_movement() {

        var cuentaRepo =
                new FakeCuentaRepository();

        var movimientoRepo =
                new FakeMovimientoRepository();

        var clock =
                Clock.fixed(
                        Instant.parse(
                                "2026-06-20T10:00:00Z"
                        ),
                        ZoneOffset.UTC
                );

        var cuenta =
                new Cuenta(
                        "12345",
                        money(
                                "100.00"
                        )
                );

        cuentaRepo.save(
                cuenta
        );

        var useCase =
                new DepositarUseCase(
                        cuentaRepo,
                        movimientoRepo
                );

        useCase.execute(
                "12345",
                money(
                        "50.00"
                )
        );

        var actual =
                cuentaRepo
                        .findByNumeroCuenta(
                                "12345"
                        )
                        .orElseThrow();

        assertEquals(
                money(
                        "150.00"
                ),
                actual.saldo()
        );

        assertEquals(
                1,
                movimientoRepo
                        .movimientos()
                        .size()
        );

        var movimiento =
                movimientoRepo
                        .movimientos()
                        .get(0);

        assertEquals(
                Direccion.CREDIT,
                movimiento.direccion()
        );

        assertEquals(
                money(
                        "50.00"
                ),
                movimiento.monto()
        );
    }

    @Test
    void should_throw_when_account_does_not_exist() {

        var useCase =
                new DepositarUseCase(
                        new FakeCuentaRepository(),
                        new FakeMovimientoRepository()
                );

        assertThrows(
                CuentaNoEncontradaException.class,

                () ->
                        useCase.execute(
                                "404",
                                money(
                                        "50.00"
                                )
                        )
        );
    }

    private Money money(
            String amount
    ) {

        return new Money(
                new BigDecimal(
                        amount
                ),

                Currency.getInstance(
                        "MXN"
                )
        );
    }

}