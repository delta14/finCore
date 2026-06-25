package com.fincore.application.cuenta;

import com.fincore.domain.cuenta.Cuenta;
import com.fincore.domain.cuenta.CuentaRepository;
import com.fincore.domain.movimiento.Direccion;
import com.fincore.domain.movimiento.Movimiento;
import com.fincore.domain.movimiento.MovimientoRepository;
import com.fincore.domain.shared.Money;

import java.time.Instant;
import java.util.UUID;

public class TransferirUseCase {

    private final CuentaRepository cuentaRepository;

    private final MovimientoRepository movimientoRepository;

    public TransferirUseCase(
            CuentaRepository cuentaRepository,
            MovimientoRepository movimientoRepository
    ) {

        this.cuentaRepository =
                cuentaRepository;

        this.movimientoRepository =
                movimientoRepository;
    }

    public void execute(
            String origenId,
            String destinoId,
            Money monto
    ) {

        if (origenId.equals(
                destinoId
        )) {

            throw new IllegalArgumentException(
                    "No se puede transferir a la misma cuenta"
            );
        }

        Cuenta origen =
                cuentaRepository
                        .findByNumeroCuenta(
                                origenId
                        )
                        .orElseThrow(
                                () ->
                                        new CuentaNoEncontradaException(
                                                origenId
                                        )
                        );

        Cuenta destino =
                cuentaRepository
                        .findByNumeroCuenta(
                                destinoId
                        )
                        .orElseThrow(
                                () ->
                                        new CuentaNoEncontradaException(
                                                destinoId
                                        )
                        );

        if (!origen.moneda()
                .equals(
                        destino.moneda()
                )) {

            throw new IllegalArgumentException(
                    "Las cuentas deben tener misma moneda"
            );
        }

        origen.cargar(
                monto
        );

        destino.abonar(
                monto
        );

        cuentaRepository.save(
                origen
        );

        cuentaRepository.save(
                destino
        );

        movimientoRepository.save(
                new Movimiento(
                        UUID.randomUUID().toString(),
                        origen.numeroCuenta(),
                        Direccion.DEBIT,
                        monto,
                        Instant.now()
                )
        );

        movimientoRepository.save(
                new Movimiento(
                        UUID.randomUUID().toString(),
                        destino.numeroCuenta(),
                        Direccion.CREDIT,
                        monto,
                        Instant.now()
                )
        );
    }

}