package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioSourceProperty;

import static org.lwjgl.openal.AL10.*;

public class SetRelativeCommand implements Command {

    private AudioSourceProperty sourceProperty;
    private boolean relative;

    public SetRelativeCommand(AudioSourceProperty sourceProperty, boolean relative) {
        this.sourceProperty = sourceProperty;
        this.relative = relative;
    }

    @Override
    public void execute() {
        alSourcei(sourceProperty.getId(), AL_SOURCE_RELATIVE, relative ? 1 : 0);
    }
}
