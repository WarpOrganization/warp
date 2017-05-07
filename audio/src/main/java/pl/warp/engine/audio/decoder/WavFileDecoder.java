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
public class WavFileDecoder implements SoundFileDecoder {

    private static final Logger LOGGER = Logger.getLogger(WavFileDecoder.class);

    @Override
    public SoundDataDecoded decode(File soundFile) throws IOException {
        AudioInputStream stream = null;

        try {
            stream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e) {
            LOGGER.error(e);
            return new SoundDataDecoded();
        }

        SoundDataDecoded decoded = new SoundDataDecoded();

        AudioFormat format = stream.getFormat();

        decoded.frequency = (int)format.getSampleRate();
        decoded.channels = format.getChannels();
        decoded.bitrate = 0; //TODO co tu ma być, a może nic?
        decoded.bitsPerChannel = format.getSampleSizeInBits();

        byte[] b = IOUtils.toByteArray(stream);
        ByteBuffer data = BufferUtils.createByteBuffer(b.length).put(b);
        data.flip();

        decoded.data = data;

        return decoded;
    }
}
