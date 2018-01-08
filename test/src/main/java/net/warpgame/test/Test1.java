package net.warpgame.test;

import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.common.transform.Transforms;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.graphics.GraphicsThread;
import net.warpgame.engine.graphics.camera.Camera;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.camera.QuaternionCamera;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.material.MaterialProperty;
import net.warpgame.engine.graphics.mesh.shapes.QuadMesh;
import net.warpgame.engine.graphics.mesh.shapes.SphereBuilder;
import net.warpgame.engine.graphics.rendering.culling.BoundingBox;
import net.warpgame.engine.graphics.rendering.culling.BoundingBoxCalculator;
import net.warpgame.engine.graphics.rendering.culling.BoundingBoxProperty;
import net.warpgame.engine.graphics.rendering.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.rendering.scene.mesh.SceneMesh;
import net.warpgame.engine.graphics.rendering.scene.tesselation.SceneTessellationMode;
import net.warpgame.engine.graphics.rendering.scene.tesselation.TessellationModeProperty;
import net.warpgame.engine.graphics.rendering.screenspace.cubemap.CubemapProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSource;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSourceProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.SceneLightManager;
import net.warpgame.engine.graphics.resource.mesh.ObjLoader;
import net.warpgame.engine.graphics.resource.texture.ImageData;
import net.warpgame.engine.graphics.resource.texture.ImageDataArray;
import net.warpgame.engine.graphics.resource.texture.ImageDecoder;
import net.warpgame.engine.graphics.resource.texture.PNGDecoder;
import net.warpgame.engine.graphics.texture.Cubemap;
import net.warpgame.engine.graphics.texture.Texture2D;
import net.warpgame.engine.graphics.utility.projection.PerspectiveMatrix;
import net.warpgame.engine.graphics.window.Display;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
public class Test1 {

    public static final Display DISPLAY = new Display(false, 1280, 720);
    private static BoundingBoxCalculator calc;

    public static void main(String[] args) {
        System.out.println();
        EngineContext engineContext = new EngineContext("dev");
        GraphicsThread thread = engineContext.getLoadedContext()
                .findOne(GraphicsThread.class)
                .get();
        calc = engineContext.getLoadedContext()
                .findOne(BoundingBoxCalculator.class)
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
        createCubemap(scene, thread);
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
            createMugs(scene);
            createCastle(scene);
            createFloor(scene);
            Component lsource = new SceneComponent(scene);
            lsource.addProperty(new TransformProperty().move(new Vector3f(0, 0, 20)));
            createLight(lsource);
        });

        return ship;
    }

    private static void createCastle(Scene scene) {
        SceneMesh mesh = ObjLoader.read(
                Test1.class.getResourceAsStream("castle2.obj"),
                true).toMesh();


        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("castle_tex.png"),
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
        MeshProperty meshProperty = new MeshProperty(mesh);
        MaterialProperty materialProperty = new MaterialProperty(material);
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(new Vector3f(0,-30,0));
        Component component = new SceneComponent(scene);
        component.addProperty(meshProperty);
        component.addProperty(materialProperty);
        component.addProperty(transformProperty);
    }

    private static void createFloor(Scene scene) {
        SceneMesh quadMesh = new QuadMesh();
        Texture2D diffuse = new Texture2D(
                white.getHeight(),
                white.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                white.getData());
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.rotateX((float) -(Math.PI / 2));
        transformProperty.scale(new Vector3f(100f));
        transformProperty.move(new Vector3f(0,-30,0));
        Material material = new Material(diffuse);
        MaterialProperty materialProperty = new MaterialProperty(material);
        MeshProperty meshProperty = new MeshProperty(quadMesh);
        Component component = new SceneComponent(scene);
        component.addProperty(meshProperty);
        component.addProperty(materialProperty);
        component.addProperty(transformProperty);

    }

    private static void createMugs(Scene scene) {
        SceneMesh mugMesh = ObjLoader.read(
                Test1.class.getResourceAsStream("mug.obj"),
                true).toMesh();
        ImageData mugImageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("he-goat_tex.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D mugDiffuse = new Texture2D(
                mugImageData.getHeight(),
                mugImageData.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                mugImageData.getData());
        Material mugMaterial = new Material(mugDiffuse);

        BoundingBox boundingBoxMesh = calc.compute(mugMesh);
        ImageData boxImageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("bounding-box.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D boxDiffuse = new Texture2D(
                boxImageData.getHeight(),
                boxImageData.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                boxImageData.getData());
        Material boxMaterial = new Material(boxDiffuse);
        for(int x = 0; x < 1; x++){
            for(int y = 0; y < 1; y++) {
                for(int z = 0; z < 1; z++) {
                    MeshProperty mugMeshProperty = new MeshProperty(mugMesh);
                    MaterialProperty mugMaterialProperty = new MaterialProperty(mugMaterial);
                    TransformProperty mugTransformProperty = new TransformProperty();
                    mugTransformProperty.setTranslation(new Vector3f(x * 5, y * 5, z * 5));
                    mugTransformProperty.move(new Vector3f(0,0,-50));
                    mugTransformProperty.scale(new Vector3f(0.8f, 0.8f, 0.8f));
                    mugTransformProperty.rotate(x, y, z);

                    Component mugComponent = new SceneComponent(scene);
                    mugComponent.addScript(AsdfScript.class);
                    mugComponent.addProperty(mugMeshProperty);
                    mugComponent.addProperty(mugMaterialProperty);
                    mugComponent.addProperty(mugTransformProperty);
                    mugComponent.addProperty(new BoundingBoxProperty(boundingBoxMesh));

                    MeshProperty boxMeshProperty = new MeshProperty(boundingBoxMesh);
                    MaterialProperty boxMaterialProperty = new MaterialProperty(boxMaterial);
                    TransformProperty boxTransformProperty = new TransformProperty();

                    Component boxComponent = new SceneComponent(mugComponent);
                    mugComponent.addProperty(boxMeshProperty);
                    mugComponent.addProperty(boxMaterialProperty);
                    mugComponent.addProperty(boxTransformProperty);
                }
            }
        }
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
        createSphere(scene, t.negate(), stoneD, null, null, 0.3f).addScript(S.class);
    }

    public static class S extends Script {

        @OwnerProperty(TransformProperty.NAME)
        private TransformProperty p;

        public S(Component owner) {
            super(owner);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onUpdate(int delta) {
           p.rotateY(delta  / 1000f);
        }
    }

    private static Component createSphere(Component scene, Vector3f trans, Texture2D diffuse, Texture2D bump, Texture2D normal, float roughness) {
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
        sphere.addProperty(new TessellationModeProperty(SceneTessellationMode.BEZIER));

        TransformProperty property = new TransformProperty();
        sphere.addProperty(property);
        property.move(trans);
        return sphere;
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

    public static class AsdfScript extends Script {

        @OwnerProperty(TransformProperty.NAME)
        TransformProperty transProp;

        public AsdfScript(Component component) {
            super(component);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onUpdate(int delta) {
            transProp.rotate(0, 0, delta*0.01f);
        }
    }

}