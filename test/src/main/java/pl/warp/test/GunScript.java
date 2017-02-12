package pl.warp.test;

import com.badlogic.gdx.math.Vector3;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.engine.graphics.particles.ParticleAnimator;
import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.SimpleParticleAnimator;
import pl.warp.engine.graphics.particles.dot.DotParticle;
import pl.warp.engine.graphics.particles.dot.DotParticleSystem;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.graphics.particles.dot.RandomSpreadingStageDotParticleFactory;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.script.GameScript;
import pl.warp.game.script.updatescheduler.DelayScheduling;

/**
 * @author Hubertus
 *         Created 7/12/16
 */
@DelayScheduling(delayInMillis = 1000 / 57)
public class GunScript extends GameScript<GameComponent> {

    private final Component owner;
    private int cooldown;
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

    public GunScript(GameComponent owner) {
        super(owner);
        this.owner = owner;
    }

    @Override
    public void init() {
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
    public void update(int delta) {
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
            Transforms.getAbsoluteRotation(getOwner()).transform(direction.set(FORWARD_VECTOR));
            Transforms.getAbsoluteRotation(getOwner()).transform(direction2.set(RIGHT_VECTOR));
            bulletTranslation.set(direction);
            bulletTranslation.mul(0.5f);
            bulletTranslation.add(transformProperty.getTranslation());
            bulletTranslation.add(direction2.mul(3.0f));
            direction.mul(BULLET_SPEED);
            parentVelocity.set(physicalProperty.getVelocity());
            direction.add(parentVelocity.mul(BULLET_MASS));

            GameComponent bullet = new GameSceneComponent(getContext());
            bullet.addProperty(new BulletProperty());
            bullet.addProperty(new RenderableMeshProperty(bulletMesh));
            bullet.addProperty(new GraphicsMaterialProperty(bulletMaterial));
            TransformProperty transformProperty = new TransformProperty();
            transformProperty.setTranslation(new Vector3f(bulletTranslation));

            if (getOwner() == TestSceneLoader.MAIN_GOAT) {
                Component particles = new GameSceneComponent(bullet);
                ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0, 0, 0), 0, 0);
                ParticleStage[] stages = {
                        new ParticleStage(1.0f, new Vector4f(0.5f, 2.0f, 2.5f, 0.5f)),
                        new ParticleStage(1.0f, new Vector4f(0.0f, 0.5f, 0.5f, 0.0f)),
                };

                ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(new Vector3f(.02f), 100, 0, true, true, stages);
                particles.addProperty(new ParticleEmitterProperty(new DotParticleSystem(animator, factory, 400)));
            }


            bullet.addProperty(transformProperty);
            PhysicalBodyProperty physicalBodyProperty = new PhysicalBodyProperty(BULLET_MASS, 0.1f, 0.1f, 0.1f);
            physicalBodyProperty.applyForce(direction);
            bullet.addProperty(physicalBodyProperty);
            bullet.addProperty(new ColliderProperty(new PointCollider(bullet, bulletTranslation2.set(bulletTranslation.x, bulletTranslation.y, bulletTranslation.z))));
            root.addChild(bullet);
            new BulletScript(bullet, 10000, explosionSpritesheet, getOwner());
        }

    }
}
