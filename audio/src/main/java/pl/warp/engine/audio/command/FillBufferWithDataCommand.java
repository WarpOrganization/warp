package pl.warp.engine.audio.command;

import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.audio.decoder.SoundData;

/**
 * Created by MarconZet on 26.11.2017.
 */
public class FillBufferWithDataCommand implements Command{

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
