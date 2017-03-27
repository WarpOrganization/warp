package pl.warp.game.graphics.effects.atmosphere;

import org.joml.Vector3f;
import pl.warp.engine.core.scene.Property;
import pl.warp.game.scene.GameComponent;

/**
 * @author Jaca777
 *         Created 2017-03-12 at 11
 */
public class AtmosphereProperty extends Property<GameComponent> {
    public static final String ATMOSPHERE_PROPERTY_NAME = "atmosphere";

    private Vector3f color;

    private float atmosphereRadius;
    private float innerExp;
    private float innerMul;
    private float outerExp;
    private float outerMul;
    private float lightMul;

    public AtmosphereProperty(Vector3f color, float atmosphereRadius, float innerExp, float innerMul, float outerExp, float outerMul, float lightMul) {
        super(ATMOSPHERE_PROPERTY_NAME);
        this.color = color;
        this.atmosphereRadius = atmosphereRadius;
        this.innerExp = innerExp;
        this.innerMul = innerMul;
        this.outerExp = outerExp;
        this.outerMul = outerMul;
        this.lightMul = lightMul;
    }

    public static String getAtmospherePropertyName() {
        return ATMOSPHERE_PROPERTY_NAME;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getAtmosphereRadius() {
        return atmosphereRadius;
    }

    public void setAtmosphereRadius(float atmosphereRadius) {
        this.atmosphereRadius = atmosphereRadius;
    }

    public float getInnerExp() {
        return innerExp;
    }

    public void setInnerExp(float innerExp) {
        this.innerExp = innerExp;
    }

    public float getInnerMul() {
        return innerMul;
    }

    public void setInnerMul(float innerMul) {
        this.innerMul = innerMul;
    }

    public float getOuterExp() {
        return outerExp;
    }

    public void setOuterExp(float outerExp) {
        this.outerExp = outerExp;
    }

    public float getOuterMul() {
        return outerMul;
    }

    public void setOuterMul(float outerMul) {
        this.outerMul = outerMul;
    }

    public float getLightMul() {
        return lightMul;
    }

    public AtmosphereProperty setLightMul(float lightMul) {
        this.lightMul = lightMul;
        return this;
    }
}
