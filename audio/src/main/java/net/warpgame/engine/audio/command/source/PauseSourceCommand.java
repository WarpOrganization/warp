package net.warpgame.engine.audio.command.source;

import net.warpgame.engine.audio.AudioSourceProperty;
import net.warpgame.engine.audio.command.Command;

import static org.lwjgl.openal.AL10.*;

public class PauseSourceCommand implements Command {

    private AudioSourceProperty sourceProperty;

    public PauseSourceCommand(AudioSourceProperty sourceProperty) {
        this.sourceProperty = sourceProperty;
    }

    @Override
    public void execute() {
        alSourcePause(sourceProperty.getId());
    }
}
