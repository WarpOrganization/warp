package net.warpgame.test;

import net.warpgame.engine.audio.AudioSourceProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;

public class MusicScript extends Script {

    @OwnerProperty(AudioSourceProperty.NAME)
    private AudioSourceProperty audioSourceProperty;


    public MusicScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        audioSourceProperty.play();
    }

    @Override
    public void onUpdate(int delta) {

    }
}
