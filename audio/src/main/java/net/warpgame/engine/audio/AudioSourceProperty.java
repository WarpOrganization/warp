package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.*;
import net.warpgame.engine.audio.command.buffer.AttachBufferCommand;
import net.warpgame.engine.audio.command.source.PauseSourceCommand;
import net.warpgame.engine.audio.command.source.PlaySourceCommand;
import net.warpgame.engine.audio.command.source.SetSourceIntCommand;
import net.warpgame.engine.audio.command.source.StopSourceCommand;
import net.warpgame.engine.core.property.Property;
import static org.lwjgl.openal.AL10.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class AudioSourceProperty extends Property {

    private int id = -1;

    private float pitch;
    private float gain;

    private float maxDistance;
    private float rolloffFactor;
    private float referenceDistance;

    private float minGain;
    private float maxGain;

    private float coneOuterGain;
    private float coneInnerAngle;
    private float coneOuterAngle;

    private boolean looping = false;
    private boolean isPlaying = false;
    private boolean playOnStartup = false;

    private AudioClip audioClip;

    private BlockingQueue<Command> commandQueue = new ArrayBlockingQueue<>(20);

    private AudioContext audioContext;

    public AudioSourceProperty() {

    }

    public AudioSourceProperty play() {
        if(id == -1) throw new RuntimeException("Playing clip before assigning source to component is forbidden");
        commandQueue.add(new PlaySourceCommand(this, audioContext.getPlayingSources()));
        audioContext.getPlayingSources().add(this);
        isPlaying = true;
        return this;
    }

    public AudioSourceProperty stop() {
        commandQueue.add(new StopSourceCommand(this));
        return this;
    }

    public AudioSourceProperty pause() {
        commandQueue.add(new PauseSourceCommand(this));
        return this;
    }



    @Override
    public void enable() {
        super.enable();
        audioContext = getOwner().getContext().getLoadedContext().findOne(AudioContext.class).get();
        if(audioClip != null)
            setAudioClip(audioClip);
        try {
            id = audioContext.getSource();
            audioContext.getAllSources().add(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to get source id for AudioSourceProperty");
        }
        if(playOnStartup) play();
    }

    public AudioSourceProperty setAudioClip(AudioClip audioClip) {
        this.audioClip = audioClip;
        if (audioClip.getId() == -1 && audioContext != null) {
            audioClip.init(audioContext, commandQueue);
            commandQueue.add(new AttachBufferCommand(this));
        }
        return this;
    }

    public AudioSourceProperty setLooping(boolean looping) {
        this.looping = looping;
        commandQueue.add(new SetSourceIntCommand(this, AL_LOOPING, looping?AL_TRUE:AL_FALSE));
        return this;
    }

    public boolean isPlayOnStartup() {
        return playOnStartup;
    }

    //it will play the moment you add property to component
    public AudioSourceProperty setPlayOnStartup(boolean playOnStartup) {
        this.playOnStartup = playOnStartup;
        return this;
    }

    void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public AudioClip getAudioClip() {
        return audioClip;
    }

    public boolean isLooping() {
        return looping;
    }

    public int getId() {
        return id;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    BlockingQueue<Command> getCommandQueue() {
        return commandQueue;
    }
}
