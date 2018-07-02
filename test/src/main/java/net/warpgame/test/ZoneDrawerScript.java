package net.warpgame.test;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.graphics.GraphicsThread;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.material.MaterialProperty;
import net.warpgame.engine.graphics.mesh.MeshProperty;
import net.warpgame.engine.graphics.mesh.StaticMesh;
import net.warpgame.engine.graphics.resource.mesh.ObjLoader;
import net.warpgame.engine.graphics.resource.texture.ImageData;
import net.warpgame.engine.graphics.resource.texture.ImageDecoder;
import net.warpgame.engine.graphics.resource.texture.PNGDecoder;
import net.warpgame.engine.graphics.texture.Texture2D;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.function.Supplier;

public class ZoneDrawerScript extends Script {

    @OwnerProperty(@IdOf(Test1.Sup.class))
    private Test1.Sup sup;

    private Component[] rings = new Component[3];
    private TransformProperty[] transformProperties = new TransformProperty[3];

    public ZoneDrawerScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        getOwner().getContext().getLoadedContext().findOne(GraphicsThread.class).get().scheduleOnce(() ->
        {
            StaticMesh ringMesh = ObjLoader.read(
                    Test1.class.getResourceAsStream("kube.obj"),
                    true).toMesh();

            ImageData imageData = ImageDecoder.decodePNG(
                    Test1.class.getResourceAsStream("bullet.png"),
                    PNGDecoder.Format.RGBA
            );
            Texture2D diffuse = new Texture2D(
                    imageData.getWidth(),
                    imageData.getHeight(),
                    GL11.GL_RGBA16,
                    GL11.GL_RGBA,
                    true,
                    imageData.getData());
            Material ringMaterial = new Material(diffuse);
            for (int i = 0; i < 3; i++) {
                rings[i] = new SceneComponent(getOwner());
                MeshProperty ringMeshProperty = new MeshProperty(ringMesh);
                MaterialProperty ringMaterailProperty = new MaterialProperty(ringMaterial);
                TransformProperty ringTransformProperty = new TransformProperty();

                rings[i].addProperty(ringMeshProperty);
                rings[i].addProperty(ringMaterailProperty);
                rings[i].addProperty(ringTransformProperty);
            }

            for (int i = 0; i < 3; i++) {
                transformProperties[i] = rings[i].getProperty(Property.getTypeId(TransformProperty.class));
            }

            transformProperties[1].rotateX((float) Math.PI);
            transformProperties[2].rotateZ((float) Math.PI);
        });
    }

    @Override
    public void onUpdate(int delta) {
        for (TransformProperty transformProperty : transformProperties) {
            transformProperty.setScale(new Vector3f(sup.supplier.get()));
        }
    }
}
