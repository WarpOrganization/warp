package pl.warp.engine.audio;

import org.joml.Vector3f;
import pl.warp.engine.core.component.Component;

import static org.lwjgl.openal.AL10.*;

/**
 * @author Hubertus
 *         Created 22.12.16
 */
public class AudioSource {
    private Vector3f offset;
    private int id;
    private Component owner;
    private boolean isRelative;
    private boolean isPersistent;

    public AudioSource(Component owner, Vector3f offset, boolean isPersistent){
        this.owner = owner;
        this.offset = offset;
        this.isRelative = false;
        this.isPersistent = isPersistent;
    }

    public AudioSource(Vector3f offset, boolean isPersistent){
        this.owner = owner;
        this.offset = offset;
        this.isRelative = true;
        this.isPersistent = isPersistent;
    }

    protected void dispose(){
        alDeleteSources(id);
    }

    public Vector3f getOffset() {
        return offset;
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Component getOwner() {
        return owner;
    }

    boolean isPlaying(){
        return alGetSourcei(id, AL_SOURCE_STATE) == AL_PLAYING;
    }


    public boolean isRelative() {
        return isRelative;
    }

    public void setRelative(boolean relative) {
        isRelative = relative;
    }

    public boolean isPersistent() {
        return isPersistent;
    }

    public void setPersistent(boolean persistent) {
        isPersistent = persistent;
    }
}
