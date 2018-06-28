package net.warpgame.engine.audio.command.source;

import net.warpgame.engine.audio.AudioSourceProperty;
import net.warpgame.engine.audio.command.Command;

import static org.lwjgl.openal.AL10.alSourcef;

public class SetSourceFloatCommand implements Command {

    private AudioSourceProperty id;
    private int param;
    private float value;

    public SetSourceFloatCommand(AudioSourceProperty id, int param, float value) {
        this.id = id;
        this.param = param;
        this.value = value;
    }

    @Override
    public void execute() {
        alSourcef(id.getId(), param, value);
    }
}
