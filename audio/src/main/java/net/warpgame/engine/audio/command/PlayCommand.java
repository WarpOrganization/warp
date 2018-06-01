package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSourceProperty;

import static org.lwjgl.openal.AL10.*;

/**
 * @author Hubertus
 *         Created 22.12.16
 */
public class PlayCommand implements Command {

    private final AudioSourceProperty source;
    private final String soundName;

    public PlayCommand(AudioSourceProperty source, String soundName) {
        this.source = source;
        this.soundName = soundName;
    }

    @Override
    public void execute(AudioContext context) {
        alSourcei(source.getId(), AL_SOURCE_RELATIVE, source.isRelative() ? AL_TRUE : AL_FALSE);
        alSourcei(source.getId(), AL_BUFFER, context.getSoundBank().getSound(soundName));
        alSourcei(source.getId(), AL_REFERENCE_DISTANCE, 5);
        alSourcePlay(source.getId());
    }
}
