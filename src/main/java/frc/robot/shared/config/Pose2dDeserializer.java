package frc.robot.shared.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/**
 * Jackson deserializer for a Pose2d stored as JSON with fields {@code x}, {@code y} (meters), and {@code rotation} in degrees.
 */
public class Pose2dDeserializer extends JsonDeserializer<Pose2d> {
    /**
     * Parses a Pose2d from JSON with fields {@code x}, {@code y} (meters), and {@code rotation} in degrees.
     *
     * @param parser  Jackson parser positioned at the pose node
     * @param context deserialization context provided by Jackson
     * @return pose built from the JSON values (rotation converted to radians)
     * @throws IOException if the JSON cannot be read
     */
    @Override
    public Pose2d deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        ObjectMapper mapper   = (ObjectMapper) parser.getCodec();
        JsonNode     node     = mapper.readTree(parser);

        double       x        = node.get("x").asDouble();
        double       y        = node.get("y").asDouble();
        double       rotation = node.get("rotation").asDouble();

        Pose2d       pose     = new Pose2d(x, y, new Rotation2d(Math.toRadians(rotation)));
        return pose;
    }
}
