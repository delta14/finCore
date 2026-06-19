package com.fincore.domain.cuenta;

public class SaldoInsuficienteException
        extends RuntimeException {

    public SaldoInsuficienteException() {

        super(
                "Saldo insuficiente para realizar la operación"
        );
    }

}