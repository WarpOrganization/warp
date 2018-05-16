package net.warpgame.engine.audio.decoder;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memByteBuffer;

/**
 * Created by Marcin on 30.04.2017.
 */
class OggFileDecoder {


    static SoundData decode(String filename) {
        ShortBuffer result;
        int channels;
        int sampleRate;
        ByteBuffer rawAudio;

        IntBuffer channelsBuffer = stackMallocInt(1);
        IntBuffer sampleRateBuffer = stackMallocInt(1);


        result = stb_vorbis_decode_filename(filename, channelsBuffer, sampleRateBuffer);

        channels = channelsBuffer.get();

        sampleRate = sampleRateBuffer.get();

        rawAudio = ByteBuffer.allocate(result.capacity() * 2);
        rawAudio.asShortBuffer().put(result);

        return new SoundData(rawAudio, sampleRate, channels, 16);
    }

}
