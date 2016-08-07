package pl.warp.engine.graphics.light;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.Transforms;

/**
 * @author Jaca777
 *         Created 2016-06-30 at 13
 */
public class SpotLight {

    private static final Vector3f NON_DIRECTIONAL_DIR = new Vector3f();
    private static final float NON_DIRECTIONAL_ANGLE = 181.0f;
    private static final float NON_DIRECTIONAL_GRADIENT = 0.0f;

    private Component owner;
    private Vector3f relativePosition;
    private Vector3f coneDirection;
    private float coneAngle;
    private float coneGradient;
    private Vector3f color, ambientColor;
    private float attenuation, gradient;
    private boolean enabled = true;

    public SpotLight(
            Component owner,
            Vector3f relativePosition,
            Vector3f coneDirection, float coneAngle,
            float coneGradient,
            Vector3f color,
            Vector3f ambientColor,
            float attenuation,
            float gradient) {
        this.owner = owner;
        this.relativePosition = relativePosition;
        this.coneDirection = coneDirection;
        this.coneAngle = coneAngle;
        this.coneGradient = coneGradient;
        this.color = color;
        this.ambientColor = ambientColor;
        this.attenuation = attenuation;
        this.gradient = gradient;
    }

    public SpotLight(Component owner,
                     Vector3f relativePosition,
                     Vector3f color,
                     Vector3f ambientColor,
                     float attenuation,
                     float gradient) {
        this(owner,
                relativePosition,
                NON_DIRECTIONAL_DIR,
                NON_DIRECTIONAL_ANGLE,
                NON_DIRECTIONAL_GRADIENT,
                color,
                ambientColor,
                attenuation,
                gradient);
    }

    private Vector3f tempPosition = new Vector3f();

    public Vector3f getPosition() {
        Matrix4f fullTransform = Transforms.getActualTransform(owner);
        return fullTransform.transformPosition(relativePosition, tempPosition);
    }

    public void setRelativePosition(Vector3f position) {
        this.relativePosition = position;
    }

    private Vector3f tempDirection = new Vector3f();

    public Vector3f getDirection() {
        Quaternionf fullRotation = Transforms.getActualRotation(owner);
        return fullRotation.transform(coneDirection, tempDirection);
    }


    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setConeDirection(Vector3f direction) {
        this.coneDirection = direction;
    }

    public float getConeAngle() {
        return coneAngle;
    }

    public void setConeAngle(float coneAngle) {
        this.coneAngle = coneAngle;
    }

    public Vector3f getConeDirection() {
        return coneDirection;
    }

    public float getConeGradient() {
        return coneGradient;
    }

    public void setConeGradient(float coneGradient) {
        this.coneGradient = coneGradient;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getAmbientColor() {
        return ambientColor;
    }

    public void setAmbientColor(Vector3f ambientColor) {
        this.ambientColor = ambientColor;
    }

    public float getAttenuation() {
        return attenuation;
    }

    public void setAttenuation(float attenuation) {
        this.attenuation = attenuation;
    }

    public float getGradient() {
        return gradient;
    }

    public void setGradient(float gradient) {
        this.gradient = gradient;
    }


}
