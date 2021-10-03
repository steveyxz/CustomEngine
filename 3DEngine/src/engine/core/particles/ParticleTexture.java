package engine.core.particles;

public class ParticleTexture {

    private final int textureID;
    private final int numberOfRows;

    public ParticleTexture(int textureID, int numberOfRows) {
        this.textureID = textureID;
        this.numberOfRows = numberOfRows;
    }

    public int getTextureID() {
        return textureID;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
}
