package frc.robot.subsystems.turret;

import frc.robot.shared.subsystems.AbstractSetAndSeekSubsystem;
import frc.robot.subsystems.turret.config.TurretSubsystemConfig;
import frc.robot.subsystems.turret.devices.TurretMotor;

public class TurretSubsystem extends AbstractSetAndSeekSubsystem<TurretSubsystemConfig> {
    /**
     * Builds the turret subsystem with a single SparkMax-driven motor and default motion profile values.
     *
     * @param config turret configuration bundle loaded from JSON
     */
    public TurretSubsystem(TurretSubsystemConfig config) {
        this(config, new TurretMotor(config));
    }

    private TurretSubsystem(TurretSubsystemConfig config, TurretMotor motor) {
        super(config, motor);
    }

}
