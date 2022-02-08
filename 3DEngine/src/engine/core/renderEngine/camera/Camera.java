/*
 * Copyright (c) 2021-2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.renderEngine.camera;

import engine.core.global.Global;
import engine.core.tools.maths.FastTrig;
import engine.core.tools.maths.vectors.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.sin;

public abstract class Camera {

    private final Map<String, Vector3f> positions = new HashMap<>();
    //x is pitch, y is yaw, z is roll
    private final Map<String, Vector3f> rotations = new HashMap<>();
    //Directional vector
    private final Map<String, Vector3f> directions = new HashMap<>();
    private boolean enabled = true;

    public abstract void tick();

    public abstract void frame();

    public Vector3f degreeToDirection(float degs) {
        float rads = 3.1415926535f / 180 * degs;
        float s = (float) (sin(rads)), c = (float) (Math.cos(rads));
        return new Vector3f(-s, 0, -c);
    }

    public Map<String, Vector3f> getPositions() {
        return positions;
    }

    public Vector3f getRotation(String sceneId) {
        Vector3f vec3 = rotations.get(sceneId);
        if (vec3 == null) {
            Vector3f tempvec = new Vector3f(0, 0, 0);
            if (sceneId == null) {
                return tempvec;
            }
            rotations.put(sceneId, tempvec);
            return tempvec;
        }
        return vec3;
    }

    public Vector3f getPosition(String sceneId) {
        Vector3f vec3 = positions.get(sceneId);
        if (vec3 == null) {
            Vector3f tempvec = new Vector3f(0, 0, 0);
            if (sceneId == null) {
                return tempvec;
            }
            positions.put(sceneId, tempvec);
            return tempvec;
        }
        return vec3;
    }

    public Vector3f getVectorDirection(String sceneId) {
        Vector3f vec3 = directions.get(sceneId);
        if (vec3 == null) {
            Vector3f tempvec = new Vector3f(0, 0, 0);
            if (sceneId == null) {
                return tempvec;
            }
            directions.put(sceneId, tempvec);
            return tempvec;
        }
        return vec3;
    }

    public void updateCameraVectors() {
        String currentScene = Global.currentScene.getSceneId();
        if (!directions.containsKey(currentScene)) {
            directions.put(currentScene, new Vector3f(0, 0, 0));
        }
        if (!rotations.containsKey(currentScene)) {
            rotations.put(currentScene, new Vector3f(0, 0, 0));
        }
        Vector3f newVec = new Vector3f();
        Vector3f rotation = rotations.get(currentScene);
        newVec.x = FastTrig.cos(Math.toRadians(rotation.y)) * FastTrig.cos(Math.toRadians(rotation.x));
        newVec.y = FastTrig.sin(Math.toRadians(rotation.x));
        newVec.z = FastTrig.sin(Math.toRadians(rotation.y)) * FastTrig.cos(Math.toRadians(rotation.x));
        newVec.normalise(directions.get(currentScene));
    }

    public boolean enabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggleEnabled() {
        enabled = !enabled;
    }

    public float getPitch(String sceneId) {
        return getRotation(sceneId).x;
    }

    public void setPitch(float pitch, String sceneId) {
        getRotation(sceneId).x = pitch;
    }

    public float getYaw(String sceneId) {
        return getRotation(sceneId).y;
    }

    public void setYaw(float yaw, String sceneId) {
        getRotation(sceneId).y = yaw;
    }

    public float getRoll(String sceneId) {
        return getRotation(sceneId).z;
    }

    public void setRoll(float roll, String sceneId) {
        getRotation(sceneId).z = roll;
    }
}
