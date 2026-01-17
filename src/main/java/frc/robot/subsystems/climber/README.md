# Climber subsystem

## Role (game/mechanical)

Raises the robot onto tower rungs (1, 2, or 3) for endgame points in REBUILT.
Extends and retracts arms or hooks for staged climbs: latch lower rung, transfer
weight, then reach higher. Holds position under load to prevent backdrive while
waiting for the buzzer.

## Control and configuration (programming)

- Add limit switches or encoders for each stage so the driver sees progress.
- Provide a manual override mode for recovery if auto-sequences fail.
- Include current limiting and soft stops to protect hardware on rung contact.
- Default state should keep motors braked and stationary.
- Autonomous/teleop commands should run scripted climb sequences with clear
  abort paths.
- Log stage transitions and time-to-climb so we can tune for faster endgame
  cycles.

## Code structure and maintenance

- Classes (planned): climber subsystem, command factory for staged climbs,
  actuator IO wrappers, and sensor interfaces.
- Reviewer notes: keep state machine for stages simple and observable; ensure
  overrides can safely interrupt sequences.

## TODO

- Decide on actuator types, sensors, and sequencing before adding command
  implementations.
