package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.ManualCommand;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

@Service()
@Profile("client")
public class AudioSettings {

    private float masterVolume = 1;
    private float dopplerFactor = 1;
    private float speedOfSound = 330;

    private AudioContext audioContext;

    public AudioSettings(AudioContext audioContext) {
        this.audioContext = audioContext;
    }

    public float getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(float masterVolume) {
        audioContext.getCommands().add(
                new ManualCommand(
                        () -> AL10.alListenerf(AL10.AL_GAIN, masterVolume)
                ));
        this.masterVolume = masterVolume;
    }

    public float getDopplerFactor() {
        return dopplerFactor;
    }

    public void setDopplerFactor(float dopplerFactor) {
        audioContext.getCommands().add(
                new ManualCommand(
                        () -> AL10.alDopplerFactor(dopplerFactor)
                ));
        this.dopplerFactor = dopplerFactor;
    }

    public float getSpeedOfSound() {
        return speedOfSound;
    }

    public void setSpeedOfSound(float speedOfSound) {
        audioContext.getCommands().add(
                new ManualCommand(
                        () -> AL11.alSpeedOfSound(speedOfSound)
                ));
        this.speedOfSound = speedOfSound;
    }
}
