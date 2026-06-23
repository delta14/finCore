package com.fincore.application.cuenta;

import com.fincore.domain.movimiento.Movimiento;
import com.fincore.domain.movimiento.MovimientoRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class FakeMovimientoRepository implements MovimientoRepository {

    private final List<Movimiento> movimientos = new ArrayList<>();

    @Override
    public void save(Movimiento movimiento){
        movimientos.add(movimiento);
    }

    @Override
    public List<Movimiento> findByCuentaIdAndPeriodo(String cuentaId, Instant desde, Instant hasta, int page, int size){
        return movimientos;
    }

    public List<Movimiento> movimientos(){
        return movimientos;
    }
}
