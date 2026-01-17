# Vision subsystem

## Role (game/mechanical)

Gives the robot field awareness. Uses AprilTags or object detection to locate
hubs, alliance zones, and FUEL for faster scoring in REBUILT. Provides pose and
target info for aiming and pickup assists.

## Control and configuration (programming)

- Estimates robot pose relative to the field using tag observations.
- Tracks goals or pickup spots and shares angles to the turret and drivebase.
- Flags target confidence so commands can decide when to trust vision over
  odometry.
- Run latency compensation so turret and shooter aim use current data.
- Provide a toggle for driver-assist aiming vs. pure manual control.
- In sim, feed synthetic tag data so autonomous paths can be tested without
  cameras.
- Publishes pose and target info to AdvantageKit; drivebase can fuse it with
  odometry.
- Expose ready/valid signals so commands only act on good frames.
- Document camera mounting offsets in config files to keep transforms correct.

## Code structure and maintenance

- Classes (planned): vision subsystem, pipeline/camera IO, and adapters for
  turret/drivebase consumers.
- Reviewer notes: keep transforms in config, not code; gate outputs with
  confidence to avoid bad frames driving commands.

## TODO

- Select camera hardware and finalize AprilTag pipeline; add calibration steps
  and config fields once hardware is mounted.
