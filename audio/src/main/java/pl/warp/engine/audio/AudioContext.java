package pl.warp.engine.audio;

import static org.lwjgl.openal.AL10.alGenSources;

/**
 * Created by huber on 20.12.2016.
 */
public class AudioContext {
    public int registerAudioSource(){
        int source = alGenSources();

        //TODO store value
        return source;
    }

    public void unregisterAudioSource(int sourceId){
        //TODO
    }

    public void ScheudleTask(){
        //TODO
    }

    protected void getPlaying(){
        //TODO
    }

    protected void getRegisteredSources(){
        //TODO
    }
}
