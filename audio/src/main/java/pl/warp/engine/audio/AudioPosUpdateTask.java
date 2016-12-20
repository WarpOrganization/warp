package pl.warp.engine.audio;

import pl.warp.engine.core.EngineTask;

/**
 * Created by huber on 20.12.2016.
 */
public class AudioPosUpdateTask extends EngineTask {

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

    }
}
