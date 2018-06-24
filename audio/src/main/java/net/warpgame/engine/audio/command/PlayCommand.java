package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSourceProperty;

import java.util.List;

import static org.lwjgl.openal.AL10.*;

public class PlayCommand implements Command {
    private AudioSourceProperty source;
    private List<AudioSourceProperty> playingSources;

    public PlayCommand(AudioSourceProperty source, List<AudioSourceProperty> playingSources) {
        this.source = source;
        this.playingSources = playingSources;
    }

    @Override
    public void execute() {
        alSourcePlay(source.getId());
        playingSources.add(source);
    }
}
