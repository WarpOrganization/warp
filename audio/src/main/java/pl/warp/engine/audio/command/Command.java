package pl.warp.engine.audio.command;

import pl.warp.engine.audio.AudioContext;

/**
 * @author Hubertus
 *         Created 22.12.16
 */
public interface Command {
    void execute(AudioContext context);
}
