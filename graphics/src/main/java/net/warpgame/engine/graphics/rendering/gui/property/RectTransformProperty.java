package net.warpgame.engine.graphics.rendering.gui.property;

import net.warpgame.engine.core.property.Property;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * @author MarconZet
 * Created 15.08.2018
 */
public class RectTransformProperty extends Property {
    private Vector3f position;
    private Quaternionf rotation;
    private Vector3f scale;
    private int width;
    private int height;

    public RectTransformProperty(int width, int height) {
        this.width = width;
        this.height = height;
        setUp();
    }

    private void setUp(){
        position = new Vector3f();
        rotation = new Quaternionf();
        scale = new Vector3f().set(1);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
