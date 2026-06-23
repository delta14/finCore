package com.fincore.application.cuenta;

public class CuentaNoEncontradaException extends RuntimeException{


    public CuentaNoEncontradaException(String numeroCuenta){
        super("Cuenta no encontrada: " + numeroCuenta);
    }
}
