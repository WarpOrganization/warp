package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSource;

/**
 * @author Hubertus
 *         Created 22.12.16
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
