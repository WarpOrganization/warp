package net.warpgame.engine.audio;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.ExecuteAfterTask;
import net.warpgame.engine.core.context.task.RegisterTask;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.property.Transforms;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.ALC_TRUE;
import static org.lwjgl.openal.ALC10.alcCreateContext;
import static org.lwjgl.openal.ALC10.alcMakeContextCurrent;
import static org.lwjgl.openal.SOFTHRTF.ALC_HRTF_SOFT;
import static org.lwjgl.openal.SOFTHRTF.alcResetDeviceSOFT;

/**
 * @author Hubertus
 *         Created 20.12.2016
 */

@Service
@RegisterTask(thread = "audio")
public class AudioUpdateTask extends EngineTask {
    private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);


    private AudioContext context;
    private long device;
    private long alcContext;

    public AudioUpdateTask(AudioContext audioContext) {
        this.context = audioContext;
    }

    @Override
    protected void onInit() {
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


    @Override
    protected void onClose() {
        context.getAllBuffers().stream().map(AudioClip::getId).forEach(AL10::alDeleteBuffers);
        context.getFreeBuffers().forEach(AL10::alDeleteBuffers);

        context.getAllSources().stream().map(AudioSourceProperty::getId).forEach(AL10::alDeleteBuffers);
        context.getFreeSources().forEach(AL10::alDeleteBuffers);

        ALC10.alcDestroyContext(alcContext);
        ALC10.alcCloseDevice(device);
    }

    @Override
    public void update(int delta) {
        try {
            checkFreeBuffersAndSources();
            if(context.getListener() == null)
                return;
            updateListener();
            updateSources();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkFreeBuffersAndSources() {
        if(context.getFreeBuffers().size() < 10) {
            IntBuffer intBuffer = BufferUtils.createIntBuffer(50);
            AL10.alGenBuffers(intBuffer);
            while (intBuffer.hasRemaining()) {
                context.getFreeBuffers().add(intBuffer.get());
            }
        }
        if(context.getFreeSources().size() < 10) {
            IntBuffer intBuffer = BufferUtils.createIntBuffer(50);
            AL10.alGenSources(intBuffer);
            while (intBuffer.hasRemaining()) {
                context.getFreeSources().add(intBuffer.get());
            }
        }
    }

    private Vector3f forwardVector = new Vector3f();
    private Vector3f upVector = new Vector3f();
    private Vector3f posVector = new Vector3f();

    private Vector3f listenerPosVector = new Vector3f();
    private float[] orientation = new float[6];

    private void updateListener() {
        Quaternionf fullRotation = Transforms.getAbsoluteRotation(context.getListener().getOwner(), new Quaternionf());
        fullRotation.transform(forwardVector.set(FORWARD_VECTOR));
        fullRotation.transform(upVector.set(UP_VECTOR));
        orientation[0] = forwardVector.x;
        orientation[1] = forwardVector.y;
        orientation[2] = forwardVector.z;
        orientation[3] = upVector.x;
        orientation[4] = upVector.y;
        orientation[5] = upVector.z;

        Transforms.getAbsolutePosition(context.getListener().getOwner(), listenerPosVector);
        alListener3f(AL_POSITION, listenerPosVector.x, listenerPosVector.y, listenerPosVector.z);
        alListenerfv(AL_ORIENTATION, orientation);
    }

    private void updateSources() throws InterruptedException {
        for (int i = 0; i < context.getPlayingSources().size(); i++) {
            AudioSourceProperty source = context.getPlayingSources().get(i);
            while(source.getCommandQueue().size() > 0)
                source.getCommandQueue().take().execute();
            if (alGetSourcei(source.getId(), AL_SOURCE_STATE) == AL_PLAYING) {
                updateSourcePos(source);
            } else {
                context.getPlayingSources().remove(i);
                source.setPlaying(false);
            }
        }
    }

    private void updateSourcePos(AudioSourceProperty source) {
        try {
            Transforms.getAbsolutePosition(source.getOwner(), posVector);
        } catch (NullPointerException e) {
            posVector.zero();
        }
        alSource3f(source.getId(), AL_POSITION, posVector.x, posVector.y, posVector.z);

    }
}
