package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSourceProperty;
import org.lwjgl.openal.AL10;

public class SetLoopingCommand implements Command {

    private AudioSourceProperty source;
    private boolean looping;

    public SetLoopingCommand(AudioSourceProperty source, boolean looping) {
        this.source = source;
        this.looping = looping;
    }

    @Override
    public void execute() {
        AL10.alSourcei(source.getId(), AL10.AL_LOOPING, looping? AL10.AL_TRUE: AL10.AL_FALSE);
    }
}
