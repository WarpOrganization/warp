package pl.warp.engine.audio;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.properties.Transforms;

import static org.lwjgl.openal.AL10.*;

/**
 * @author Hubertus
 *         Created 20.12.2016
 */
public class AudioPosUpdateTask extends EngineTask {

    private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);


    private AudioContext context;

    public AudioPosUpdateTask(AudioContext audioContext) {
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
        updateDirections();
        updateListener();
        updateSources();
    }

    private Vector3f forwardVector = new Vector3f();
    private Vector3f upVector = new Vector3f();
    private Vector3f posVector = new Vector3f();
    private Vector3f listenerPosVector = new Vector3f();
    private float[] orientation = new float[6];

    private void updateDirections() {
        Quaternionf fullRotation = Transforms.getAbsoluteRotation(context.getListener().getComponent());
        fullRotation.transform(forwardVector.set(FORWARD_VECTOR));
        fullRotation.transform(upVector.set(UP_VECTOR));
        orientation[0] = forwardVector.x;
        orientation[1] = forwardVector.y;
        orientation[2] = forwardVector.z;
        orientation[3] = upVector.x;
        orientation[4] = upVector.y;
        orientation[5] = upVector.z;
    }

    private void updateListener() {
        Transforms.getAbsolutePosition(context.getListener().getComponent(), listenerPosVector);
        listenerPosVector.add(context.getListener().getOffset());
        alListener3f(AL_POSITION, listenerPosVector.x, listenerPosVector.y, listenerPosVector.z);
        alListenerfv(AL_ORIENTATION, orientation);
    }

    private void updateSources() {
        for (int i = 0; i < context.getPlaying().size(); i++) {
            AudioSource source = context.getPlaying().get(i);
            if (alGetSourcei(source.getId(), AL_SOURCE_STATE) == AL_PLAYING) {
                if (source.isPlaying()) {
                    Transforms.getAbsolutePosition(source.getOwner(), posVector);
                    posVector.add(source.getOffset());
                    alSource3f(source.getId(), AL_POSITION, posVector.x, posVector.y, posVector.z);
                }
            } else {
                context.getPlaying().remove(i);
                if(!source.isPersistent()){
                    source.dispose();
                }
            }
        }
    }
}
