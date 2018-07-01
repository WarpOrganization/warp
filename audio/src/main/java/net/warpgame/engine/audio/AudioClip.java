package net.warpgame.engine.audio;


import net.warpgame.engine.audio.command.Command;
import net.warpgame.engine.audio.command.buffer.FillBufferCommand;
import net.warpgame.engine.audio.decoder.SoundData;
import net.warpgame.engine.audio.decoder.SoundDecoderManager;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class AudioClip {
    private int id;
    private String file;

    public AudioClip(String file) {
        this.id = -1;
        this.file = file;
    }

    void init(AudioContext context, BlockingQueue<Command> commandQueue){
        if(id == -1) {
            try {
                SoundData data = SoundDecoderManager.decode(file);
                id = context.getBuffer();
                context.getAllBuffers().add(this);
                commandQueue.add(new FillBufferCommand(this, data));
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to get buffer id for AudioClip");
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
