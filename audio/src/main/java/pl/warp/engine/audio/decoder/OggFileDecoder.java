package pl.warp.engine.audio.decoder;

import org.lwjgl.BufferUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Created by MarconZet on 30.04.2017.
 */
public class OggFileDecoder{

    public static SoundData decode(String filename) {
        stackPush();
        IntBuffer channelsBuffer = stackMallocInt(1);
        stackPush();
        IntBuffer sampleRateBuffer = stackMallocInt(1);

        ShortBuffer rawAudioBuffer = stb_vorbis_decode_filename(filename, channelsBuffer, sampleRateBuffer);

        int channels = channelsBuffer.get();
        int sampleRate = sampleRateBuffer.get();
        stackPop();
        stackPop();
        short[] s = rawAudioBuffer.array();
        ByteBuffer bb = ByteBuffer.allocate(s.length * 2);
        bb.asShortBuffer().put(s);
        return new SoundData(bb, sampleRate, channels, 16);
    }
}
