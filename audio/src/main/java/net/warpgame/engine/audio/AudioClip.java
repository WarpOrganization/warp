package net.warpgame.engine.audio;

import jdk.nashorn.internal.objects.annotations.Function;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

public class AudioClip {
    private int id;
    private String file;

    public AudioClip(String file) {
        this.file = file;
    }

    void init(AudioContext context){
        context.getSoundBank().initAudioClip(this);
    }

    public String getName(){
        return FilenameUtils.getBaseName(file);
    }

    public String getPaht(){
        return FilenameUtils.getPath(file);
    }
}
