package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioClip;
import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.decoder.SoundData;
import org.lwjgl.openal.AL10;

import java.util.Map;

import static org.lwjgl.openal.AL10.alBufferData;

/**
 * Created by MarconZet on 31.05.2018.
 */
public class CreateAudioClipCommand implements Command {

    private AudioClip audioClip;
    private SoundData soundData;
    private Map<String, Integer> sound;

    public CreateAudioClipCommand(AudioClip audioClip, SoundData soundData, Map<String, Integer> sound) {
        this.audioClip = audioClip;
        this.soundData = soundData;
        this.sound = sound;
    }

    @Override
    public void execute(AudioContext context) {
        if(!sound.containsKey(audioClip.getFile())){
            int buffer = AL10.alGenBuffers();
            sound.put(audioClip.getFile(), buffer);
            audioClip.setId(buffer);
            alBufferData(buffer, soundData.getOpenALFormat(), soundData.getData(), soundData.getFrequency());
        } else {
            audioClip.setId(sound.get(audioClip.getFile()));
        }


    }
}
