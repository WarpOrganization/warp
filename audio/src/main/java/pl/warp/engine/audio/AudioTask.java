package pl.warp.engine.audio;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;
import pl.warp.engine.core.EngineTask;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.SOFTHRTF.*;

/**
 * @author Hubertus
 *         Created 17.12.16
 */
public class AudioTask extends EngineTask {

    private AudioContext context;
    private long device;

    public AudioTask(AudioContext context){
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
        while (!context.getCommandsQueue().isEmpty()){
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

    private void initOpenAL(){
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
