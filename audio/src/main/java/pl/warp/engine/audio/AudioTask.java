package pl.warp.engine.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import pl.warp.engine.core.execution.task.EngineTask;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Queue;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.SOFTHRTF.ALC_HRTF_SOFT;
import static org.lwjgl.openal.SOFTHRTF.alcResetDeviceSOFT;

/**
 * @author Hubertus
 *         Created 17.12.16
 */
public class AudioTask extends EngineTask {

    private AudioContext context;
    private long device;
    private static final int BUFFER_LENGTH = 48000;

    public AudioTask(AudioContext context) {
        this.context = context;
    }

    @Override
    protected void onInit() {
        initOpenAL();
    }

    @Override
    protected void onClose() {
        //TODO free sources, buffers
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
        loadBuffers();
    }

    public AudioContext getContext() {
        return context;
    }

    byte[] b = new byte[BUFFER_LENGTH];

    private void loadBuffers() {
        for (int i = 0; i < context.getMusicPlaying().size(); i++) {
            MusicSource source = context.getMusicPlaying().get(i);
            int n = alGetSourcei(source.getId(), AL_BUFFERS_PROCESSED);
            for (int j = 0; j < n; j++) source.getBuffers().offer(alSourceUnqueueBuffers(source.getId()));
            while (!source.getBuffers().isEmpty()) {
                Queue<Integer> buffers = source.getBuffers();
                int buffer = buffers.poll();
                if (!source.isDoneReading()) {
                    try {
                        if (source.getStream().read(b) != -1) {
                            ByteBuffer data = BufferUtils.createByteBuffer(b.length).put(b);
                            data.flip();
                            alBufferData(buffer, source.getOpenALFormat(), data, (int) source.getSampleRate());
                            source.incrementCurrentCycles();
                            alSourceQueueBuffers(source.getId(), buffer);
                        } else
                            source.doneReading();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!source.isPlaying()) {
                alSourcePlay(source.getId());
            }
        }
    }

    private void updateMusicSources() {

    }

    private void initOpenAL() {
        device = ALC10.alcOpenDevice((ByteBuffer) null);
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        long alcContext = alcCreateContext(device, (IntBuffer) null);
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
