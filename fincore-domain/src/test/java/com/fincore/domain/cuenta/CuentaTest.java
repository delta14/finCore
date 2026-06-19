    package com.fincore.domain.cuenta;

    import com.fincore.domain.shared.Money;
    import org.junit.jupiter.api.Test;

    import java.math.BigDecimal;
    import java.util.Currency;

    import static org.junit.jupiter.api.Assertions.*;

    class CuentaTest {

        @Test
        void should_update_balance_after_operations() {

            var cuenta = new Cuenta(
                    "123456789",
                    new Money(
                            new BigDecimal("100.00"),
                            Currency.getInstance("MXN")
                    )
            );

            cuenta.abonar(
                    new Money(
                            new BigDecimal("50.00"),
                            Currency.getInstance("MXN")
                    )
            );

            cuenta.cargar(
                    new Money(
                            new BigDecimal("30.00"),
                            Currency.getInstance("MXN")
                    )
            );

            assertEquals(
                    new Money(
                            new BigDecimal("120.00"),
                            Currency.getInstance("MXN")
                    ),
                    cuenta.saldo()
            );
        }

        @Test
        void should_throw_exception_when_balance_is_insufficient() {

            var cuenta = new Cuenta(
                    "123456789",
                    new Money(
                            new BigDecimal("100.00"),
                            Currency.getInstance("MXN")
                    )
            );

            assertThrows(
                    SaldoInsuficienteException.class,
                    () -> cuenta.cargar(
                            new Money(
                                    new BigDecimal("150.00"),
                                    Currency.getInstance("MXN")
                            )
                    )
            );
        }

    }