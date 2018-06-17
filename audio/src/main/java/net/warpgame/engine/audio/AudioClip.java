package net.warpgame.engine.audio;

import jdk.nashorn.internal.objects.annotations.Function;
import net.warpgame.engine.audio.command.FillBufferWithData;
import net.warpgame.engine.audio.decoder.SoundData;
import net.warpgame.engine.audio.decoder.SoundDecoderManager;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;

public class AudioClip {
    private int id;
    private String file;

    public AudioClip(String file) {
        this.id = -1;
        this.file = file;
    }

    void init(AudioContext context){
        if(id == -1) {
            try {
                SoundData data = SoundDecoderManager.decode(file);
                id = context.getBuffer();
                context.putCommand(new FillBufferWithData(id, data));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName(){
        return FilenameUtils.getBaseName(file);
    }

    public String getPath(){
        return FilenameUtils.getPath(file);
    }

    public String getFile(){
        return file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
