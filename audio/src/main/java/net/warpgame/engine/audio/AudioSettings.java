package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.ManualCommand;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;

@Service()
@Profile("sound")
public class AudioSettings {

    private float masterVolume = 1;
    private float dopplerFactor = 1;//dopplerFactor and speedOfSound don't do anything
    private float speedOfSound = 330;

    private AudioContext audioContext;

    public AudioSettings(AudioContext audioContext) {
        this.audioContext = audioContext;
        this.setDopplerFactor(dopplerFactor);
        this.setMasterVolume(masterVolume);
        this.setSpeedOfSound(speedOfSound);
    }

    public float getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(float masterVolume) {
        if(masterVolume < 0)
            throw new RuntimeException("master volume must be non-negative");
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
        if(dopplerFactor <= 0)
            throw new RuntimeException("doppler factor must be positive");
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
        if(speedOfSound <= 0)
            throw new RuntimeException("speed of sound must be positive");
        audioContext.getCommands().add(
                new ManualCommand(
                        () -> AL11.alSpeedOfSound(speedOfSound)
                ));
        this.speedOfSound = speedOfSound;
    }
}
