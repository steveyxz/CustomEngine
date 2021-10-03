package engine.core.sound;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class MusicTrack {

    private final String path;
    private final Music music;

    public MusicTrack(String track, String ref) throws SlickException {
        this.path = track;
        this.music = new Music(MusicTrack.class.getClassLoader().getResourceAsStream(track), ref);
    }

    public void play() {
        music.play();
    }

    public void play(int pitch, int volume) {
        music.play(pitch, volume);
    }

    public void stop() {
        if (music.playing()) {
            music.stop();
        }
    }

    public void loop() {
        music.loop();
    }

    public void loop(int pitch, int volume) {
        music.loop(pitch, volume);
    }

    public void fade(int duration, float endVolume, boolean endAfter) {
        if (music.playing()) {
            music.fade(duration, endVolume, endAfter);
        }
    }

    public void setPosition(int position) {
        if (music.playing()) {
            music.setPosition(position);
        }
    }

    public float getPosition() {
        return music.getPosition();
    }

    public float getVolume() {
        return music.getVolume();
    }

    public void setVolume(float volume) {
        music.setVolume(volume);
    }

    public boolean playing() {
        return music.playing();
    }

    public void pause() {
        music.pause();
    }

    public void resume() {
        music.resume();
    }

    public String getPath() {
        return path;
    }
}
