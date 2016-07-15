package pl.warp.game;

import com.badlogic.gdx.math.Vector3;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.SimpleComponent;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.input.GLFWInput;
import pl.warp.engine.graphics.math.Transforms;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/12/16.
 */
public class GunScript extends Script<Component> {

    private int cooldown;
    private final GLFWInput input;
    private int timer;
    private TransformProperty transformProperty;
    private PhysicalBodyProperty physicalProperty;
    private Component root;

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private static final float BULLET_SPEED = 1f;
    private static final float BULLET_MASS = 0.001f;

    private Mesh bulletMesh;


    public GunScript(Component owner, int cooldown, GLFWInput input, Component root, Mesh bulletMesh) {
        super(owner);
        this.cooldown = cooldown;
        this.input = input;
        this.root = root;
        this.bulletMesh = bulletMesh;
    }

    @Override
    public void onInit() {
        timer = 0;
        transformProperty = getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        physicalProperty = getOwner().getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);

    }

    @Override
    public void onUpdate(int delta) {
        input();
        cooldown(delta);
    }

    private void input() {
        if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
            shoot();
    }

    private void cooldown(int delta) {
        if (timer > 0) timer -= delta;
    }


    private Vector3f direction = new Vector3f();
    private Vector3f parentVelocity = new Vector3f();
    private Vector3f bulletTranslation = new Vector3f();
    private Vector3 bulletTranslation2 = new Vector3();

    private void shoot() {
        if (timer <= 0) {
            timer = cooldown;
            Transforms.getActualRotation(getOwner()).transform(direction.set(FORWARD_VECTOR));
            bulletTranslation.set(direction);
            bulletTranslation.mul(3.5f);
            bulletTranslation.add(transformProperty.getTranslation());
            direction.mul(BULLET_SPEED);
            parentVelocity.set(physicalProperty.getVelocity());
            direction.add(parentVelocity.mul(BULLET_MASS));

            Component bullet = new SimpleComponent(getContext());
            new GraphicsMeshProperty(bullet, bulletMesh);
            new TransformProperty(bullet).setTranslation(new Vector3f(bulletTranslation));
            new PhysicalBodyProperty(bullet, BULLET_MASS, 0.128f).applyForce(direction);
            new ColliderProperty(bullet, new PointCollider(bullet, bulletTranslation2.set(bulletTranslation.x,bulletTranslation.y,bulletTranslation.z)));
            root.addChild(bullet);
            new BulletScript(bullet,1000);
        }

    }
}
