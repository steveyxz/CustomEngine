package engine.core.objects;

import engine.core.renderEngine.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import static engine.core.global.Global.currentScene;

public abstract class GameObject {

    //Texturing
    public final TexturedModel model;
    //Positioning (per tick)
    public Vector3f position;
    public Vector3f rotation;
    public Vector3f velocity;
    public float scale;
    //Lighting
    private float shineDamper = 1;
    private float reflectivity = 0;
    //Scene Info
    public String sceneId = "";

    public GameObject(Vector3f position, Vector3f rotation, float scale, TexturedModel model) {
        this.position = position;
        this.model = model;
        this.scale = scale;
        this.rotation = rotation;
        this.velocity = new Vector3f(0, 0, 0);
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public TexturedModel getModel() {
        return model;
    }

    public void tick() {
        position.translate(velocity.x, velocity.y, velocity.z);
    }

    public void frame() {
    }

}
