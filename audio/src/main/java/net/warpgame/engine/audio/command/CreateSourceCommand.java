package net.warpgame.engine.audio.command;

import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.AudioSource;

import static org.lwjgl.openal.AL10.alGenSources;

/**
 * @author hubertus
 *         Created 23.12.16
 */
public class CreateSourceCommand implements Command {

    private AudioSource source;

    public CreateSourceCommand(AudioSource source) {
        this.source = source;
    }

    @Override
    public void execute(AudioContext context) {
        source.setId(alGenSources());
    }
}
