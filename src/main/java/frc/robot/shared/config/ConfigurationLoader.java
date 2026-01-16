package frc.robot.shared.config;

import java.io.File;

import javax.naming.ConfigurationException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ConfigurationLoader {

    /**
     * Loads a configuration file from the deploy directory and maps it to a type
     *
     * @param <TConfig> The Java type to map the configuration file
     * @param fileName  The name of the JSON file to load
     * @param classOfT  The class type to map the configuration file to
     * @return loaded configuration instance
     * @throws ConfigurationException when the file cannot be loaded or parsed
     */
    public static <TConfig> TConfig load(String fileName, Class<TConfig> classOfT) throws ConfigurationException {
        try {
            // Generic and Mapping Setup
            JavaType     type   = TypeFactory.defaultInstance().constructType(classOfT);
            ObjectMapper om     = new ObjectMapper();

            // Custom Deserializer Setup
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Pose2d.class, new Pose2dDeserializer());
            om.registerModule(module);

            // File setup
            File    deployDirectory = Filesystem.getDeployDirectory();
            File    configFile      = new File(deployDirectory, fileName);

            // Map the config to the class type and return
            TConfig config          = om.readValue(configFile, type);

            // Use reflection to iterate over each public field of TConfig
            String  simpleName      = classOfT.getSimpleName();
            iterateFields(classOfT, config, simpleName);

            return config;
        } catch (Exception e) {
            e.printStackTrace();

            throw new ConfigurationException("Failed to load configuration file: " + fileName);
        }
    }

    private static <TConfig> void iterateFields(Class<?> classOfT, TConfig config, String parentFieldName) throws IllegalAccessException {
        for (var field : classOfT.getFields()) {
            field.setAccessible(true);
            String fieldName  = parentFieldName + "/" + field.getName();
            Object fieldValue = field.get(config);

            if (fieldValue instanceof Double) {
                SmartDashboard.putNumber(fieldName, (Double) fieldValue);
            } else if (fieldValue instanceof Boolean) {
                SmartDashboard.putBoolean(fieldName, (Boolean) fieldValue);
            } else if (fieldValue instanceof Integer) {
                SmartDashboard.putNumber(fieldName, (Integer) fieldValue);
            } else if (fieldValue instanceof String) {
                SmartDashboard.putString(fieldName, (String) fieldValue);
            } else if (fieldValue != null && fieldValue.getClass().getName().startsWith("frc.robot.subsystems")) {
                iterateFields(fieldValue.getClass(), fieldValue, fieldName);
            }
        }
    }
}
