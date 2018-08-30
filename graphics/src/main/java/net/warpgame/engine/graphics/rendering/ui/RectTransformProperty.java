package net.warpgame.engine.graphics.rendering.ui;

import net.warpgame.engine.core.property.Property;
import org.joml.Vector2f;
import org.joml.Vector2fc;

/**
 * @author MarconZet
 * Created 15.08.2018
 */
public class RectTransformProperty extends Property {
    private Vector2f position;
    private float rotation;
    private Vector2f scale;
    private int width;
    private int height;

    public RectTransformProperty(int width, int height) {
        this.width = width;
        this.height = height;
        setUp();
    }

    private void setUp(){
        position = new Vector2f();
        rotation = 0;
        scale = new Vector2f().set(1);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2fc getPosition() {
        return position;
    }

    public void setPosition(Vector2fc position) {
        this.position.set(position);
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vector2fc getScale() {
        return scale;
    }

    public void setScale(Vector2fc scale) {
        this.scale.set(scale);
    }

    public void setWidthAndHeight(int val){
        this.width = val;
        this.height = val;
    }
}
