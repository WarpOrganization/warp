package net.warpgame.test;

import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.property.Transforms;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.graphics.GraphicsThread;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.material.MaterialProperty;
import net.warpgame.engine.graphics.mesh.shapes.SphereBuilder;
import net.warpgame.engine.graphics.mesh.MeshProperty;
import net.warpgame.engine.graphics.mesh.StaticMesh;
import net.warpgame.engine.graphics.rendering.screenspace.cubemap.CubemapProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSource;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSourceProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.SceneLightManager;
import net.warpgame.engine.graphics.resource.texture.ImageData;
import net.warpgame.engine.graphics.resource.texture.ImageDataArray;
import net.warpgame.engine.graphics.resource.texture.ImageDecoder;
import net.warpgame.engine.graphics.resource.texture.PNGDecoder;
import net.warpgame.engine.graphics.texture.Cubemap;
import net.warpgame.engine.graphics.texture.Texture2D;
import net.warpgame.engine.graphics.window.Display;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
public class MultiplayerTest {

    public static final Display DISPLAY = new Display(false, 1280, 720);


    public static void main(String[] args) {
        System.out.println();
        EngineContext engineContext = new EngineContext("dev", "fullPhysics");
        GraphicsThread thread = engineContext.getLoadedContext()
                .findOne(GraphicsThread.class)
                .get();
        setupScene(engineContext, thread);
    }

    private static void setupListeners(Component root, EngineContext context) {
        root.addListener(new ShipLoadListener(root, context.getLoadedContext().findOne(GraphicsThread.class).get()));
        CameraHolder cameraHolder = context.getLoadedContext().findOne(CameraHolder.class).get();
        root.addListener(new BoardShipListener(root, cameraHolder, DISPLAY, context.getComponentRegistry()));
    }

    private static void setupScene(EngineContext engineContext, GraphicsThread thread) {
        SceneHolder sceneHolder = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get();
        Scene scene = sceneHolder.getScene();
//        createModels(scene, thread);
        createLight(new SceneComponent(scene, 1000000010));
        createCubemap(scene, thread);
        setupListeners(scene, engineContext);
    }

    private static void createCubemap(Scene scene, GraphicsThread thread) {
        thread.scheduleOnce(() -> {
            ImageDataArray imageDataArray = ImageDecoder.decodeCubemap("net/warpgame/test/stars3", PNGDecoder.Format.RGBA);
            Cubemap cubemap = new Cubemap(imageDataArray.getWidth(), imageDataArray.getHeight(), imageDataArray.getData());
            CubemapProperty cubemapProperty = new CubemapProperty(cubemap);
            scene.addProperty(cubemapProperty);
        });
    }

    private static void createLight(Component component) {
        component.addProperty(new TransformProperty().move(new Vector3f(0, 0, 20)));
        SceneLightManager sceneLightManager = component.getContext()
                .getLoadedContext()
                .findOne(SceneLightManager.class)
                .get();
        LightSource lightSource = new LightSource(new Vector3f(1.3f, 1.3f, 1.3f).mul(20));
        LightSourceProperty lightSourceProperty = new LightSourceProperty(lightSource);
        component.addProperty(lightSourceProperty);
        sceneLightManager.addLight(lightSourceProperty);
    }

    private static void createModels(Scene scene, GraphicsThread graphicsThread) {
        graphicsThread.scheduleOnce(() -> {
            createSpheres(scene);
            Component lsource = new SceneComponent(scene);
            lsource.addProperty(new TransformProperty().move(new Vector3f(0, 0, 20)));
            createLight(lsource);
        });

    }

    private static ImageData white = ImageDecoder.decodePNG(
            Test1.class.getResourceAsStream("tex.png"),
            PNGDecoder.Format.RGB
    );

    private static ImageData woodDiffuse = ImageDecoder.decodePNG(
            Test1.class.getResourceAsStream("wood/wood-stack-1-DIFFUSE.png"),
            PNGDecoder.Format.RGBA
    );

