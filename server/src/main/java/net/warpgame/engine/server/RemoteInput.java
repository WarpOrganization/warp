package net.warpgame.engine.server;

import net.warpgame.engine.core.context.service.Service;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
@Service
public class RemoteInput {
    private boolean forwardPressed;
    private boolean backwardsPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean rotationUp;
    private boolean rotationDown;
    private boolean rotationRight;
    private boolean rotationLeft;

    public boolean isForwardPressed() {
        return forwardPressed;
    }

    public void setForwardPressed(boolean forwardPressed) {
        this.forwardPressed = forwardPressed;
    }

    public boolean isBackwardsPressed() {
        return backwardsPressed;
    }

    public void setBackwardsPressed(boolean backwardsPressed) {
        this.backwardsPressed = backwardsPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRotationUp() {
        return rotationUp;
    }

    public void setRotationUp(boolean rotationUp) {
        this.rotationUp = rotationUp;
    }

    public boolean isRotationDown() {
        return rotationDown;
    }

    public void setRotationDown(boolean rotationDown) {
        this.rotationDown = rotationDown;
    }

    public boolean isRotationRight() {
        return rotationRight;
    }

    public void setRotationRight(boolean rotationRight) {
        this.rotationRight = rotationRight;
    }

    public boolean isRotationLeft() {
        return rotationLeft;
    }

    public void setRotationLeft(boolean rotationLeft) {
        this.rotationLeft = rotationLeft;
    }
}
