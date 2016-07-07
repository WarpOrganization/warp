package pl.warp.engine.physics.property;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.physics.property.logic.BasicColliderLogic;

import java.util.Objects;

/**
 * Created by hubertus on 7/3/16.
 */

public class BasicColliderProperty extends Property<Component> implements ColliderProperty {

    private final btCollisionShape shape;
    private btCollisionObject collisionObject;
    private Vector3f offset;

    private BasicColliderLogic logic;

    public BasicColliderProperty(Component owner, btCollisionShape shape, Vector3f offset) {
        super(owner, COLLIDER_PROPERTY_NAME);
        this.shape = shape;
        this.offset = offset;
        collisionObject = new btCollisionObject();
        collisionObject.setCollisionShape(shape);
        Matrix4 t = new Matrix4();
        collisionObject.setWorldTransform(new Matrix4());
        logic = new BasicColliderLogic(this);
    }

    public btCollisionObject getCollisionObject() {
        return collisionObject;
    }

    public btCollisionShape getShape() {
        return shape;
    }

    @Override
    public BasicColliderLogic getLogic() {
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

    public Vector3f getOffset() {
        return offset;
    }
}
