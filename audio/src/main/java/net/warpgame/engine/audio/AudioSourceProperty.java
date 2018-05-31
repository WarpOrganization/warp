package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.CreateSourceCommand;
import net.warpgame.engine.audio.command.PlayCommand;
import net.warpgame.engine.core.property.Property;
import org.apache.commons.io.FilenameUtils;

public class AudioSourceProperty extends Property {

    public static final String NAME = "source";

    private int id;
    private boolean isRelative;


    private String soundFilePath;
    private String soundName;

    private AudioContext audioContext;

    public AudioSourceProperty(String soundFilePath) {
        super(NAME);
        this.soundFilePath = soundFilePath;
        this.soundName = FilenameUtils.getBaseName(soundFilePath);
        this.isRelative = false;
    }

    public void play() {
        audioContext.putCommand(new PlayCommand(this, soundName));
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
        audioContext.putCommand(new CreateSourceCommand(this));
        audioContext = getOwner().getContext().getLoadedContext().findOne(AudioContext.class).get();
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
}
