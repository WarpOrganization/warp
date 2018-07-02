package net.warpgame.engine.audio.command.source;

import net.warpgame.engine.audio.AudioSourceProperty;
import net.warpgame.engine.audio.command.Command;

import static org.lwjgl.openal.AL10.*;

public class StopSourceCommand implements Command {

    private AudioSourceProperty sourceProperty;

    public StopSourceCommand(AudioSourceProperty sourceProperty) {
        this.sourceProperty = sourceProperty;
    }

    @Override
    public void execute() {
        alSourceStop(sourceProperty.getId());
    }
}
