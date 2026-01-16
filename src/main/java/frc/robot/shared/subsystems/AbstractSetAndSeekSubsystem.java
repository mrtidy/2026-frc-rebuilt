package frc.robot.shared.subsystems;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import frc.robot.devices.Motor;
import frc.robot.devices.motor.MotorIOInputsAutoLogged;
import frc.robot.shared.config.AbstractSetAndSeekSubsystemConfig;

/**
 * Base subsystem that generates and follows a trapezoidal motion profile.
 * <p>
 * Concrete mechanisms should extend this class to gain a simple "set and seek" API: call {@link #setTarget(double)} to define a goal and repeatedly
 * call {@link #seekTarget()} from a command to step the profile forward. Motor control is intentionally left as a no-op so hardware bindings can be
 * added later.
 * </p>
 */
public abstract class AbstractSetAndSeekSubsystem<TConfig extends AbstractSetAndSeekSubsystemConfig> extends AbstractSubsystem<TConfig> {
    protected final Motor                   motor;

    protected final MotorIOInputsAutoLogged motorInputs = new MotorIOInputsAutoLogged();

    protected TrapezoidProfile.Constraints  constraints;

    protected TrapezoidProfile              profile;

    protected TrapezoidProfile.State        goalState;

    protected TrapezoidProfile.State        setpointState;

    /**
     * Creates a profiled subsystem with bounded setpoints, motion constraints, and a single motor.
     *
     * @param config Configuration values that define the allowable range, motion limits, and initial state.
     * @param motor  Motor controller that reports position/velocity and accepts duty-cycle commands.
     */
    protected AbstractSetAndSeekSubsystem(TConfig config, Motor motor) {
        super(config);
        this.motor  = motor;

        constraints = new TrapezoidProfile.Constraints(config.getMaximumVelocitySupplier().get(),
                config.getMaximumAccelerationSupplier().get());
        profile     = new TrapezoidProfile(constraints);

        double initialPosition = config.getInitialPositionSupplier().get();
        double initialVelocity = config.getInitialVelocitySupplier().get();

        goalState     = new TrapezoidProfile.State(initialPosition, 0.0);
        setpointState = new TrapezoidProfile.State(initialPosition, initialVelocity);
    }

    /**
     * Sets a new goal for the motion profile. Values outside the configured range are clamped.
     *
     * @param targetPosition Desired mechanism position (same units as the configuration).
     */
    public void setTarget(double targetPosition) {
        if (isSubsystemDisabled()) {
            return;
        }

        double clampedTarget = clamp(targetPosition, config.getMinimumSetpointSupplier().get(),
                config.getMaximumSetpointSupplier().get());
        goalState = new TrapezoidProfile.State(clampedTarget, 0.0);
    }

    /**
     * Advances the trapezoidal profile by one cycle and hands the setpoint to the subclass for actuation.
     */
    public void seekTarget() {
        if (isSubsystemDisabled()) {
            return;
        }

        motor.updateInputs(motorInputs);
        org.littletonrobotics.junction.Logger.processInputs(className + "/motor", motorInputs);

        refreshConstraints();

        setpointState = profile.calculate(kDt, setpointState, goalState);
        applySetpoint(setpointState);
        logSetpoint(setpointState, goalState);
    }

    /**
     * Resets the internal profile state to a measured pose. Call this before starting a new profile to avoid jumps when encoders drift.
     *
     * @param position Current measured position in mechanism units.
     * @param velocity Current measured velocity in mechanism units per second.
     */
    public void synchronizeToMeasurement(double position, double velocity) {
        setpointState = new TrapezoidProfile.State(position, velocity);
        goalState     = new TrapezoidProfile.State(position, 0.0);
    }

    /**
     * States whether the mechanism is within the configured tolerance of the goal position.
     *
     * @return True when the measured position is at the goal.
     */
    public boolean atTarget() {
        if (isSubsystemDisabled()) {
            return true;
        }

        double error = Math.abs(goalState.position - getMeasuredPosition());
        return error <= config.getPositionToleranceSupplier().get();
    }

    /**
     * Hook for subclasses to respond when a seek command is interrupted. Default implementation stops the motor.
     */
    public void handleSeekInterrupted() {
        motor.stop();
    }

    /**
     * Applies the calculated setpoint to hardware. Override to customize control behavior.
     *
     * @param setpoint The next state from the trapezoidal profile.
     */
    protected void applySetpoint(TrapezoidProfile.State setpoint) {
        double maximumVelocity = Math.max(Math.abs(config.getMaximumVelocitySupplier().get()), 1e-6);
        double velocityTerm    = clamp(setpoint.velocity / maximumVelocity, -1.0, 1.0);

        double error           = setpoint.position - getMeasuredPosition();
        double range           = config.getMaximumSetpointSupplier().get() - config.getMinimumSetpointSupplier().get();
        double positionTerm    = range != 0.0 ? clamp(error / range, -1.0, 1.0) : 0.0;

        double outputPercent   = clamp(positionTerm + velocityTerm, -1.0, 1.0);
        motor.setSpeed(outputPercent);

        log.recordOutput("commandedDutyCycle", outputPercent);
    }

    /**
     * Provides the measured mechanism position. Override to read from an encoder or other sensor.
     *
     * @return The current measured position in mechanism units. Defaults to the profiled setpoint for simulation-only usage.
     */
    protected double getMeasuredPosition() {
        return motor.getEncoderPosition();
    }

    /**
     * Provides the measured mechanism velocity. Override to read from an encoder or other sensor.
     *
     * @return The current measured velocity in mechanism units per second. Defaults to the profiled setpoint velocity.
     */
    protected double getMeasuredVelocity() {
        return motor.getEncoderVelocity();
    }

    private void logSetpoint(TrapezoidProfile.State setpoint, TrapezoidProfile.State goal) {
        log.recordOutput("goalPosition", goal.position);
        log.recordOutput("goalVelocity", goal.velocity);
        log.recordOutput("setpointPosition", setpoint.position);
        log.recordOutput("setpointVelocity", setpoint.velocity);
        log.recordOutput("measuredPosition", getMeasuredPosition());
        log.recordOutput("measuredVelocity", getMeasuredVelocity());
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private void refreshConstraints() {
        constraints = new TrapezoidProfile.Constraints(config.getMaximumVelocitySupplier().get(),
                config.getMaximumAccelerationSupplier().get());
        profile     = new TrapezoidProfile(constraints);
    }
}