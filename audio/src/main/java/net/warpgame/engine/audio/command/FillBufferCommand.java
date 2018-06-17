package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioClip;
import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.decoder.SoundData;

import static org.lwjgl.openal.AL10.alBufferData;

public class FillBufferCommand implements Command {

    private AudioClip clip;
    private SoundData soundData;

    public FillBufferCommand(AudioClip clip, SoundData soundData) {
        this.clip = clip;
        this.soundData = soundData;
    }

    @Override
    public void execute() {
        alBufferData(clip.getId(), soundData.getOpenALFormat(), soundData.getData(), soundData.getFrequency());
    }
}
