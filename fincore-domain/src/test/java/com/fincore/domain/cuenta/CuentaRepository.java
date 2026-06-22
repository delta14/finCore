package com.fincore.domain.cuenta;

import java.util.Optional;

public interface CuentaRepository {

    Optional<Cuenta> findByNoCuenta(String numeroCuenta);

    void save(Cuenta cuenta);
}
