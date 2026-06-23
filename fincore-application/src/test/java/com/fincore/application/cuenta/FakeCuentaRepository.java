package com.fincore.application.cuenta;

import com.fincore.domain.cuenta.Cuenta;
import com.fincore.domain.cuenta.CuentaRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeCuentaRepository implements CuentaRepository {

    private final Map<String, Cuenta> cuentas = new HashMap<>();

    @Override
    public Optional<Cuenta> findByNumeroCuenta( String numeroCuenta){
        return Optional.ofNullable(cuentas.get(numeroCuenta));
    }

    @Override
    public void save(Cuenta cuenta){
        cuentas.put(cuenta.numeroCuenta(), cuenta);
    }
}
