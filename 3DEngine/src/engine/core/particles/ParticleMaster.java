/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.particles;

import engine.core.renderEngine.Camera;
import engine.core.tools.maths.InsertionSort;
import engine.core.tools.maths.vectors.Matrix4f;

import java.util.*;

public class ParticleMaster {

    private static final Map<ParticleTexture, List<Particle>> particles = new HashMap<>();
    private static ParticleRenderer renderer;

    public static void init(Matrix4f projectionMatrix) {
        renderer = new ParticleRenderer(projectionMatrix);
    }

    public static void reloadProjections(Matrix4f projection) {
        renderer.reloadProjections(projection);
    }

    public static void update() {

        Iterator<Map.Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
        while (mapIterator.hasNext()) {
            List<Particle> list = mapIterator.next().getValue();
            Iterator<Particle> iterator = list.iterator();
            while (iterator.hasNext()) {
                Particle p = iterator.next();
                boolean stillAlive = p.update();
                if (!stillAlive) {
                    iterator.remove();
                    if (list.isEmpty()) {
                        mapIterator.remove();
                    }
                }
            }
            InsertionSort.sortHighToLow(list);
        }
    }


    public static void render(Camera camera) {
        renderer.render(particles, camera);
    }

    public static void cleanUp() {
        renderer.cleanUp();
    }

    public static void addParticle(Particle particle) {
        List<Particle> list = particles.computeIfAbsent(particle.getTexture(), k -> new ArrayList<>());
        list.add(particle);
    }


}
