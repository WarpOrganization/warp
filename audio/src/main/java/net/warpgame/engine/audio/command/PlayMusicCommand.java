package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.MusicSource;

import static org.lwjgl.openal.AL10.*;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class PlayMusicCommand implements Command {

    private MusicSource source;

    public PlayMusicCommand(MusicSource source) {
        this.source = source;
    }

    @Override
    public void execute(AudioContext context) {
        alSourcei(source.getId(), AL_REFERENCE_DISTANCE, 5);
        alSourcei(source.getId(), AL_SOURCE_RELATIVE, source.isRelative() ? AL_TRUE : AL_FALSE);
        source.loadNew(source.getPlayList().getNextFile());
        alSourcePlay(source.getId());
        context.getMusicPlaying().add(source);
    }
}
