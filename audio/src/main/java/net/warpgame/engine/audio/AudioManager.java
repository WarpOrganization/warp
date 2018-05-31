package net.warpgame.engine.audio;

import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.io.FilenameUtils;

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

}
