package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;

/**
 * @author Hubertus
 *         Created 22.12.16
 */
public interface Command {
    void execute(AudioContext context);
}
