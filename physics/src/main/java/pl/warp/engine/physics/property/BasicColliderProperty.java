package pl.warp.engine.physics.property;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;

import java.util.Objects;

/**
 * Created by hubertus on 7/3/16.
 */

public class BasicColliderProperty extends Property<Component> implements ColliderProperty {

    private final btCollisionShape shape;
    private btCollisionObject collisionObject;

    private BasicColliderPropertyLogic logic;

    public BasicColliderProperty(Component owner, btCollisionShape shape) {
        super(owner, COLLIDER_PROPERTY_NAME);
        this.shape = shape;
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(shape);
        Matrix4 t = new Matrix4();
        collisionObject.setWorldTransform(new Matrix4());

        logic = new BasicColliderPropertyLogic(this);
    }

    public btCollisionObject getCollisionObject() {
        return collisionObject;
    }

    public btCollisionShape getShape() {
        return shape;
    }

    @Override
    public BasicColliderPropertyLogic getLogic() {
        return logic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicColliderProperty that = (BasicColliderProperty) o;
        return Objects.equals(shape, that.shape) &&
                Objects.equals(collisionObject, that.collisionObject) &&
                Objects.equals(logic, that.logic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, collisionObject, logic);
    }
}
