package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.command.Command;
import net.warpgame.engine.audio.decoder.SoundData;

/**
 * Created by MarconZet on 26.11.2017.
 */
public class FillBufferWithDataCommand implements Command {

    private int buffer;
    private SoundData data;

    public FillBufferWithDataCommand(int buffer, SoundData data){
        this.buffer = buffer;
        this.data = data;
    }

    @Override
    public void execute(AudioContext context) {
        data.fillBufferWithData(buffer);
    }
}
