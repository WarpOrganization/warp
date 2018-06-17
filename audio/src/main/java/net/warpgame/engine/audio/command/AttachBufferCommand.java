package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSourceProperty;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.alSourcei;

public class AttachBufferCommand implements Command {

    private AudioSourceProperty source;

    public AttachBufferCommand(AudioSourceProperty source) {
        this.source = source;
    }

    @Override
    public void execute() {
        alSourcei(source.getId(), AL_BUFFER, source.getAudioClip().getId());
    }
}
