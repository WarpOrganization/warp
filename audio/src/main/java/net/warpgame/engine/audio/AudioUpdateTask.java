package net.warpgame.engine.audio;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.ExecuteAfterTask;
import net.warpgame.engine.core.context.task.RegisterTask;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.property.Transforms;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import java.nio.IntBuffer;

import static org.lwjgl.openal.AL10.*;

/**
 * @author Hubertus
 *         Created 20.12.2016
 */

@Service
@RegisterTask(thread = "audio")
@ExecuteAfterTask(AudioCommandsTask.class)
public class AudioUpdateTask extends EngineTask {

    private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);


    private AudioContext context;

    public AudioUpdateTask(AudioContext audioContext) {
        this.context = audioContext;
    }

    @Override
    protected void onInit() {

    }


    @Override
    protected void onClose() {

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
        Transforms.getAbsolutePosition(source.getOwner(), posVector);
        alSource3f(source.getId(), AL_POSITION, posVector.x, posVector.y, posVector.z);

    }
}
