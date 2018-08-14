package net.warpgame.servertest.client;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.material.MaterialProperty;
import net.warpgame.engine.graphics.mesh.MeshProperty;
import net.warpgame.engine.graphics.mesh.StaticMesh;
import net.warpgame.engine.graphics.resource.mesh.ObjLoader;
import net.warpgame.engine.graphics.resource.texture.ImageData;
import net.warpgame.engine.graphics.resource.texture.ImageDecoder;
import net.warpgame.engine.graphics.resource.texture.PNGDecoder;
import net.warpgame.engine.graphics.texture.Texture2D;
import net.warpgame.engine.net.NetComponentRegistry;
import net.warpgame.engine.physics.RigidBodyConstructor;
import net.warpgame.engine.physics.simplified.SimplifiedPhysicsProperty;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

/**
 * @author Hubertus
 * Created 14.08.2018
 */
@Service
@Profile("client")
public class BulletCreator {

    private NetComponentRegistry componentRegistry;
    private StaticMesh mesh;
    private Material material;
    private RigidBodyConstructor rigidBodyConstructor;

    public BulletCreator(NetComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    public void initialize() {
        mesh = ObjLoader.read(
                ClientTest.class.getResourceAsStream("bullet.obj"),
                true).toMesh();
        ImageData imageData = ImageDecoder.decodePNG(
                ClientTest.class.getResourceAsStream("bullet.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D diffuse = new Texture2D(
                imageData.getHeight(),
                imageData.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                imageData.getData());
        material = new Material(diffuse);
    }


    public void create(Component gun, int bulletId) {
        SceneComponent bullet = new SceneComponent(componentRegistry.getRootComponent(), bulletId);
        setup(bullet, gun);
    }

    public Component create(Component gun) {
        SceneComponent bullet = componentRegistry.createPublicComponent(componentRegistry.getRootComponent());
        setup(bullet, gun);

        return bullet;
    }

    private void setup(Component bullet, Component gun) {
        TransformProperty transformProperty = new TransformProperty();
        TransformProperty ownerTransformProperty = gun.getProperty(Property.getTypeId(TransformProperty.class));

        Vector3f forwardVector = new Vector3f(-2, 0, 0);
        forwardVector.rotate(transformProperty.getRotation());
        transformProperty.move(ownerTransformProperty.getTranslation(new Vector3f()).add(forwardVector));
        transformProperty.setRotation(ownerTransformProperty.getRotation());
        bullet.addProperty(transformProperty);

        SimplifiedPhysicsProperty physicsProperty = new SimplifiedPhysicsProperty(0.1f);
        SimplifiedPhysicsProperty ownerPhysicsProperty = gun.getProperty(Property.getTypeId(SimplifiedPhysicsProperty.class));
        physicsProperty.setVelocity(new Vector3f(-2).add(ownerPhysicsProperty.getVelocity()));
        bullet.addProperty(physicsProperty);

        bullet.addProperty(new MeshProperty(mesh));
        bullet.addProperty(new MaterialProperty(material));
        bullet.addListener(new BulletDeathListener(bullet));
    }
}
