package net.warpgame.engine.audio.decoder;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memByteBuffer;

/**
 * Created by Marcin on 30.04.2017.
 */
public class OggFileDecoder {


    public static SoundData decode(String filename) {
        int result;
        int channels;
        int sampleRate;
        ByteBuffer rawAudio;

        try(MemoryStack stack = stackPush()) {
            IntBuffer channelsBuffer = stackMallocInt(1);
            stackPush();
            IntBuffer sampleRateBuffer = stackMallocInt(1);
            PointerBuffer rawAudioBuffer = stack.pointers(NULL);


            result = stb_vorbis_decode_filename(filename, channelsBuffer, sampleRateBuffer, rawAudioBuffer);

            channels = channelsBuffer.get();

            sampleRate = sampleRateBuffer.get();

            rawAudio = memByteBuffer(rawAudioBuffer.get(0), result * channels);

            stackPop();
            stackPop();
        }
        return new SoundData(result, rawAudio, sampleRate, channels, 16);
    }

}
