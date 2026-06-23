package com.fincore.application.cuenta;

import com.fincore.domain.cuenta.Cuenta;
import com.fincore.domain.cuenta.CuentaRepository;
import com.fincore.domain.movimiento.Direccion;
import com.fincore.domain.movimiento.Movimiento;
import com.fincore.domain.movimiento.MovimientoRepository;
import com.fincore.domain.shared.Money;

import java.time.Instant;
import java.util.UUID;

public class DepositarUseCase {

    private final CuentaRepository cuentaRepository;

    private final MovimientoRepository movimientoRepository;

    public DepositarUseCase(CuentaRepository cuentaRepository, MovimientoRepository movimientoRepository){

        this.cuentaRepository = cuentaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    public void execute(String numeroCuenta, Money monto){

        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta).orElseThrow(
                () -> new CuentaNoEncontradaException(numeroCuenta)
        );

        cuenta.abonar(monto);

        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento(UUID.randomUUID().toString(), cuenta.numeroCuenta(), Direccion.CREDIT, monto, Instant.now());

        movimientoRepository.save(movimiento);
    }
}
