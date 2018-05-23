package net.warpgame.test;

import net.warpgame.engine.audio.SourceProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;

public class MusicScript extends Script {

    @OwnerProperty(SourceProperty.NAME)
    private SourceProperty sourceProperty;


    public MusicScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        sourceProperty.play();
    }

    @Override
    public void onUpdate(int delta) {

    }
}
