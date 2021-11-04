package engine.core.objects.gui.constraints.types;

import engine.core.objects.gui.constraints.ConstraintLevel;
import engine.core.objects.gui.constraints.GuiConstraint;
import org.lwjgl.util.vector.Vector2f;

public class RelativeConstraint extends GuiConstraint {

    private final boolean shiftCentre;
    private float value;

    public RelativeConstraint(float value) {
        this(value, true);
    }

    public RelativeConstraint(float value, boolean shiftCentre) {
        this.value = value;
        this.shiftCentre = shiftCentre;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isShiftCentre() {
        return shiftCentre;
    }

    @Override
    public void transform() {
        Vector2f texturePos = getParent().getTexture().getPos();
        Vector2f textureScale = getParent().getTexture().getScale();
        if (getLevel() == ConstraintLevel.X) {
            float x = (value * 2) - 1;
            texturePos.setX(x);
            setValueShift(x);
            if (shiftCentre) {
                float add = textureScale.getX();
                texturePos.setX(getValueShift() + add);
                setValueShift(getValueShift() + add);
            }
        }
        if (getLevel() == ConstraintLevel.Y) {
            float y = (2 - value * 2) - 1;
            texturePos.setY(y);
            setValueShift(y);
            if (shiftCentre) {
                float add = textureScale.getY();
                texturePos.setY(getValueShift() - add);
                setValueShift(getValueShift() - add);
            }
        }
        if (getLevel() == ConstraintLevel.WIDTH) {
            textureScale.setX(value);
            setValueShift(value);

        }
        if (getLevel() == ConstraintLevel.HEIGHT) {
            textureScale.setY(value);
            setValueShift(value);
        }

    }
}
