package com.fincore.domain.movimiento;

import com.fincore.domain.shared.Money;

import java.time.Instant;
import java.util.Objects;

public class Movimiento {

    private final String id;
    private final String cuentaId;
    private final Direccion direccion;
    private final Money monto;
    private final Instant fecha;


    public Movimiento(String id, String cuentaId, Direccion direccion, Money monto, Instant fecha) {
        this.id = Objects.requireNonNull(id, "id requerido");
        this.cuentaId = Objects.requireNonNull(cuentaId, "cientaId requerido");
        this.direccion = Objects.requireNonNull(direccion, "dirección requerida");
        this.monto = Objects.requireNonNull(monto, "monto requerido");
        this.fecha = Objects.requireNonNull(fecha, "fecha requerida");
    }

    public String id() {
        return id;
    }

    public String cuentaId() {
        return cuentaId;
    }

    public Direccion direccion() {
        return direccion;
    }

    public Money monto() {
        return monto;
    }

    public Instant fecha() {
        return fecha;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (!(o instanceof  Movimiento other))
            return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}


