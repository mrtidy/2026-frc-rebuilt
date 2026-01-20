# Hopper subsystem

## Role (game/mechanical)

Holds collected FUEL and keeps it moving toward the center of the robot. Uses an
active floor or belts to pull FUEL from the intake side toward the feeder slot,
spacing pieces and preventing bounce-back while pre-staging for shots.

## Control and configuration (programming)

- Coordinate motor speed with feeder to avoid overstuffing the center lane.
- Sensor coverage near the feeder exit helps throttle flow so the indexer can
  decide when to shoot.
- Provide a clear manual override to reverse and clear jams.
- Default command should idle the floor; RobotContainer can bind a “stage fuel”
  action.
- Logs should capture piece counts or sensor states so we know how much is
  banked before shooting.

## Code structure and maintenance

- Classes (planned): hopper subsystem class, command factory, and floor device
  IO; add sensor interfaces once chosen.
- Reviewer notes: keep flow coordination hooks so feeder/indexer can request
  slow/fast modes; log sensor states for replay.

## TODO

- Define sensor placement and motor configuration once mechanical packaging is
  set.
