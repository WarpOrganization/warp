package pl.warp.engine.audio.command;

import org.lwjgl.BufferUtils;
import pl.warp.engine.audio.AudioContext;
import pl.warp.engine.audio.MusicSource;

import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;

/**
 * @author Hubertus
 *         Created 26.12.2016
 */
public class CreateMusicSourceCommand implements Command {

    private MusicSource source;

    public CreateMusicSourceCommand(MusicSource source) {
        this.source = source;
    }

    @Override
    public void execute(AudioContext context) {
        source.setId(alGenSources());
        IntBuffer buffers = BufferUtils.createIntBuffer(3);
        alGenBuffers(buffers);
        for (int i = 0; i < 3; i++) source.getBuffers().add(buffers.get(i));
    }
}
