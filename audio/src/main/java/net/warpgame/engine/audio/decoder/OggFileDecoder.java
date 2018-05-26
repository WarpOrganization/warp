package net.warpgame.engine.audio.decoder;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;

/**
 * Created by Marcin on 30.04.2017.
 */
class OggFileDecoder {


    static SoundData decode(String filename) {
        ShortBuffer shortBuffer;
        int channels;
        int sampleRate;

        IntBuffer channelsBuffer = stackMallocInt(1);
        IntBuffer sampleRateBuffer = stackMallocInt(1);


        shortBuffer = stb_vorbis_decode_filename(filename, channelsBuffer, sampleRateBuffer);

        channels = channelsBuffer.get();

        sampleRate = sampleRateBuffer.get();

        return new SoundData(shortBuffer, sampleRate, channels, 16);
    }

}
