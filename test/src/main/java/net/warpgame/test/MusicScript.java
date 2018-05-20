package net.warpgame.test;

import net.warpgame.engine.audio.AudioManager;
import net.warpgame.engine.audio.AudioSource;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.ContextService;
import net.warpgame.engine.graphics.camera.CameraHolder;
import org.joml.Vector3f;

public class MusicScript extends Script {

    @ContextService
    private AudioManager audioManager;

    private AudioSource audioSource;
    private boolean started;



    public MusicScript(Component owner) {
        super(owner);
        started = false;
    }

    @Override
    public void onInit() {
        audioSource = audioManager.createPersistentSource(this.getOwner(), new Vector3f(0,0,0));
    }

    @Override
    public void onUpdate(int delta) {
        if(!started) {
            audioManager.play(audioSource, "Stellardrone - Light Years - 10 Messier 45");
            started = true;
        }
    }
}
