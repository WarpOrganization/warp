package pl.warp.engine.audio.decoder;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memByteBuffer;

/**
 * Created by MarconZet on 30.04.2017.
 */
public class OggFileDecoder{

    public static SoundData decode(String filename) {

        try(MemoryStack stack = stackPush()) {
            IntBuffer channelsBuffer = stackMallocInt(1);
            stackPush();
            IntBuffer sampleRateBuffer = stackMallocInt(1);
            PointerBuffer rawAudioBuffer = stack.pointers(NULL);

            int result = stb_vorbis_decode_filename(filename, channelsBuffer, sampleRateBuffer, rawAudioBuffer);
            int channels = channelsBuffer.get();
            int sampleRate = sampleRateBuffer.get();
            ByteBuffer rawAudio = memByteBuffer(rawAudioBuffer.get(0), result * channels);

            stackPop();
            stackPop();

            return new SoundData(rawAudio, sampleRate, channels, 16);
        }
    }
}
