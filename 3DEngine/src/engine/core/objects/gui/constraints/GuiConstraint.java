package engine.core.objects.gui.constraints;

import engine.core.objects.gui.components.GuiComponent;

public abstract class GuiConstraint {

    private GuiComponent parent;
    private ConstraintLevel level;
    private float valueShift = 0;

    public GuiConstraint() {}

    public GuiConstraint(GuiComponent parent) {
        this.parent = parent;
    }

    public void setParent(GuiComponent parent) {
        this.parent = parent;
    }

    public GuiComponent getParent() {
        return parent;
    }

    public ConstraintLevel getLevel() {
        return level;
    }

    public void setLevel(ConstraintLevel level) {
        this.level = level;
    }

    public float getValueShift() {
        return valueShift;
    }

    public void setValueShift(float valueShift) {
        this.valueShift = valueShift;
    }

    public abstract void transform();
}
