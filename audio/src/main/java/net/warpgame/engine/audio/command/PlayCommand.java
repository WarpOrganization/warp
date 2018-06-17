package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioClip;
import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSourceProperty;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL10.alSourcePlay;

public class PlayCommand implements Command {
    AudioClip clip;
    AudioSourceProperty source;

    public PlayCommand(AudioSourceProperty source, AudioClip clip) {
        this.clip = clip;
        this.source = source;
    }

    @Override
    public void execute(AudioContext context) {
        alSourcei(source.getId(), AL_SOURCE_RELATIVE, source.isRelative() ? AL_TRUE : AL_FALSE);
        alSourcei(source.getId(), AL_BUFFER, clip.getId());
        alSourcei(source.getId(), AL_REFERENCE_DISTANCE, 5);
        alSourcePlay(source.getId());
    }
}
