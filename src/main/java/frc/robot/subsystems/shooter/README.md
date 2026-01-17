# Shooter subsystem

## Role (game/mechanical)

Spins up flywheels to launch FUEL across the field or into the scoring hub.
Needs steady RPM so volleys stay accurate during REBUILT matches, with close-
range and long-range setpoints to cover scoring zones.

## Control and configuration (programming)

- Drives one or more wheels to a target RPM and holds it under load.
- Expose a ready-to-fire signal when RPM is within tolerance and stable.
- Include spin-down behavior to save battery when idle; keep a quick-spool
  option for defense-heavy cycles.
- Use feedforward plus PID to maintain speed when the indexer loads a piece.
- Default command should idle; RobotContainer binds presets for common
  distances.
- Log target RPM, actual RPM, and ready flags so drivers and autonomous can time
  shots.

## Code structure and maintenance

- Classes (planned): shooter subsystem, command factory for RPM presets, and
  wheel motor IO wrappers with sensors.
- Reviewer notes: keep presets organized and avoid magic numbers; log ready
  state so autos can gate shots on RPM stability.

## TODO

- Choose wheel count, motor types, and sensors; add tuning presets once the
  hardware is on the bot.
