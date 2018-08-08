package net.warpgame.test;

import net.warpgame.content.LoadShipEvent;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.core.execution.EngineThread;
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
import net.warpgame.engine.physics.simplified.SimplifiedPhysicsProperty;
import org.lwjgl.opengl.GL11;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ShipLoadListener extends Listener<LoadShipEvent> {

    private final EngineThread graphicsThread;
    private StaticMesh mesh;
    private ImageData imageData;
    private Texture2D diffuse;
    Material material;

    protected ShipLoadListener(Component owner, EngineThread graphicsThread) {
        super(owner, Event.getTypeId(LoadShipEvent.class));
        this.graphicsThread = graphicsThread;
        graphicsThread.scheduleOnce(this::init);
    }

    private void init() {
        mesh = ObjLoader.read(
                Test1.class.getResourceAsStream("he-goat.obj"),
                true).toMesh();
        imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("he-goat_tex.png"),
                PNGDecoder.Format.RGBA
        );
        diffuse = new Texture2D(
                imageData.getHeight(),
                imageData.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                imageData.getData());
        material = new Material(diffuse);
    }

    @Override
    public void handle(LoadShipEvent event) {
        Component ship = new SceneComponent(getOwner(), event.getShipComponentId());
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(event.getPos());
        ship.addProperty(transformProperty);
        ship.addProperty(new SimplifiedPhysicsProperty(10f));
        ship.addProperty(new MeshProperty(mesh));
        ship.addProperty(new MaterialProperty(material));
    }
}
