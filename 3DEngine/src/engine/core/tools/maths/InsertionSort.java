/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.tools.maths;

import engine.core.particles.Particle;

import java.util.List;

public class InsertionSort {

    /**
     * Sorts a list of particles so that the particles with the highest distance
     * from the camera are first, and the particles with the shortest distance
     * are last.
     *
     * @param list - the list of particles needing sorting.
     */
    public static void sortHighToLow(List<Particle> list) {
        for (int i = 1; i < list.size(); i++) {
            Particle item = list.get(i);
            if (item.getDistance() > list.get(i - 1).getDistance()) {
                sortUpHighToLow(list, i);
            }
        }
    }

    private static void sortUpHighToLow(List<Particle> list, int i) {
        Particle item = list.get(i);
        int attemptPos = i - 1;
        while (attemptPos != 0 && list.get(attemptPos - 1).getDistance() < item.getDistance()) {
            attemptPos--;
        }
        list.remove(i);
        list.add(attemptPos, item);
    }

}