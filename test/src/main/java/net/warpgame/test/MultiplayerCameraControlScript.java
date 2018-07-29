package net.warpgame.test;

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
public class MultiplayerCameraControlScript extends Script {

    private static final float ROT_MODIFIER = 0.0001f;

    public MultiplayerCameraControlScript(Component owner) {
        super(owner);
    }

    @OwnerProperty(@IdOf(TransformProperty.class))
    private TransformProperty transformProperty;

    @ContextService
    private Input input;

    private Vector2f lastCursorPos = new Vector2f();

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
        rotate(delta);
    }

    private void rotate(int delta) {
        Vector2f cursorPositionDelta = input.getCursorPosition();
        cursorPositionDelta.sub(lastCursorPos);
        transformProperty.rotateZ(-cursorPositionDelta.y * ROT_MODIFIER * delta);
        transformProperty.rotateLocalY(-cursorPositionDelta.x * ROT_MODIFIER * delta);
        lastCursorPos = input.getCursorPosition();
    }

}
