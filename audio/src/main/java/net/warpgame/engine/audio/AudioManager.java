package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.CreateSourceCommand;
import net.warpgame.engine.audio.command.PauseCommand;
import net.warpgame.engine.audio.command.PlayCommand;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.io.FilenameUtils;
import org.joml.Vector3f;

import java.io.IOException;

/**
 * @author Hubertus
 *         Created 22.12.16
 */

@Service
public class AudioManager {

    private AudioContext audioContext;
    private AudioThread audioThread;

    public AudioManager(AudioContext audioContext, AudioThread audioThread) {
        this.audioContext = audioContext;
        this.audioThread = audioThread;
    }

    void prepereAudioClip(String clip) {
        String name = FilenameUtils.getBaseName(clip);
        SoundBank soundBank = audioContext.getSoundBank();
        if(!soundBank.containsSound(name)){
            audioThread.scheduleOnce(() -> {
                try {
                    soundBank.loadFile(clip);
                } catch (IOException e) {
                    throw new RuntimeException(String.format("Can't find filed named %s", clip));
                }
            });

        }
    }

}
