package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.*;
import net.warpgame.engine.audio.command.buffer.AttachBufferCommand;
import net.warpgame.engine.audio.command.source.*;
import net.warpgame.engine.core.property.Property;
import static org.lwjgl.openal.AL10.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class AudioSourceProperty extends Property {

    private int id = -1;

    private float pitch = 1;
    private float volume = 1;

    private float minDistance = 1;
    private float maxDistance = 500;//Achtung! maxDistance > minDistance
    private float rollOffFactor = 1;
    private int volumeRollOff = AL_INVERSE_DISTANCE_CLAMPED;

    private float coneOuterGain;
    private float coneInnerAngle;
    private float coneOuterAngle;

    private boolean looping = false;
    private boolean playOnStartup = true;

    private AudioClip audioClip;

    private boolean isPlaying = false;

    private BlockingQueue<Command> commandQueue = new ArrayBlockingQueue<>(20);
    private AudioContext audioContext;

    public AudioSourceProperty() {

    }

    public AudioSourceProperty play() {
        if(id == -1) throw new RuntimeException("Playing clip before adding property to component is forbidden");
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
    public void init() {
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
        this.setPitch(pitch)
                .setVolume(volume)
                .setMaxDistance(maxDistance)
                .setMinDistance(minDistance)
                .setRollOffFactor(rollOffFactor)
                .setLooping(looping);
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

    public float getPitch() {
        return pitch;
    }

    public AudioSourceProperty setPitch(float pitch) {
        this.pitch = pitch;
        commandQueue.add(new SetSourceFloatCommand(this, AL_PITCH, pitch));
        return this;
    }

    public float getVolume() {
        return volume;
    }

    public AudioSourceProperty setVolume(float volume) {
        this.volume = volume;
        commandQueue.add(new SetSourceFloatCommand(this, AL_GAIN, volume));
        return this;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public AudioSourceProperty setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
        commandQueue.add(new SetSourceFloatCommand(this, AL_MAX_DISTANCE, maxDistance));
        return this;
    }

    public float getMinDistance() {
        return minDistance;
    }

    public AudioSourceProperty setMinDistance(float minDistance) {
        this.minDistance = minDistance;
        commandQueue.add(new SetSourceFloatCommand(this, AL_REFERENCE_DISTANCE, minDistance));
        return this;
    }

    public float getRollOffFactor() {
        return rollOffFactor;
    }

    public AudioSourceProperty setRollOffFactor(float rollOffFactor) {
        this.rollOffFactor = rollOffFactor;
        commandQueue.add(new SetSourceFloatCommand(this, AL_ROLLOFF_FACTOR, rollOffFactor));
        return this;
    }

    public int getVolumeRollOff() {
        return volumeRollOff;
    }

    public AudioSourceProperty setVolumeRollOff(int volumeRollOff) {
        this.volumeRollOff = volumeRollOff;
        commandQueue.add(new SetSourceIntCommand(this, AL_DISTANCE_MODEL, volumeRollOff));
        return this;
    }
}
