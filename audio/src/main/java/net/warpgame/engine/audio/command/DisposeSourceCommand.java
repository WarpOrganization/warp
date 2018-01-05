package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSource;

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
