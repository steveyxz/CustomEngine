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
