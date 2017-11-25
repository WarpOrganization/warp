package pl.warp.engine.audio.decoder;

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

    private static final Logger LOGGER = Logger.getLogger(WavFileDecoder.class);

    public static SoundData decode(File soundFile) throws IOException {
        AudioInputStream stream;

        try {
            stream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        AudioFormat format = stream.getFormat();

        byte[] b = IOUtils.toByteArray(stream);
        ByteBuffer data = BufferUtils.createByteBuffer(b.length).put(b);
        data.flip();

        return new SoundData(data, (int)format.getSampleRate(), format.getChannels(), 0, format.getSampleSizeInBits());
    }
}
