# FINCORE — Roadmap de construcción y aprendizaje

> Perfil asumido: Java + Spring intermedio · ~12 h/semana.
> Duración total estimada: **~7-9 meses**. Es un proyecto de portfolio serio.

## Principio de secuenciación

**Dominio primero.** Construimos y estabilizamos el dominio dentro de un monolito
modular *antes* de introducir infraestructura compleja (batch, eventos, Kafka), y
dejamos los microservicios para el final. Meter Kafka o microservicios pronto es el
error más común: añade complejidad sin un dominio que la justifique.

Regla de mentoría: si en algún punto te entran ganas de "saltar a Kafka" o "ya meter
microservicios", esa es justo la señal para frenar y preguntarte qué problema real lo
justifica.

## Restricción de entorno (M1, 8 GB)

El cuello de botella del proyecto es la RAM, no Java. Nunca corras todo a la vez.

- **JDK 21 nativo arm64** (Temurin/Zulu vía SDKMAN!). Verifica que `java -version`
  diga `aarch64`.
- **Postgres nativo** (Homebrew) en el día a día; Docker solo cuando haga falta.
- **Docker ligero**: Colima u OrbStack en vez de Docker Desktop.
- **Kafka** solo desde la Fase 7; modo KRaft, un broker; considera Redpanda.
- **IDE**: IntelliJ Community con heap limitado, o VS Code + Java si va justo.

---

## Fases

### Fase 0 — Cimientos y entorno · ~1 sem
Validar entorno; refrescar Java 21 moderno (records, sealed, pattern matching, virtual
threads); estructura Maven multi-módulo.
**Aprendes:** por qué Java 21 cambia el modelado de dominio (records para Value Objects,
sealed para estados).

### Fase 1 — Esqueleto del monolito modular + arquitectura · ~2-3 sem
Cuatro capas (Domain / Application / Infrastructure / Presentation) con Hexagonal sobre
**Clientes** y **Cuentas**.
**Aprendes:** qué va en cada capa y por qué; puerto vs. adaptador; cómo Clean
Architecture protege el dominio de Spring. La fase más conceptual y la más valiosa.

### Fase 2 — Núcleo de dominio: dinero y consistencia · ~3-4 sem
**Movimientos** y **Transferencias**. Representación de dinero (nunca `double`; Value
Object `Money` con `BigDecimal`), idempotencia, integridad transaccional, doble partida
contable, optimistic vs pessimistic locking.
**Aprendes:** DDD aplicado (Agregados, invariantes, transacciones atómicas). El corazón
fintech.

### Fase 3 — Seguridad empresarial · ~2 sem
**Seguridad**: Spring Security + JWT; roles ADMIN/AUDITOR/OPERATOR/CUSTOMER;
autorización por método.
**Aprendes:** autenticación vs. autorización; por qué los permisos viven en el dominio
y no solo en anotaciones.

### Fase 4 — Persistencia a escala · ~3 sem
Diseño de BD (conceptual → lógico → físico), índices, constraints, **particionamiento**
de movimientos; simulación de volumen (100k clientes, 500k cuentas, 10M movimientos).
Incluye **Auditoría**.
**Aprendes:** dónde están los cuellos de botella reales; cómo el modelo físico difiere
del lógico; indexación para millones de filas.

### Fase 5 — Procesamiento masivo (Spring Batch) · ~3-4 sem
**Procesos Batch**: cierre diario, generación de intereses, consolidación, estados de
cuenta, procesos nocturnos.
**Aprendes:** arquitectura Job/Step/Chunk; reinicio y recuperación; por qué batch ≠ un
bucle grande.

### Fase 6 — Reportes · ~2 sem
**Reportes** + **Productos Financieros**: Excel (Apache POI), PDF (OpenPDF vs
JasperReports), estados de cuenta.
**Aprendes:** cuándo cada herramienta — POI para datos tabulares, Jasper para plantillas
complejas, OpenPDF para control fino.

### Fase 7 — Event-Driven: del monolito a Kafka · ~3-4 sem
Primero **eventos de dominio internos + patrón Outbox** (sin Kafka). Solo después,
**Kafka**: topics, particiones, consumer groups; eventos `TransferCreated`,
`FraudDetected`, `BatchCompleted`.
**Aprendes:** acoplar con eventos vs. con llamadas directas; por qué Kafka es una
decisión con coste, no magia.

### Fase 8 — Consumidores de eventos · ~3 sem
**Motor Antifraude**, **Conciliación** y **Notificaciones** como consumidores de los
eventos de la Fase 7.
**Aprendes:** arquitectura reactiva real; procesamiento asíncrono; idempotencia en
consumidores.

### Fase 9 — Dashboard operativo + observabilidad · ~2 sem
**Dashboard Operativo**, métricas, health checks, trazas.
**Aprendes:** cómo se opera un sistema financiero en producción.

### Fase 10 — Extracción a microservicios · ~3-4 sem
Con el dominio estable: qué servicio extraer primero (el más independiente y con distinto
perfil de carga), qué dejar en el monolito, riesgos de la migración.
**Aprendes:** microservicios es una decisión organizativa y de escalado, no un objetivo
en sí.

---

## Método de enseñanza (en cada solución)

1. Concepto → 2. Problema que resuelve → 3. Decisión arquitectónica → 4. Uso en
empresas reales → 5. Ventajas → 6. Desventajas → 7. Alternativas → 8. Cómo escala.

El código solo después de la explicación, en piezas pequeñas y controladas, con cada
clase y responsabilidad explicada.
