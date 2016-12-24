package pl.warp.engine.audio.command;

import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.audio.AudioSource;

import static org.lwjgl.openal.AL10.*;

/**
 * @author Hubertus
 *         Created 22.12.16
 */
public class PlayCommand implements Command {

    private final AudioSource source;
    private final String soundName;

    public PlayCommand(AudioSource source, String soundName) {

        this.source = source;
        this.soundName = soundName;
    }

    @Override
    public void execute(AudioContext context) {
        alSourcei(source.getId(), AL_SOURCE_RELATIVE, AL_FALSE);
        alSourcei(source.getId(), AL_BUFFER, context.getSoundBank().getSound(soundName));
        alSourcei(source.getId(),AL_REFERENCE_DISTANCE , 5);
        alSourcePlay(source.getId());
        context.getPlaying().add(source);
    }
}
