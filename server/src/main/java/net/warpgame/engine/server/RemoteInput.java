package net.warpgame.engine.server;

/**
 * @author Hubertus
 * Created 05.01.2018
 */

public class RemoteInput {
    private boolean forwardPressed;
    private boolean backwardsPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean rotationUp;
    private boolean rotationDown;
    private boolean rotationLeft;
    private boolean rotationRight;
    private boolean rotationLeftX;
    private boolean rotationRightX;

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

    public boolean isRotationLeftX() {
        return rotationLeftX;
    }

    public void setRotationLeftX(boolean rotationLeftX) {
        this.rotationLeftX = rotationLeftX;
    }

    public boolean isRotationRightX() {
        return rotationRightX;
    }

    public void setRotationRightX(boolean rotationRightX) {
        this.rotationRightX = rotationRightX;
    }
}
