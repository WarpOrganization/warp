package pl.warp.test;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.component.Scene;
import pl.warp.engine.core.component.SceneComponent;
import pl.warp.engine.core.component.SceneHolder;
import pl.warp.engine.core.context.EngineContext;
import pl.warp.engine.graphics.GraphicsThread;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.CameraHolder;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.material.MaterialProperty;
import pl.warp.engine.graphics.mesh.shapes.SphereBuilder;
import pl.warp.engine.graphics.rendering.scene.mesh.MeshProperty;
import pl.warp.engine.graphics.rendering.scene.mesh.SceneMesh;
import pl.warp.engine.graphics.rendering.screenspace.light.LightSource;
import pl.warp.engine.graphics.rendering.screenspace.light.LightSourceProperty;
import pl.warp.engine.graphics.rendering.screenspace.light.SceneLightManager;
import pl.warp.engine.graphics.resource.mesh.ObjLoader;
import pl.warp.engine.graphics.resource.texture.ImageData;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.utility.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.window.Display;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
public class Test1 {

    public static final Display DISPLAY = new Display(false, 1280, 920);


    public static void main(String[] args) {
        System.out.println();
        EngineContext engineContext = new EngineContext();
        GraphicsThread thread = engineContext.getLoadedContext()
                .findOne(GraphicsThread.class)
                .get();
        setupScene(engineContext, thread);
        setupCamera(engineContext);
    }

    private static void setupScene(EngineContext engineContext, GraphicsThread thread) {
        SceneHolder sceneHolder = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get();
        Scene scene = sceneHolder.getScene();
        createModels(scene, thread);
    }

    private static void createLight(Component component) {
        SceneLightManager sceneLightManager = component.getContext()
                .getLoadedContext()
                .findOne(SceneLightManager.class)
                .get();
        LightSource lightSource = new LightSource(new Vector3f(1.3f, 1.3f, 1.3f).mul(20));
        LightSourceProperty lightSourceProperty = new LightSourceProperty(lightSource);
        component.addProperty(lightSourceProperty);
        sceneLightManager.addLight(lightSourceProperty);
    }

    private static Component createModels(Scene scene, GraphicsThread graphicsThread) {
        Component ship = new SceneComponent(scene);
        graphicsThread.scheduleOnce(() -> {
            createShip(ship);
            createSpheres(scene);
            Component lsource = new SceneComponent(scene);
            lsource.addProperty(new TransformProperty().move(new Vector3f(0, 0, 20)));
            createLight(lsource);
        });

        return ship;
    }


    private static void createShip(Component ship) {
        SceneMesh mesh = ObjLoader.read(
                Test1.class.getResourceAsStream("pistol.obj"),
                true).toMesh();
        MeshProperty meshProperty = new MeshProperty(mesh);
        ship.addProperty(meshProperty);

        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("fighter_1.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D diffuse = new Texture2D(
                imageData.getHeight(),
                imageData.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                imageData.getData());
        Material material = new Material(diffuse);
        MaterialProperty materialProperty = new MaterialProperty(material);
        ship.addProperty(materialProperty);

        TransformProperty property = new TransformProperty();
        ship.addProperty(property);
        property.move(new Vector3f(0, 0, -10f));
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
                white.getHeight(),
                white.getHeight(),
                GL11.GL_RGB16,
                GL11.GL_RGB,
                true,
                white.getData());

        createSphere(scene, new Vector3f(0, 0, 40), whiteD, null, null, 0.0f);
        createSphere(scene, new Vector3f(12, 0, 40), whiteD, null, null, 0.3f);
        createSphere(scene, new Vector3f(24, 0, 40), whiteD, null, null, 1.0f);
    }

    private static void createSphere(Component scene, Vector3f trans, Texture2D diffuse, Texture2D bump, Texture2D normal, float roughness) {
        Component sphere = new SceneComponent(scene);

        SceneMesh mesh = SphereBuilder.createShape(20, 20, 4);
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
    }

    private static void setupCamera(EngineContext engineContext) {
        Scene scene = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get()
                .getScene();
        Component cameraComponent = new SceneComponent(scene);
        cameraComponent.addProperty(new TransformProperty().move(new Vector3f(-10, -20, 60))
                .rotate((float) (Math.PI / 4), -(float) (Math.PI / 4), (float) 0));
        cameraComponent.addScript(SimpleControlScript.class);

        CameraHolder cameraHolder = engineContext.getLoadedContext()
                .findOne(CameraHolder.class)
                .get();
        PerspectiveMatrix projection = new PerspectiveMatrix(
                55f,
                0.1f,
                10000f,
                DISPLAY.getWidth(),
                DISPLAY.getHeight()
        );
        Camera camera = new QuaternionCamera(cameraComponent, new TransformProperty(), projection);
        cameraHolder.setCamera(camera);
    }
}
