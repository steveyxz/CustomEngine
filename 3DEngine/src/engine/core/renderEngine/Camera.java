package engine.core.renderEngine;

import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.sin;

public abstract class Camera {

    private final Map<String, Vector3f> positions = new HashMap<>();
    private float pitch; //Up down
    private float yaw; //Left right
    private float roll; //Rotation (in degrees)

    public abstract void tick();

    public Vector3f degreeToDirection(float degs) {
        float rads = 3.1415926535f / 180 * degs;
        float s = (float) (sin(rads)), c = (float) (Math.cos(rads));
        return new Vector3f(-s, 0, -c);
    }


    public abstract void frame();

    public Map<String, Vector3f> getPositions() {
        return positions;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
