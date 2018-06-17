package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSourceProperty;

import static org.lwjgl.openal.AL10.*;

public class PlayCommand implements Command {
    private AudioSourceProperty source;

    public PlayCommand(AudioSourceProperty source) {
        this.source = source;
    }

    @Override
    public void execute(AudioContext context) {
        alSourcePlay(source.getId());
    }
}