    private static ImageData woodBump = ImageDecoder.decodePNG(
            Test1.class.getResourceAsStream("wood/wood-stack-1-DISP.png"),
            PNGDecoder.Format.RGBA
    );

    private static ImageData woodNorm = ImageDecoder.decodePNG(
            Test1.class.getResourceAsStream("wood/wood-stack-1-NORM.png"),
            PNGDecoder.Format.RGBA
    );

    private static ImageData stoneDiffuse = ImageDecoder.decodePNG(
            Test1.class.getResourceAsStream("stone/stone-redstone-2.png"),
            PNGDecoder.Format.RGBA
    );

    private static ImageData stoneBump = ImageDecoder.decodePNG(
            Test1.class.getResourceAsStream("stone/stone-redstone-2-DISP.png"),
            PNGDecoder.Format.RGBA
    );

    private static ImageData stoneNorm = ImageDecoder.decodePNG(
            Test1.class.getResourceAsStream("stone/stone-redstone-2-NORM.png"),
            PNGDecoder.Format.RGBA
    );

    private static void createSpheres(Component scene) {


        Texture2D woodD = new Texture2D(
                woodDiffuse.getHeight(),
                woodDiffuse.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                woodDiffuse.getData());

        Texture2D woodB = new Texture2D(
                woodBump.getHeight(),
                woodBump.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                woodBump.getData());

        Texture2D woodN = new Texture2D(
                woodNorm.getHeight(),
                woodNorm.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                woodNorm.getData());

        Texture2D stoneD = new Texture2D(
                stoneDiffuse.getHeight(),
                stoneDiffuse.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                stoneDiffuse.getData());

        Texture2D stoneB = new Texture2D(
                stoneBump.getHeight(),
                stoneBump.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                stoneBump.getData());

        Texture2D stoneN = new Texture2D(
                stoneNorm.getHeight(),
                stoneNorm.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                stoneNorm.getData());


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                createSphere(scene,
                        new Vector3f(i * 15, j * 15, 0),
                        ((i + j) % 2 == 0) ? woodD : stoneD,
                        ((i + j) % 2 == 0) ? woodB : stoneB,
                        ((i + j) % 2 == 0) ? woodN : stoneN,
                        0.15f
                );
            }
        }

        Texture2D whiteD = new Texture2D(
                white.getWidth(),
                white.getHeight(),
                GL11.GL_RGB16,
                GL11.GL_RGB,
                true,
                white.getData());


        Component a = new SceneComponent(scene);
        a.addProperty(new TransformProperty().rotateY((float) (Math.PI / 2.0f)).move(new Vector3f(20, 0, 20)));
        Component b = new SceneComponent(scene);
        b.addProperty(new TransformProperty().rotateX((float) (Math.PI / 2.0f)).move(new Vector3f(0, 0, 20)));
        Component sphere = createSphere(b, new Vector3f(0, 0, 40), whiteD, null, null, 0.0f);
        Vector3f t = Transforms.getAbsolutePosition(sphere, new Vector3f());
        //t.add(0, -3, 0);
    }

    private static Component createSphere(Component scene, Vector3f trans, Texture2D diffuse, Texture2D bump, Texture2D normal, float roughness) {
        Component sphere = new SceneComponent(scene);

        StaticMesh mesh = SphereBuilder.createShape(20, 20, 4);
        MeshProperty meshProperty = new MeshProperty(mesh);
        sphere.addProperty(meshProperty);

        Material material = new Material(diffuse);
        if (bump != null) material.setDisplacement(bump, 0.5f);
        material.setNormalMap(normal);
        material.setRoughness(roughness);
        MaterialProperty materialProperty = new MaterialProperty(material);
        sphere.addProperty(materialProperty);

        TransformProperty property = new TransformProperty();
        sphere.addProperty(property);
        property.move(trans);
        return sphere;
    }

}
