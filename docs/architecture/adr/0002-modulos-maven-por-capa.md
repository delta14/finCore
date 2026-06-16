# ADR-0002: Organizar los módulos Maven por capa (con paquetes por contexto)

- **Estado:** Aceptado
- **Fecha:** 2026-06-15
- **Decisores:** Rob (con mentoría de arquitectura)

## Contexto

FINCORE arranca como monolito modular (ver ADR-0001) y debe poder extraer
microservicios en el futuro. Necesitamos imponer la *dependency rule* de Clean
Architecture (las dependencias apuntan hacia el dominio; el dominio no conoce Spring
ni JPA). El dominio todavía se está descubriendo y es un proyecto de una sola persona.

## Alternativas consideradas

1. **Un solo módulo, fronteras por paquetes** — Cero ceremonia, pero la regla de
   dependencia es solo convención: nada impide que el dominio importe Spring.
   (Reforzable con ArchUnit.)
2. **Módulos Maven por capa** — `domain / application / infrastructure / presentation`.
   La frontera la impone el compilador: el módulo `domain` no tiene Spring/JPA en su
   classpath, así que el import ilegal no compila. Pero mezcla todos los contextos de
   negocio dentro de cada módulo.
3. **Módulos Maven por contexto** — Un módulo por bounded context (cuentas, clientes…),
   con capas hexagonales dentro de cada uno. Más cercano a la futura partición a
   microservicios, pero mucha más estructura desde el día 1.

## Decisión

**Módulos Maven por capa** (`fincore-domain`, `fincore-application`,
`fincore-infrastructure`, `fincore-presentation`), con un `pom.xml` padre que coordina.
Dentro de cada módulo, **los paquetes se organizan por contexto de negocio**
(`com.fincore.domain.cliente`, `...cuenta`, `...movimiento`), no en un paquete único.

Regla mental: **módulos por capa, paquetes por contexto.**

## Consecuencias

- **Positivas:** simple de arrancar y de entender; la *dependency rule* queda impuesta
  por Maven (el dominio no puede importar infraestructura); build incremental.
- **Negativas / coste:** varios `pom.xml` (más ceremonia); riesgo de acoplar contextos
  dentro de un mismo módulo de capa — mitigado por la convención de paquetes por
  contexto y, más adelante, por tests de arquitectura (ArchUnit).
- **A revisar más adelante:** si un contexto necesita extraerse a microservicio o el
  acoplamiento entre contextos crece, reorganizar hacia módulos por contexto (futuro ADR).
