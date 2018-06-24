package net.warpgame.engine.audio.decoder;

import org.lwjgl.openal.AL10;

import java.nio.*;

import static org.lwjgl.openal.AL10.alBufferData;

/**
 * Created by Marcin on 30.04.2017.
 */
public class SoundData {
    private ShortBuffer data;
    private int frequency;
    private int channels;
    private int bitsPerChannel;

    public SoundData(ShortBuffer data, int frequency, int channels, int bitsPerChannel){
        this.data = data;
        this.frequency = frequency;
        this.channels = channels;
        this.bitsPerChannel = bitsPerChannel;
    }

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

    public ShortBuffer getData() {
        return data;
    }

    public int getFrequency() {
        return frequency;
    }
}
