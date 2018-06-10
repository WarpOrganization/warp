package net.warpgame.engine.physics.constraints;

import com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint;
import net.warpgame.engine.physics.FullPhysicsProperty;

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
    private FullPhysicsProperty property1;
    private FullPhysicsProperty property2;
    protected btTypedConstraint bulletConstraint;

    public Constraint(FullPhysicsProperty property1, FullPhysicsProperty property2) {
        this.property1 = property1;
        this.property2 = property2;
    }

    public abstract int getType();

    public btTypedConstraint getBulletConstraint() {
        return bulletConstraint;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }


    public FullPhysicsProperty getProperty1() {
        return property1;
    }

    public FullPhysicsProperty getProperty2() {
        return property2;
    }
}
