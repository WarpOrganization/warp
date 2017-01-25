package pl.warp.engine.audio.command;

import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.audio.AudioSource;

/**
 * @author Hubertus
 *         Created 28.12.16
 */
public class DisposeSourceCommand implements Command {

    private AudioSource source;

    public DisposeSourceCommand(AudioSource source) {
        this.source = source;
    }


    @Override
    public void execute(AudioContext context) {
        //TODO
    }
}
