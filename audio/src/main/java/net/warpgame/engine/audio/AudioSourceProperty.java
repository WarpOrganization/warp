package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.AttachBufferCommand;
import net.warpgame.engine.audio.command.PlayCommand;
import net.warpgame.engine.audio.command.SetLoopingCommand;
import net.warpgame.engine.core.property.Property;

public class AudioSourceProperty extends Property {

    private int id;

    private boolean isPlaying;
    private boolean loop;
    private AudioClip audioClip;

    private AudioContext audioContext;

    public AudioSourceProperty() {
        this.isPlaying = false;
    }

    public void play() {
        audioContext.putCommand(new PlayCommand(this, audioContext.getPlayingSources()));
        setPlaying(true);
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
        try {
            id = audioContext.getSource();
            audioContext.getAllSources().add(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

    public void setAudioClip(AudioClip audioClip) {
        this.audioClip = audioClip;
        audioClip.init(audioContext);
        audioContext.putCommand(new AttachBufferCommand(this));
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
        audioContext.putCommand(new SetLoopingCommand(this, loop));
    }
}
