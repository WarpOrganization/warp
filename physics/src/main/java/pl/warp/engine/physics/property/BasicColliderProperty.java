package pl.warp.engine.physics.property;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

/**
 * Created by hubertus on 7/3/16.
 */

public class BasicColliderProperty extends Property<Component> implements ColliderProperty {

    private final btCollisionShape shape;
    btCollisionObject collisionObject;

    public BasicColliderProperty(Component owner, btCollisionShape shape) {
        super(owner);
        this.shape = shape;
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(shape);
    }


    @Override
    public void addToWorld(btCollisionWorld world) {
        world.addCollisionObject(collisionObject);
    }

    @Override
    public void removeFromWorld(btCollisionWorld world) {
        world.removeCollisionObject(collisionObject);
    }

    @Override
    public void setTransform(Matrix4 transform) {
        collisionObject.setWorldTransform(transform);
    }

    @Override
    public void dispose() {
        shape.dispose();
        collisionObject.dispose();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicColliderProperty that = (BasicColliderProperty) o;
        return Objects.equals(shape, that.shape) &&
                Objects.equals(collisionObject, that.collisionObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, collisionObject);
    }
}
