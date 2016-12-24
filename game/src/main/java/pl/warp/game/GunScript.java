package pl.warp.game;

import com.badlogic.gdx.math.Vector3;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.SimpleComponent;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.input.GLFWInput;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

/**
 * Created by hubertus on 7/12/16.
 */
public class GunScript extends Script<Component> {

    private final Component owner;
    private int cooldown;
    private final GLFWInput input;
    private int timer;
    private TransformProperty transformProperty;
    private PhysicalBodyProperty physicalProperty;
    private Component root;
    private Texture2DArray explosionSpritesheet;
    private Material bulletMaterial;
    private final AudioManager audioManager;
    private Component playerShip;

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private static final float BULLET_SPEED = 1f;
    private static final float BULLET_MASS = 0.01f;

    private Mesh bulletMesh;


    public GunScript(Component owner, int cooldown, GLFWInput input, Component root, Mesh bulletMesh, Texture2DArray explosionSpritesheet, Texture2D bulletTexture, Component playerShip, AudioManager audioManager) {
        super(owner);
        this.owner = owner;
        this.cooldown = cooldown;
        this.input = input;
        this.root = root;
        this.bulletMesh = bulletMesh;
        this.bulletMaterial = new Material(bulletTexture);
        this.audioManager = audioManager;
        this.bulletMaterial.setBrightnessTexture(bulletTexture);
        this.explosionSpritesheet = explosionSpritesheet;
        this.playerShip = playerShip;
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
        else shot = false;
    }

    private void cooldown(int delta) {
        if (timer > 0) timer -= delta;
    }


    private Vector3f direction = new Vector3f();
    private Vector3f parentVelocity = new Vector3f();
    private Vector3f bulletTranslation = new Vector3f();
    private Vector3 bulletTranslation2 = new Vector3();
    private boolean shot = false;

    private void shoot() {
        if (!shot) {
            audioManager.playSingle(owner, "gun");
            shot = true;
        }
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
            new GraphicsMaterialProperty(bullet, bulletMaterial);
            new TransformProperty(bullet).setTranslation(new Vector3f(bulletTranslation));
            new PhysicalBodyProperty(bullet, BULLET_MASS, 0.1f, 0.1f, 0.1f).applyForce(direction);
            new ColliderProperty(bullet, new PointCollider(bullet, bulletTranslation2.set(bulletTranslation.x, bulletTranslation.y, bulletTranslation.z)));
            root.addChild(bullet);
            new BulletScript(bullet, 10000, explosionSpritesheet, playerShip);
        }

    }
}
