package net.warpgame.engine.input;

import org.joml.Vector2f;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 11
 */
public interface Input {

    Vector2f getCursorPosition();

    @Deprecated
    Vector2f getCursorPositionDelta();

    Vector2f getScrollPosition();

    boolean isKeyDown(int key);

    boolean isMouseButtonDown(int button);

    void update();

}
