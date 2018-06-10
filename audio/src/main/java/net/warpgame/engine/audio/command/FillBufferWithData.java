package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.decoder.SoundData;

import static org.lwjgl.openal.AL10.alBufferData;

public class FillBufferWithData implements Command {

    private int buffer;
    private SoundData soundData;

    public FillBufferWithData(int buffer, SoundData soundData) {
        this.buffer = buffer;
        this.soundData = soundData;
    }

    @Override
    public void execute(AudioContext context) {
        alBufferData(buffer, soundData.getOpenALFormat(), soundData.getData(), soundData.getFrequency());
    }
}
