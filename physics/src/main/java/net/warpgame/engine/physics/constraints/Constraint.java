package net.warpgame.engine.physics.constraints;

/**
 * @author Hubertus
 * Created 04.10.2017
 */
public abstract class Constraint {

    public static final int BALL_CONSTRAINT = 2;
    public static final int HINGE_CONSTRAINT = 4;
    public static final int SLIDER_CONSTRAINT = 8;
    public static final int CONE_CONSTRAINT = 16;
    public static final int FIXED_CONSTRAINT = 32;
    public static final int GENERIC_CONSTRAINT = 64;

    private int id;

    public abstract int getType();

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
