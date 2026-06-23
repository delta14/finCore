package com.fincore.domain.movimiento;

import java.time.Instant;
import java.util.List;

public interface MovimientoRepository {

    void save(Movimiento movimiento);

    List<Movimiento> findByCuentaIdAndPeriodo(
            String cuentaId,
            Instant desde,
            Instant hasta,
            int page,
            int size
    );
}
