package net.warpgame.engine.audio.command;

import org.lwjgl.BufferUtils;
import net.warpgame.engine.audio.AudioContext;
import net.warpgame.engine.audio.MusicSource;

import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class CreateMusicSourceCommand implements Command {

    private static final int N_BUFFERS = 3;

    private MusicSource source;

    public CreateMusicSourceCommand(MusicSource source) {
        this.source = source;
    }

    @Override
    public void execute(AudioContext context) {
        source.setId(alGenSources());
        IntBuffer buffers = BufferUtils.createIntBuffer(N_BUFFERS);
        alGenBuffers(buffers);
        for (int i = 0; i < 3; i++) source.getBuffers().add(buffers.get(i));
        //TODO it was probably important
        //context.getMusicSources().put(source.getId(), source);
    }
}
