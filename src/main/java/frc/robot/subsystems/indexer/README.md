# Indexer subsystem

## Role (game/mechanical)

Controls when FUEL leaves the robot. Acts as the gate between storage
(hopper/feeder) and the shooter so shots can be timed during REBUILT scoring
windows. Meters one piece at a time to prevent double-feeds and can hold a
preloaded piece for autonomous or volleys.

## Control and configuration (programming)

- Owns the fire/hold logic: do not spin unless shooter RPM and turret aim are
  ready.
- Add a shot-ready flag for autonomous steps so commands can wait before
  advancing.
- Support reverse/clear to back pieces out when the shooter is disabled.
- Default command should keep the gate closed; shooting commands request
  single-step or burst feeds.
- Telemetry should log feed events and piece count so match replay shows timing.

## Code structure and maintenance

- Classes (planned): indexer subsystem, command factory, gate motor IO, and
  sensors for piece detection.
- Reviewer notes: keep a clean ready-to-fire signal for autos; guard against
  double-commanding the gate when shooter is not ready.

## TODO

- Add hardware map, sensors, and command factory once the physical gate design
  is finalized.
