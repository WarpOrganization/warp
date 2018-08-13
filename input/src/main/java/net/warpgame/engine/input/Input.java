package net.warpgame.engine.input;

import org.joml.Vector2f;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 11
 */
public interface Input {

    void getCursorPosition(Vector2f vector);

    @Deprecated
    Vector2f getCursorPositionDelta();

    void getScrollPosition(Vector2f vector);

    boolean isKeyDown(int key);

    boolean isMouseButtonDown(int button);

    void update();

}
