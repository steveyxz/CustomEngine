/*
 * Copyright (c) 2022. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.objects.components.animation;

import engine.core.objects.GameObject;
import engine.core.objects.components.Component;
import engine.core.renderEngine.Loader;
import engine.core.renderEngine.models.ModelTexture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FrameAnimationComponent extends Component {

    private final int tickDelay;
    private final List<ModelTexture> textureCycle;
    private Iterator<ModelTexture> iterator;
    private int count = 0;

    protected FrameAnimationComponent(GameObject parent, int tickDelay, ModelTexture... textureCycle) {
        super(parent);
        this.tickDelay = tickDelay;
        this.textureCycle = List.of(textureCycle);
        this.iterator = this.textureCycle.iterator();
    }

    public FrameAnimationComponent(GameObject parent, int tickDelay, String... textureCycle) {
        super(parent);
        this.tickDelay = tickDelay;
        List<ModelTexture> t = new ArrayList<>();
        for (String s : textureCycle) {
            t.add(new ModelTexture(Loader.loadTexture(s)));
        }
        this.textureCycle = t;
        this.iterator = this.textureCycle.iterator();
    }

    @Override
    public String getId() {
        return "frameanimation";
    }

    @Override
    public void tick() {
        if (count > tickDelay) {
            if (!iterator.hasNext()) {
                iterator = textureCycle.iterator();
            }
            ModelTexture t = iterator.next();
            parent().getModel().setTexture(t);
            count = 0;
        }
        count++;
    }

    @Override
    public void frame() {
    }

    public int tickDelay() {
        return tickDelay;
    }

    public List<ModelTexture> textureCycle() {
        return textureCycle;
    }
}
