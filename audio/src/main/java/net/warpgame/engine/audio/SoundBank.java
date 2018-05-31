package net.warpgame.engine.audio;

import net.warpgame.engine.audio.decoder.SoundDecoderManager;
import net.warpgame.engine.audio.decoder.WavFileDecoder;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;
import net.warpgame.engine.core.context.EngineContext;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * @author Hubertus
 *         Created 23.12.16
 */
public class SoundBank {
    Map<String, Integer> sounds = new TreeMap<>();

    private AudioContext context;

    public SoundBank(AudioContext context){
        this.context = context;
    }

    public int getSound(String soundName) {
        return sounds.get(soundName);
    }

    public boolean containsSound(String soundName){
        return sounds.containsKey(soundName);
    }

    void initAudioClip(AudioClip audioClip){

    }

    public void loadFile(String file) throws IOException {
        int buffer = AL10.alGenBuffers();
        SoundDecoderManager.decode(file).fillBufferWithData(buffer);
        sounds.put(FilenameUtils.getBaseName(file), buffer);
    }
}
