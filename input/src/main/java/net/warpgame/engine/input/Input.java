package net.warpgame.engine.input;

import org.joml.Vector2f;
import org.joml.Vector2fc;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 11
 */
public interface Input {

    @Deprecated
    void getCursorPosition(Vector2f vector);

    Vector2fc getCursorPosition();

    @Deprecated
    Vector2f getCursorPositionDelta();

    @Deprecated
    void getScrollPosition(Vector2f vector);

    Vector2fc getScrollPosition();

    boolean isKeyDown(int key);

    boolean isMouseButtonDown(int button);

    void update();

}
