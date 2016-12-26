package pl.warp.engine.audio.command;

import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.audio.MusicSource;
import pl.warp.engine.audio.playList.PlayList;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL10.AL_FALSE;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class PlayMusicCommand implements Command{

    private MusicSource source;
    private PlayList playList;

    public PlayMusicCommand(MusicSource source, PlayList playList){
        this.source = source;
        this.playList = playList;
    }

    @Override
    public void execute(AudioContext context) {
        alSourcei(source.getId(), AL_REFERENCE_DISTANCE, 5);
        alSourcei(source.getId(), AL_SOURCE_RELATIVE, source.isRelative() ? AL_TRUE : AL_FALSE);

    }
}
