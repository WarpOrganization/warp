package pl.warp.engine.audio.decoder;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import java.nio.*;

/**
 * Created by Marcin on 30.04.2017.
 */
public class SoundDataDecoded {


    public ByteBuffer data;

    public int frequency;
    public int channels;
    public int bitrate;
    public int bitsPerChannel;

    public int getOpenALFormat()
    {
        final int MONO = 1;
        final int STEREO = 2;
        int openALFormat = -1;
        switch (channels) {
            case MONO:
                switch (bitsPerChannel) {
                    case 8:
                        openALFormat = AL10.AL_FORMAT_MONO8;
                        break;
                    case 16:
                        openALFormat = AL10.AL_FORMAT_MONO16;
                        break;
                }
                break;
            case STEREO:
                switch (bitsPerChannel) {
                    case 8:
                        openALFormat = AL10.AL_FORMAT_STEREO8;
                        break;
                    case 16:
                        openALFormat = AL10.AL_FORMAT_STEREO16;
                        break;
                }
                break;
        }
        return openALFormat;
    }
}
