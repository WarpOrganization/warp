package pl.warp.test;

import com.badlogic.gdx.math.Vector3;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.properties.Transforms;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.physics.collider.PointCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.script.GameScript;
import pl.warp.game.script.OwnerProperty;

/**
 * @author Hubertus
 *         Created 03.03.17
 */
public class TankGunScript extends GameScript<GameComponent> {

    private final int reloadTime;
    private Component root;
    @OwnerProperty(name = GunProperty.GUN_PROPERTY_NAME)
    GunProperty gunProperty;
    private static final Vector3f FORWARD_VECTOR = new Vector3f(0, 0, -1);
    private static final Vector3f GUN_OFFSET = new Vector3f(0, 10, -10);
    private Mesh mesh;
    private Material material;

    private int reloadLeft = 0;

    public TankGunScript(GameComponent owner, int reloadTime, Component root) {
        super(owner);
        this.reloadTime = reloadTime;
        this.root = root;
    }

    @Override
    protected void init() {
        this.mesh = gunProperty.getBulletMesh();
        this.material = new Material(gunProperty.getBulletTexture());
    }

    @Override
    protected void update(int delta) {
        reload(delta);
        input();
    }

    private void reload(int delta) {
        if (reloadLeft > 0) reloadLeft -= delta;
    }

    private void input() {
        if (gunProperty.isTriggered()) shoot();
    }

    private Vector3f forwardVector = new Vector3f();
    private Vector3f translation = new Vector3f();
    private Vector3f velUpComponent = new Vector3f(0, 70, 0);
    private Vector3f gunOffset = new Vector3f(0, 10, -10);
    private Vector3 roundTranslation = new Vector3();

    private void shoot() {
        if (reloadLeft <= 0) {
            reloadLeft = reloadTime;
            Quaternionf rotation = Transforms.getAbsoluteRotation(getOwner());
            rotation.transform(FORWARD_VECTOR, forwardVector);
            rotation.transform(GUN_OFFSET, gunOffset);
            Transforms.getAbsolutePosition(getOwner(), translation);
            translation.add(gunOffset);
            GameComponent round = new GameSceneComponent(getContext());
            TransformProperty transformProperty = new TransformProperty();
            transformProperty.move(translation);
            round.addProperty(transformProperty);
            forwardVector.mul(100);
            forwardVector.add(velUpComponent);
            PhysicalBodyProperty bodyProperty = new PhysicalBodyProperty(1, 1, 1, 1);
            bodyProperty.applyForce(forwardVector);
            round.addProperty(bodyProperty);
            round.addProperty(new ColliderProperty(new PointCollider(round, roundTranslation.set(translation.x, translation.y, translation.z))));
            round.addProperty(new GravityProperty(new Vector3f(0, -1, 0)));
            round.addProperty(new RenderableMeshProperty(mesh));
            round.addProperty(new GraphicsMaterialProperty(material));
            root.addChild(round);
            new TankRoundScript(round);
        }
    }
}
