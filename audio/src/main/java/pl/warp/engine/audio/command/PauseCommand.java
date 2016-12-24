package pl.warp.engine.audio.command;

import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.audio.AudioSource;

/**
 * Created by hubertus on 22.12.16.
 */
public class PauseCommand implements Command{

    private AudioSource source;

    public PauseCommand(AudioSource source){

        this.source = source;
    }

    @Override
    public void execute(AudioContext context) {
        //TODO
    }
}
