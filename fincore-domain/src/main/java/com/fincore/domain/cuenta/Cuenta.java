package com.fincore.domain.cuenta;

import com.fincore.domain.shared.Money;

import java.util.Currency;
import java.util.Objects;

public class Cuenta {

    private final String numeroCuenta;

    private final Currency moneda;

    private Money saldo;

    public Cuenta(String numeroCuenta, Money saldoInicial) {

        this.numeroCuenta =
                Objects.requireNonNull(
                        numeroCuenta
                );

        this.saldo =
                Objects.requireNonNull(
                        saldoInicial
                );

        this.moneda = saldoInicial.currency();
    }

    public void abonar(Money monto) {

        validateCurrency(monto);

        saldo = saldo.add(monto);
    }

    public void cargar(Money monto) {

        validateCurrency(monto);

        if (!saldo.isGreaterThanOrEqual(
                monto
        )) {

            throw new SaldoInsuficienteException();
        }

        saldo = saldo.subtract(
                monto
        );
    }

    private void validateCurrency(Money monto) {

        if (!moneda.equals(
                monto.currency()
        )) {

            throw new IllegalArgumentException(
                    "La moneda no coincide con la cuenta"
            );
        }
    }

    public String numeroCuenta() {

        return numeroCuenta;
    }

    public Money saldo() {

        return saldo;
    }

    public Currency moneda(){
        return moneda;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Cuenta cuenta))
            return false;

        return numeroCuenta.equals(
                cuenta.numeroCuenta
        );
    }

    @Override
    public int hashCode() {
        return numeroCuenta.hashCode();
    }

}