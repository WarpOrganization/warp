package pl.warp.engine.audio.command;

import pl.warp.engine.audio.AudioContext;

/**
 * Created by hubertus on 22.12.16.
 */
public interface Command {
    void execute(AudioContext context);
}
