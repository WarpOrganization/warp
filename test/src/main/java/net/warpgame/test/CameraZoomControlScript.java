package net.warpgame.test;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.ContextService;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.input.Input;

/**
 * @author KocproZ
 * Created 24.07.2018
 */
public class CameraZoomControlScript extends Script {

    private static final float ZOOM_MODIFIER = 0.02f;
    private double lastScrollPos;
    private double scrollPos;

    public CameraZoomControlScript(Component owner) {
        super(owner);
    }

    @OwnerProperty(@IdOf(TransformProperty.class))
    private TransformProperty transformProperty;

    @ContextService
    private Input input;


    @Override
    public void onInit() {
        scrollPos = 0;
        lastScrollPos = 0;
    }

    @Override
    public void onUpdate(int delta) {
        scrollPos = input.getScrollPosition().y;
        zoom(delta);
        lastScrollPos = scrollPos;
    }

    private void zoom(int delta) {
        double scrollDelta = lastScrollPos - scrollPos;
        transformProperty.move((float) (scrollDelta * ZOOM_MODIFIER * delta), 0, 0);
    }

}
