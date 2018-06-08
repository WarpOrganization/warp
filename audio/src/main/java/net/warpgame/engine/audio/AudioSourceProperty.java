package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.CreateSourceCommand;
import net.warpgame.engine.audio.command.PlayCommand;
import net.warpgame.engine.core.property.Property;
import org.apache.commons.io.FilenameUtils;

public class AudioSourceProperty extends Property {

    private int id;
    private boolean isRelative;
    private boolean isPlaying;


    private String soundFilePath;
    private String soundName;

    private AudioContext audioContext;

    public AudioSourceProperty(String soundFilePath) {
        this.soundFilePath = soundFilePath;
        this.soundName = FilenameUtils.getBaseName(soundFilePath);
        this.isRelative = false;
        this.isPlaying = false;
    }

    public void play() {
        audioContext.putCommand(new PlayCommand(this, soundName));
        audioContext.getPlaying().add(this);
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
        audioContext.prepareAudioClip(soundFilePath);
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
}
