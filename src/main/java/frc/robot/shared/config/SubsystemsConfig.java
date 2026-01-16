package frc.robot.shared.config;

import frc.robot.subsystems.drivebase.config.DriveBaseSubsystemConfig;
import frc.robot.subsystems.turret.config.TurretSubsystemConfig;

/**
 * Root configuration bundle for every subsystem. Individual subsystems can be toggled or tuned via this object after loading JSON from the deploy
 * directory.
 */
public class SubsystemsConfig {

    public DriveBaseSubsystemConfig driveBaseSubsystem = new DriveBaseSubsystemConfig();

    public TurretSubsystemConfig    turretSubsystem    = new TurretSubsystemConfig();
}
