# FINCORE

Plataforma financiera empresarial de aprendizaje. Simula el núcleo operativo de una
fintech / institución financiera moderna, construida para evolucionar desde un
**monolito modular** hasta **microservicios**.

> Este proyecto es un recorrido de aprendizaje de arquitectura empresarial Java, no
> solo de programación. La prioridad es comprensión, diseño y pensamiento de
> ingeniería sobre velocidad de implementación.

## Stack objetivo

Java 21 · Spring Boot 3 · Maven · PostgreSQL · Spring Security + JWT · Docker ·
Kafka · Spring Batch · Apache POI · OpenPDF / JasperReports · JUnit 5 · Mockito · Git

## Arquitectura

- Clean Architecture + Hexagonal (Ports & Adapters)
- DDD Lite + SOLID
- Event-Driven (primero eventos internos, luego Kafka)
- Capas: **Domain · Application · Infrastructure · Presentation**

## Estructura del repositorio

```
FinCore/
├── README.md
├── .gitignore
└── docs/
    ├── roadmap.md                     # Plan por fases y objetivos de aprendizaje
    └── architecture/
        ├── adr/                       # Architecture Decision Records
        └── diagrams/                  # Diagramas (C4, entidad-relación, secuencia)
```

> La estructura del proyecto Maven (`pom.xml`, `src/`, módulos) se añade en la **Fase 1**.

## Estado actual

**Fase 0 — Cimientos y entorno.** Repositorio y documentación inicializados.

## Entorno de desarrollo

MacBook Apple Silicon M1 · 8 GB RAM. Ver notas de optimización en `docs/roadmap.md`
(usar JDK arm64, Postgres nativo, Docker ligero con Colima/OrbStack, Kafka solo a partir
de la Fase 7).
