package pl.warp.engine.audio.decoder;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import java.nio.*;

/**
 * Created by Marcin on 30.04.2017.
 */
public class SoundDataDecoded {


    private ByteBuffer data;
    private int frequency;
    private int channels;
    private int bitrate;
    private int bitsPerChannel;

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

    public ByteBuffer getData() {
        return data;
    }

    public void setData(ByteBuffer data) {
        this.data = data;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public void setBitsPerChannel(int bitsPerChannel) {
        this.bitsPerChannel = bitsPerChannel;
    }
}
