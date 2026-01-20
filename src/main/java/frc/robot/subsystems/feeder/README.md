# Feeder subsystem

## Role (game/mechanical)

Centers FUEL inside the hopper so the indexer always receives a clean handoff.
Pulls pieces from left or right into the middle lane and hands them to the
indexer, keeping the conveyor balanced during REBUILT cycles.

## Control and configuration (programming)

- Coordinate with hopper speed so we do not double-feed the center.
- Add sensors or current spikes to detect side jams and switch to a
  pulse/alternate pattern.
- Provide a quick reverse to back pieces out for clearing.
- Default state should be idle; commands from RobotContainer start centering
  when intake is active.
- Telemetry should capture which side is feeding and any detected jams for
  driver feedback.

## Code structure and maintenance

- Classes (planned): feeder subsystem, command factory, and left/right belt IO
  wrappers; add jam-detection helpers when sensors are chosen.
- Reviewer notes: keep interfaces that allow asymmetric control so autos can
  bias one side; log jam events for replay.

## TODO

- Lock in motor layout and sensing approach; add command factory entries when
  hardware is selected.
