package pl.warp.engine.audio.decoder;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import java.nio.*;

import static org.lwjgl.openal.AL10.alBufferData;

/**
 * Created by Marcin on 30.04.2017.
 */
public class SoundData {
    public int fillBufferWithData(int buffer){
        alBufferData(buffer, getOpenALFormat(), data, frequency);
        return buffer;
    }

    private ByteBuffer data;
    private int frequency;
    private int channels;
    private int bitrate;
    private int bitsPerChannel;

    private int getOpenALFormat()
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

    void setData(ByteBuffer data) {
        this.data = data;
    }

    void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    void setChannels(int channels) {
        this.channels = channels;
    }

    void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    void setBitsPerChannel(int bitsPerChannel) {
        this.bitsPerChannel = bitsPerChannel;
    }
}
