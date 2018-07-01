package net.warpgame.engine.audio.command.source;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSourceProperty;
import net.warpgame.engine.audio.command.Command;

import java.util.List;

import static org.lwjgl.openal.AL10.*;

public class PlaySourceCommand implements Command {
    private AudioSourceProperty source;
    private List<AudioSourceProperty> playingSources;

    public PlaySourceCommand(AudioSourceProperty source, List<AudioSourceProperty> playingSources) {
        this.source = source;
        this.playingSources = playingSources;
    }

    @Override
    public void execute() {
        alSourcePlay(source.getId());
        playingSources.add(source);
    }
}
