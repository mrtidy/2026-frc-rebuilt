package frc.robot.subsystems.turret.commands;

import java.util.function.Supplier;

import frc.robot.shared.commands.AbstractSetAndSeekCommand;
import frc.robot.subsystems.turret.TurretSubsystem;

/**
 * Drives the turret toward a target angle using its trapezoidal profile. Use this for preset positions or coarse moves where hitting the travel
 * limits is acceptable.
 */
public class MoveTurretToAngleCommand extends AbstractSetAndSeekCommand<TurretSubsystem> {

    /**
     * Builds a profiled turret move command that reads the target angle from a supplier.
     *
     * @param turretSubsystem       turret subsystem to control
     * @param targetDegreesSupplier supplier providing the desired turret angle in degrees
     */
    public MoveTurretToAngleCommand(TurretSubsystem turretSubsystem, Supplier<Double> targetDegreesSupplier) {
        super(turretSubsystem, targetDegreesSupplier);
    }

    /**
     * Builds a profiled turret move command that drives to a fixed angle.
     *
     * @param turretSubsystem turret subsystem to control
     * @param targetDegrees   desired turret angle in degrees
     */
    public MoveTurretToAngleCommand(TurretSubsystem turretSubsystem, double targetDegrees) {
        this(turretSubsystem, () -> targetDegrees);
    }
}
