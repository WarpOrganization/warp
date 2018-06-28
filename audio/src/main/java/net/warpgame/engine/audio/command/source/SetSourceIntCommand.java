package net.warpgame.engine.audio.command.source;

import net.warpgame.engine.audio.AudioSourceProperty;
import net.warpgame.engine.audio.command.Command;

import static org.lwjgl.openal.AL10.alSourcei;

public class SetSourceIntCommand implements Command {
    private AudioSourceProperty source;
    private int param;
    private int value;

    public SetSourceIntCommand(AudioSourceProperty source, int param, int value) {
        this.source = source;
        this.param = param;
        this.value = value;
    }

    @Override
    public void execute() {
        alSourcei(source.getId(), param, value);
    }
}
