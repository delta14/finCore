package com.fincore.application.cuenta;

import com.fincore.domain.cuenta.Cuenta;
import com.fincore.domain.movimiento.Direccion;
import com.fincore.domain.shared.Money;
import com.fincore.domain.cuenta.SaldoInsuficienteException;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class TransferirUseCaseTest {

    @Test
    void should_transfer_between_accounts() {

        var cuentaRepo =
                new FakeCuentaRepository();

        var movimientoRepo =
                new FakeMovimientoRepository();

        cuentaRepo.save(
                new Cuenta(
                        "A",
                        money("500")
                )
        );

        cuentaRepo.save(
                new Cuenta(
                        "B",
                        money("100")
                )
        );

        var useCase =
                new TransferirUseCase(
                        cuentaRepo,
                        movimientoRepo
                );

        useCase.execute(
                "A",
                "B",
                money("200")
        );

        assertEquals(
                money("300"),
                cuentaRepo
                        .findByNumeroCuenta("A")
                        .orElseThrow()
                        .saldo()
        );

        assertEquals(
                money("300"),
                cuentaRepo
                        .findByNumeroCuenta("B")
                        .orElseThrow()
                        .saldo()
        );

        assertEquals(
                2,
                movimientoRepo
                        .movimientos()
                        .size()
        );

        assertEquals(
                Direccion.DEBIT,
                movimientoRepo
                        .movimientos()
                        .get(0)
                        .direccion()
        );

        assertEquals(
                Direccion.CREDIT,
                movimientoRepo
                        .movimientos()
                        .get(1)
                        .direccion()
        );
    }

    @Test
    void should_fail_when_origin_has_insufficient_balance() {

        var cuentaRepo =
                new FakeCuentaRepository();

        cuentaRepo.save(
                new Cuenta(
                        "A",
                        money("100")
                )
        );

        cuentaRepo.save(
                new Cuenta(
                        "B",
                        money("50")
                )
        );

        var useCase =
                new TransferirUseCase(
                        cuentaRepo,
                        new FakeMovimientoRepository()
                );

        assertThrows(SaldoInsuficienteException.class, ()-> useCase.execute("A","B", money("500")));
    }

    @Test
    void should_reject_transfer_to_same_account() {

        var cuentaRepo = new FakeCuentaRepository();
        var movimientoRepo = new FakeMovimientoRepository();

        cuentaRepo.save(
                new Cuenta(
                        "A",
                        money("100")
                )
        );

        var useCase =
                new TransferirUseCase(
                        cuentaRepo,
                        movimientoRepo
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(
                        "A",
                        "A",
                        money("50")
                )
        );

        assertEquals(
                money("100"),
                cuentaRepo
                        .findByNumeroCuenta("A")
                        .orElseThrow()
                        .saldo()
        );

        assertEquals(
                0,
                movimientoRepo
                        .movimientos()
                        .size()
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