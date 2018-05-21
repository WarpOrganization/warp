package net.warpgame.engine.audio;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.ExecuteAfterTask;
import net.warpgame.engine.core.context.task.RegisterTask;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import net.warpgame.engine.core.execution.task.EngineTask;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Collection;
import java.util.Queue;
import java.util.TreeMap;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.SOFTHRTF.ALC_HRTF_SOFT;
import static org.lwjgl.openal.SOFTHRTF.alcResetDeviceSOFT;

/**
 * @author Hubertus
 *         Created 17.12.16
 */

@Service
@RegisterTask(thread = "audio")
public class AudioCommandsTask extends EngineTask {

    private AudioContext context;
    private long device;
    private long alcContext;
    private static final int BUFFER_LENGTH = 48000;

    public AudioCommandsTask(AudioContext context) {
        this.context = context;
    }

    @Override
    protected void onInit() {
        initOpenAL();
    }

    @Override
    protected void onClose() {

        //TODO FREE SOURCES

        TreeMap<String, Integer> soundBank = context.getSoundBank().sounds;
        IntBuffer buffers = BufferUtils.createIntBuffer(soundBank.size());
        soundBank.forEach((x, y) -> buffers.put(y));
        AL10.alDeleteBuffers(buffers);

        ALC10.alcDestroyContext(alcContext);
        ALC10.alcCloseDevice(device);
    }

    @Override
    public void update(int delta) {
        while (!context.getCommandsQueue().isEmpty()) {
            try {
                context.getCommandsQueue().take().execute(context);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public AudioContext getContext() {
        return context;
    }

    byte[] b = new byte[BUFFER_LENGTH];

    private void initOpenAL() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        alcContext = alcCreateContext(device, (IntBuffer) null);
        alcMakeContextCurrent(alcContext);
        AL.createCapabilities(deviceCaps);
        IntBuffer attr = BufferUtils.createIntBuffer(10)
                .put(ALC_HRTF_SOFT)
                .put(ALC_TRUE)
                .put(0);
        attr.flip();
        alcResetDeviceSOFT(device, attr);

        alDistanceModel(AL10.AL_INVERSE_DISTANCE_CLAMPED);
    }
}
