package frc.robot.subsystems.turret.config;

import java.util.function.Supplier;

import frc.robot.shared.config.AbstractSetAndSeekSubsystemConfig;

/**
 * Configuration bundle for the turret mechanism. Values are stored in degrees for readability but converted to radians at runtime where needed.
 */
public class TurretSubsystemConfig extends AbstractSetAndSeekSubsystemConfig {

    /** CAN device ID of the turret SparkMax. */
    public int     motorCanId;

    /** True if the turret motor output should be inverted. */
    public boolean motorInverted;

    /** Smart current limit for the turret motor in amps. */
    public int     smartCurrentLimitAmps;

    /**
     * Gear ratio expressed as motor rotations per one turret rotation. Example: a 100:1 reduction should be written as 100.0.
     */
    public double  motorRotationsPerMechanismRotation;

    /**
     * Supplies the CAN ID (not typically tuned, but exposed for consistency/logging).
     */
    public Supplier<Integer> getMotorCanIdSupplier() {
        return () -> (int) readTunableNumber("motorCanId", motorCanId);
    }

    /**
     * Supplies whether the turret motor output is inverted.
     */
    public Supplier<Boolean> getMotorInvertedSupplier() {
        return () -> readTunableBoolean("motorInverted", motorInverted);
    }

    /**
     * Supplies the smart current limit in amps.
     */
    public Supplier<Integer> getSmartCurrentLimitSupplier() {
        return () -> (int) readTunableNumber("smartCurrentLimitAmps", smartCurrentLimitAmps);
    }

    /**
     * Supplies the gear ratio (motor rotations per mechanism rotation).
     */
    public Supplier<Double> getMotorRotationsPerMechanismRotationSupplier() {
        return () -> readTunableNumber("motorRotationsPerMechanismRotation", motorRotationsPerMechanismRotation);
    }

    /**
     * Supplies the minimum turret angle in degrees.
     */
    public Supplier<Double> getMinimumSetpointSupplier() {
        return () -> readTunableNumber("minimumSetpoint", minimumSetpoint);
    }

    /**
     * Supplies the maximum turret angle in degrees.
     */
    public Supplier<Double> getMaximumSetpointSupplier() {
        return () -> readTunableNumber("maximumSetpoint", maximumSetpoint);
    }
}
