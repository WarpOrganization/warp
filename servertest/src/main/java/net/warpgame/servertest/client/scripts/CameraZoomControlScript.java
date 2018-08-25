package net.warpgame.servertest.client.scripts;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.ContextService;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.input.Input;
import org.joml.Vector2f;

/**
 * @author KocproZ
 * Created 24.07.2018
 */
public class CameraZoomControlScript extends Script {

    private static final float ZOOM_MODIFIER = 0.02f;
    private Vector2f lastScrollPos;
    private Vector2f scrollPos;

    public CameraZoomControlScript(Component owner) {
        super(owner);
    }

    @OwnerProperty(@IdOf(TransformProperty.class))
    private TransformProperty transformProperty;

    @ContextService
    private Input input;


    @Override
    public void onInit() {
        scrollPos = new Vector2f();
        lastScrollPos = new Vector2f();
    }

    @Override
    public void onUpdate(int delta) {
        input.getScrollPosition(scrollPos);
        zoom(delta);
        lastScrollPos.set(scrollPos);
    }

    private void zoom(int delta) {
        float scrollDelta = lastScrollPos.y - scrollPos.y;
        if (scrollDelta * ZOOM_MODIFIER * delta + transformProperty.getTranslation().x() > 0)
            transformProperty.move((scrollDelta * ZOOM_MODIFIER * delta), 0, 0);
    }

}
