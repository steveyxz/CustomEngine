package engine.core.renderEngine.models;

public class ModelTexture {

    private int textureID;
    private boolean hasTransparency = false;
    private boolean useFakeLighting = false;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public ModelTexture(int textureID, boolean hasTransparency) {
        this.textureID = textureID;
        this.hasTransparency = hasTransparency;
    }

    public ModelTexture(int textureID, boolean hasTransparency, boolean useFakelighting) {
        this.textureID = textureID;
        this.hasTransparency = hasTransparency;
        this.useFakeLighting = useFakelighting;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public boolean isHasTransparency() {
        return hasTransparency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }
}
