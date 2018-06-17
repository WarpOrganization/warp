package net.warpgame.engine.audio;


import com.sun.istack.internal.NotNull;
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

    void init(@NotNull AudioContext context){
        if(id == -1) {
            try {
                SoundData data = SoundDecoderManager.decode(file);
                id = context.getBuffer();
                context.getAllBuffers().add(this);
                context.putCommand(new FillBufferWithData(this, data));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getName(){
        return FilenameUtils.getBaseName(file);
    }

    public String getFile(){
        return file;
    }

    public int getId() {
        return id;
    }

}
