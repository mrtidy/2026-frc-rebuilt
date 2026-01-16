# Turret subsystem

This folder holds the mechanism that spins the shooter turret. It wraps a single
motor with motion limits, gearing, and trapezoidal profile control so commands
can request angles in degrees.

## What lives here

- `TurretSubsystem` – the set-and-seek subsystem that owns motion limits,
  gearing, and logging.
- `commands/MoveTurretToAngleCommand` – drives the turret toward a supplied or
  fixed angle.
- `commands/TurretSubsystemCommandFactory` – builds turret commands and keeps
  RobotContainer wiring clean.
- `config/TurretSubsystemConfig` – tunable values for CAN ID, gear ratio,
  limits, current limits, and profile constraints.
- `devices/TurretMotor` – SparkMax-backed motor wrapper that handles inversion,
  current limits, and unit conversions.

## How it behaves

- Angles are expressed in **degrees** for readability; internal math converts to
  radians for WPILib/AdvantageKit.
- Uses the shared set-and-seek base to apply trapezoidal motion profiles with
  minimum/maximum angle guards.
- Logs targets and positions through AdvantageKit for replay and tuning.
- Falls back to a `SimMotor` in simulation with the same limits and profile
  constraints.

## Configuration notes

- Tune `motorRotationsPerMechanismRotation` to match the turret reduction (motor
  rotations per one turret rotation).
- Minimum/maximum setpoints are in degrees and enforce soft limits in both real
  and sim modes.
- Current limits, inversion, and CAN ID come from `TurretSubsystemConfig`
  (loaded via `subsystems.json`).

## When to edit

- Add new commands when operators need presets or automated aiming behaviors.
- Adjust limits, reductions, or current limits when hardware changes or retuning
  is required.
- Keep logging intact so AdvantageKit captures profiles and targets for
  debugging.

## For Copilot and reviewers

- Preserve the degrees-facing public API; keep conversions inside device
  wrappers.
- Maintain command names ending in `Command` and keep factory helpers in
  `commands/TurretSubsystemCommandFactory`.
- Update this README whenever behaviors, limits, or key classes change.
