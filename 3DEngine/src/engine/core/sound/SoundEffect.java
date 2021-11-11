/*
 * Copyright (c) 2021. This file is allowed to be used under the Attribution License, or CC BY. This means that this file can be used in any way, personally or commercially without the owner's consent as long as you provide credit to Steven.
 */

package engine.core.sound;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundEffect {

    private final String path;
    private final Sound sound;

    public SoundEffect(String track, String ref) throws SlickException {
        this.path = track;
        this.sound = new Sound(MusicTrack.class.getClassLoader().getResourceAsStream(track), ref);
    }

    public void play() {
        sound.play();
    }

}
