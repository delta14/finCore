package com.fincore.domain.cuenta;

import java.util.Optional;

public interface CuentaRepository {

    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);

    void save(Cuenta cuenta);
}
