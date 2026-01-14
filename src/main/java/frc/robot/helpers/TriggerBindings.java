package frc.robot.helpers;

import frc.robot.commands.factories.DriveBaseSubsystemCommandFactory;
import frc.robot.config.DriveBaseSubsystemConfig;
import frc.robot.devices.GameController;
import frc.robot.devices.GameController.GameControllerAxes;

/**
 * Maps driver and operator controllers to robot commands so RobotContainer stays lean. Currently wires the drive controller to the default manual
 * drive command.
 */
public class TriggerBindings {

    private static final int                       DEFAULT_DRIVE_CONTROLLER_PORT  = 0;

    private static final int                       DEFAULT_ACTION_CONTROLLER_PORT = 1;

    private final GameController                   driverController;

    private final GameController                   operatorController;

    private final DriveBaseSubsystemCommandFactory driveBaseCommandFactory;

    private final DriveBaseSubsystemConfig         driveBaseConfig;

    /**
     * Creates trigger bindings with default controller ports (0 for drive, 1 for action).
     *
     * @param driveBaseSubsystem      drive base subsystem to command
     * @param driveBaseCommandFactory factory for creating drive base commands
     * @param driveBaseConfig         configuration used to scale joystick inputs into real speeds
     */
    public TriggerBindings(
            DriveBaseSubsystemCommandFactory driveBaseCommandFactory,
            DriveBaseSubsystemConfig driveBaseConfig) {
        this(driveBaseCommandFactory, driveBaseConfig, DEFAULT_DRIVE_CONTROLLER_PORT, DEFAULT_ACTION_CONTROLLER_PORT);
    }

    /**
     * Creates trigger bindings using explicit controller ports.
     *
     * @param driveBaseSubsystem      drive base subsystem to command
     * @param driveBaseCommandFactory factory for creating drive base commands
     * @param driveBaseConfig         configuration used to scale joystick inputs into real speeds
     * @param driveControllerPort     USB port for the driver controller
     * @param actionControllerPort    USB port for the operator controller
     */
    public TriggerBindings(
            DriveBaseSubsystemCommandFactory driveBaseCommandFactory,
            DriveBaseSubsystemConfig driveBaseConfig,
            int driveControllerPort,
            int actionControllerPort) {
        this.driveBaseCommandFactory = driveBaseCommandFactory;
        this.driveBaseConfig         = driveBaseConfig;
        this.driverController        = new GameController(driveControllerPort);
        this.operatorController      = new GameController(actionControllerPort);

        configureDriveControllerBindings();
        configureActionControllerBindings();
    }

    private void configureDriveControllerBindings() {
        driveBaseCommandFactory.setDefaultManualDriveCommand(
                () -> driverController.getRawAxis(GameControllerAxes.LeftStickY.getValue()),
                () -> driverController.getRawAxis(GameControllerAxes.LeftStickX.getValue()),
                () -> driverController.getRawAxis(GameControllerAxes.RightStickX.getValue()),
                driveBaseConfig);
    }

    private void configureActionControllerBindings() {
        // Operator bindings will be added as mechanisms and commands come online.
    }

}
