# Intake subsystem

## Role (game/mechanical)

Grabs loose FUEL from the carpet and hands it to the hopper so we can bank shots
for later. Deploys and spins rollers to pull FUEL from the field, then hands
pieces straight into the hopper entrance; no storage of its own. Runs fast in
teleop for cycle speed and retracts when we do not want stray game pieces.

## Control and configuration (programming)

- Needs an explicit enable/disable trigger so drivers can stop intake before
  crossing protected zones.
- Monitor current draw to detect jams and auto-pulse the rollers if needed.
- In sim, mirror timing so hopper/feeder logic can still be tested.
- Expose a boolean hasFuel-style signal if sensors exist; otherwise rely on
  hopper sensors.
- Default command should keep rollers stopped; RobotContainer binds operator
  buttons for intake/un-intake.

## Code structure and maintenance

- Classes (planned): intake subsystem class, command factory, and device IO
  wrapper to be added when hardware is chosen.
- Reviewer notes: keep public API simple (intake, outtake, stop) and log state
  transitions so AdvantageKit replays show cycles.

## TODO

- Flesh out command list and sensor strategy once hardware layout and wiring are
  finalized.
