package net.warpgame.engine.audio.command;

import org.joml.Vector3f;
import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSource;

/**
 * @author hubertus
 *         Created 23.12.16
 */
public class SetOffsetCommand implements Command {

    private final AudioSource source;
    private final Vector3f offset;

    public SetOffsetCommand(AudioSource source, Vector3f offset) {
        this.source = source;
        this.offset = offset;
    }

    @Override
    public void execute(AudioContext context) {
        //TODO
    }
}
