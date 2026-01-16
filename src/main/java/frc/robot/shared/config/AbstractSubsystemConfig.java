package frc.robot.shared.config;

import java.util.HashMap;
import java.util.Map;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;
import org.littletonrobotics.junction.networktables.LoggedNetworkString;

import edu.wpi.first.wpilibj.DriverStation;

public abstract class AbstractSubsystemConfig {
    private static final String                    SMART_DASHBOARD_PREFIX = "SmartDashboard/";

    public boolean                                 enabled                = true;

    public boolean                                 verbose                = true;

    private final Map<String, LoggedNetworkNumber> tunableNumbers         = new HashMap<>();

    private final Map<String, LoggedNetworkString> tunableStrings         = new HashMap<>();

    /**
     * Reads a tunable number backed by AdvantageKit's logged network inputs so tweaks are captured in logs and respected during replay, but still
     * falls back to the default when attached to FMS to avoid match-time latency.
     */
    protected double readTunableNumber(String key, double defaultValue) {
        String              dashboardKey    = dashboardKey(key);
        LoggedNetworkNumber dashboardNumber = tunableNumbers.computeIfAbsent(dashboardKey,
                k -> new LoggedNetworkNumber(k, defaultValue));
        return DriverStation.isFMSAttached() ? defaultValue : dashboardNumber.get();
    }

    /**
     * Reads a tunable string backed by AdvantageKit's logged network inputs so tweaks are captured in logs and respected during replay, but still
     * falls back to the default when attached to FMS to avoid match-time latency.
     */
    protected String readTunableString(String key, String defaultValue) {
        String              dashboardKey    = dashboardKey(key);
        LoggedNetworkString dashboardString = tunableStrings.computeIfAbsent(dashboardKey,
                k -> new LoggedNetworkString(k, defaultValue));
        return DriverStation.isFMSAttached() ? defaultValue : dashboardString.get();
    }

    private String dashboardKey(String key) {
        if (key.startsWith(SMART_DASHBOARD_PREFIX)) {
            return key;
        }
        return SMART_DASHBOARD_PREFIX + key;
    }
}
