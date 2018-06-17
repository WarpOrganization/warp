package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.CreateSourceCommand;
import net.warpgame.engine.audio.command.PlayCommand;
import net.warpgame.engine.core.property.Property;

public class AudioSourceProperty extends Property {

    private int id;
    private boolean isRelative;
    private boolean isPlaying;

    private AudioClip audioClip;

    private AudioContext audioContext;

    public AudioSourceProperty() {
        this.isRelative = false;
        this.isPlaying = false;
    }

    public void play() {
        audioContext.putCommand(new PlayCommand(this, audioClip));
        audioContext.getPlayingSources().add(this);
        isPlaying = true;
    }

    public void stop() {
        throw new UnsupportedOperationException("Stop command is not implemented");
    }

    public void pause() {
        throw new UnsupportedOperationException("Pause command is not implemented");
    }

    @Override
    public void enable() {
        super.enable();
        audioContext = getOwner().getContext().getLoadedContext().findOne(AudioContext.class).get();
        audioContext.putCommand(new CreateSourceCommand(this));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRelative() {
        return isRelative;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

    public void setAudioClip(AudioClip audioClip) {
        this.audioClip = audioClip;
        audioClip.init(audioContext);
    }
}
