package net.warpgame.engine.audio.decoder;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.lwjgl.BufferUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by Marcin on 30.04.2017.
 */
public class WavFileDecoder{

    protected WavFileDecoder() {
        throw new UnsupportedOperationException();
    }

    static SoundData decode(String pathToFile) throws IOException {
        File soundFile = new File(pathToFile);
        AudioInputStream stream;

        try {
            stream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
        AudioFormat format = stream.getFormat();

        byte[] b = IOUtils.toByteArray(stream);
        ByteBuffer data = BufferUtils.createByteBuffer(b.length).put(b);
        data.flip();

        return new SoundData(data.asShortBuffer(), (int)format.getSampleRate(), format.getChannels(), format.getSampleSizeInBits());
    }
}
