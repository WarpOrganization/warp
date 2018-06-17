package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioClip;
import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.decoder.SoundData;

import static org.lwjgl.openal.AL10.alBufferData;

public class FillBufferWithData implements Command {

    private AudioClip clip;
    private SoundData soundData;

    public FillBufferWithData(AudioClip clip, SoundData soundData) {
        this.clip = clip;
        this.soundData = soundData;
    }

    @Override
    public void execute(AudioContext context) {
        alBufferData(clip.getId(), soundData.getOpenALFormat(), soundData.getData(), soundData.getFrequency());
    }
}
