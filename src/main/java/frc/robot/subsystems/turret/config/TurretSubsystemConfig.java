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
    public boolean motorInverted                      = false;

    /** Smart current limit for the turret motor in amps. */
    public int     smartCurrentLimitAmps              = 40;

    /**
     * Gear ratio expressed as motor rotations per one turret rotation. Example: a 100:1 reduction should be written as 100.0.
     */
    public double  motorRotationsPerMechanismRotation = 1.0;

    /** Minimum allowed turret position in degrees (mechanism frame). */
    public double  minimumPositionDegrees             = -180.0;

    /** Maximum allowed turret position in degrees (mechanism frame). */
    public double  maximumPositionDegrees             = 180.0;

    /**
     * Seeds turret profile defaults so the mechanism can move immediately after loading configuration.
     */
    public TurretSubsystemConfig() {
        minimumSetpoint     = minimumPositionDegrees;
        maximumSetpoint     = maximumPositionDegrees;
        maximumVelocity     = 180.0;
        maximumAcceleration = 360.0;
        positionTolerance   = 1.0;
        initialPosition     = 0.0;
        initialVelocity     = 0.0;
    }

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
    public Supplier<Double> getMinimumPositionDegreesSupplier() {
        return () -> readTunableNumber("minimumPositionDegrees", minimumPositionDegrees);
    }

    /**
     * Supplies the maximum turret angle in degrees.
     */
    public Supplier<Double> getMaximumPositionDegreesSupplier() {
        return () -> readTunableNumber("maximumPositionDegrees", maximumPositionDegrees);
    }
}
