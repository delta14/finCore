package com.fincore.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money(
        BigDecimal amount,
        Currency currency
) {

    public Money {

        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);

        if (amount.scale() > 2) {
            throw new IllegalArgumentException(
                    "Máximo 2 decimales permitidos"
            );
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El importe no puede ser negativo"
            );
        }

        amount = amount.setScale(
                2,
                RoundingMode.UNNECESSARY
        );
    }

    public Money add(Money other) {

        validateSameCurrency(other);

        return new Money(
                amount.add(other.amount),
                currency
        );
    }

    public Money subtract(Money other) {

        validateSameCurrency(other);

        return new Money(
                amount.subtract(other.amount),
                currency
        );
    }

    public boolean isGreaterThanOrEqual(Money other) {

        validateSameCurrency(other);

        return amount.compareTo(
                other.amount
        ) >= 0;
    }

    private void validateSameCurrency(
            Money other
    ) {

        Objects.requireNonNull(other);

        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "No se permiten monedas distintas"
            );
        }
    }

}