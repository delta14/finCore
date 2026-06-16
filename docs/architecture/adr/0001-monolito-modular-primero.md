# ADR-0001: Empezar con un monolito modular en lugar de microservicios

- **Estado:** Aceptado
- **Fecha:** 2026-06-11
- **Decisores:** Rob (con mentoría de arquitectura)

## Contexto

FINCORE debe simular el núcleo de una fintech y, a largo plazo, poder evolucionar a
microservicios. La tentación inicial es arrancar directamente con microservicios
porque "es lo moderno". Pero al inicio del proyecto:

- El dominio (clientes, cuentas, movimientos, transferencias) **todavía no está
  estable**: las fronteras entre módulos cambiarán mucho.
- Es un proyecto de una sola persona con una máquina de 8 GB de RAM.
- No existe aún ninguna necesidad real de escalado independiente por servicio.

Dividir un dominio que todavía no entiendes bien genera fronteras equivocadas que luego
son carísimas de mover (cambios distribuidos, transacciones repartidas, despliegues
acoplados).

## Alternativas consideradas

1. **Microservicios desde el día 1** — Máxima flexibilidad de escalado. Pero:
   complejidad operativa enorme (red, despliegue, observabilidad distribuida),
   fronteras de dominio prematuras, e inviable en 8 GB de RAM. Coste de aprendizaje
   desperdiciado en infraestructura en vez de dominio.
2. **Monolito tradicional (sin modularizar)** — Simple de arrancar, pero degenera en
   "big ball of mud": sin fronteras internas, imposible de extraer servicios después.
3. **Monolito modular (elegido)** — Un solo despliegue, pero con módulos de dominio
   bien delimitados internamente (Clean/Hexagonal). Bajo coste operativo y, a la vez,
   preparado para extraer servicios cuando el dominio madure.

## Decisión

Construir FINCORE como **monolito modular** con Clean Architecture + Hexagonal,
manteniendo límites de módulo explícitos. La migración a microservicios se evaluará
solo cuando el dominio esté estable (Fase 10), extrayendo primero el servicio más
independiente y con distinto perfil de carga.

## Consecuencias

- **Positivas:** foco en el dominio, no en infraestructura; iteración rápida; un solo
  proceso que cabe en 8 GB; refactors de frontera baratos mientras el dominio cambia.
- **Negativas / coste:** disciplina manual para no saltarse las fronteras de módulo
  (sin red que lo impida); todo escala junto por ahora.
- **A revisar más adelante:** cuando un módulo necesite escalar o desplegarse aparte,
  o cuando el equipo crezca, será el momento de reconsiderar (futuro ADR).
