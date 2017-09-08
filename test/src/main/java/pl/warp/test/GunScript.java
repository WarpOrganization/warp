package pl.warp.test;

import com.badlogic.gdx.math.Vector3;
import org.joml.Vector3f;
import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.common.transform.Transforms;
import pl.warp.engine.game.GameContext;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.scene.GameSceneComponent;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.updatescheduler.DelayScheduling;

/**
 * @author Hubertus
 *         Created 7/12/16
 */
@DelayScheduling(delayInMillis = 1000 / 57)
public class GunScript extends Script {

    private final Component owner;
    private int timer;
    private TransformProperty transformProperty;
    private PhysicalBodyProperty physicalProperty;
    private Component root;
    private Texture2DArray explosionSpritesheet;
    private Material bulletMaterial;
    private AudioManager audioManager;
    private GunProperty gunProperty;

    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private static final Vector3f RIGHT_VECTOR = new Vector3f(-1, 0, 0);
    private static final float BULLET_SPEED = 10f;
    private static final float BULLET_MASS = 0.01f;

    private Mesh bulletMesh;

    public GunScript(Component owner) {
        super(owner);
        this.owner = owner;
    }

    @Override
    public void onInit() {
        timer = 0;
        transformProperty = getOwner().getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
        physicalProperty = getOwner().getProperty(PhysicalBodyProperty.PHYSICAL_BODY_PROPERTY_NAME);
        gunProperty = getOwner().getProperty(GunProperty.GUN_PROPERTY_NAME);
        this.root = gunProperty.getRoot();
        this.bulletMesh = gunProperty.getBulletMesh();
        this.bulletMaterial = new Material(gunProperty.getBulletTexture());
        this.audioManager = gunProperty.getAudioManager();
        this.bulletMaterial.setBrightnessTexture(gunProperty.getBulletTexture());
        this.explosionSpritesheet = gunProperty.getExplosionSpritesheet();
    }

    @Override
    public void onUpdate(int delta) {
        input();
        cooldown(delta);
    }

    private void input() {
        if (gunProperty.isTriggered())
            shoot();
        else shot = false;
    }

    private void cooldown(int delta) {
        if (timer > 0) timer -= delta;
    }


    private Vector3f direction = new Vector3f();
    private Vector3f direction2 = new Vector3f();
    private Vector3f parentVelocity = new Vector3f();
    private Vector3f bulletTranslation = new Vector3f();
    private Vector3 bulletTranslation2 = new Vector3();
    private boolean shot = false;

    private void shoot() {
        if (timer <= 0) {
            AudioManager.INSTANCE.playSingle(owner, "gun");
            timer = gunProperty.getCooldownTime();
            createBullet(1);
            createBullet(-1);
        }

    }

    private void createBullet(int position) {
        Transforms.getAbsoluteRotation(getOwner()).transform(direction.set(FORWARD_VECTOR)).mul(2.0f);
        Transforms.getAbsoluteRotation(getOwner()).transform(direction2.set(RIGHT_VECTOR)).mul(7.5f);
        {
            bulletTranslation.set(direction);
            bulletTranslation.add(transformProperty.getTranslation());
            bulletTranslation.add(direction2.mul(position));
            direction.mul(BULLET_SPEED);
            parentVelocity.set(physicalProperty.getVelocity());
            direction.add(parentVelocity.mul(BULLET_MASS));

            GameComponent bullet = new GameSceneComponent((GameContext)getContext());
            bullet.addProperty(new BulletProperty((GameComponent) getOwner(), 10000));
            bullet.addProperty(new RenderableMeshProperty(bulletMesh));
            bullet.addProperty(new GraphicsMaterialProperty(bulletMaterial));
            TransformProperty transformProperty = new TransformProperty();
            transformProperty.setTranslation(new Vector3f(bulletTranslation));
            transformProperty.setRotation(Transforms.getAbsoluteRotation(owner));


            bullet.addProperty(transformProperty);
            PhysicalBodyProperty physicalBodyProperty = new PhysicalBodyProperty(BULLET_MASS, 0.1f, 0.1f, 0.1f);
            physicalBodyProperty.applyForce(direction);
            bullet.addProperty(physicalBodyProperty);
            bullet.addProperty(new ColliderProperty(new PointCollider(bullet, bulletTranslation2.set(bulletTranslation.x, bulletTranslation.y, bulletTranslation.z))));
            root.addChild(bullet);
            bullet.addScript(BulletScript.class);
        }
    }
}
