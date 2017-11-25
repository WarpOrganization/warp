package pl.warp.engine.audio.decoder;

import java.io.File;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * Created by Marcin on 30.04.2017.
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
    }

    private static ShortBuffer stb_vorbis_decode_filename(String filename, IntBuffer channelsBuffer, IntBuffer sampleRateBuffer) {
        return null;
    }
}
