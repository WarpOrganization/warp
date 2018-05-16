package net.warpgame.test;

import net.warpgame.engine.audio.*;
import net.warpgame.engine.audio.playlist.PlayList;
import net.warpgame.engine.audio.playlist.PlayRandomPlayList;
import net.warpgame.engine.audio.playlist.SingleRunPlayList;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.common.transform.Transforms;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.execution.RapidExecutionStrategy;
import net.warpgame.engine.core.execution.SyncEngineThread;
import net.warpgame.engine.core.execution.SyncTimer;
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
import net.warpgame.test.console.*;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.util.Optional;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
public class Test1 {

    public static final Display DISPLAY = new Display(false, 1280, 720);
    private static BoundingBoxCalculator calc;
    private static ConsoleService consoleService;

    public static void main(String[] args) {
        System.out.println();
        EngineContext engineContext = new EngineContext("dev");
        GraphicsThread thread = engineContext.getLoadedContext()
                .findOne(GraphicsThread.class)
                .get();
        calc = engineContext.getLoadedContext()
                .findOne(BoundingBoxCalculator.class)
                .get();
        consoleService = engineContext.getLoadedContext()
                .findOne(ConsoleService.class)
                .get();
        setupScene(engineContext, thread);
        setupCamera(engineContext);
        setupAudio(engineContext);
        registerCommandsAndVariables(engineContext.getLoadedContext());
    }

    private static void setupAudio(EngineContext engineContext){


        AudioContext audioContext = new AudioContext();
        AudioManager.INSTANCE = new AudioManager(audioContext);
        audioContext.setAudioListener(
                new AudioListener(
                        engineContext.getLoadedContext().findOne(CameraHolder.class).get().getCamera().getCameraComponent()
                ));

        EngineThread audioThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        audioThread.scheduleTask(new AudioTask(audioContext));
        audioThread.scheduleTask(new AudioPosUpdateTask(audioContext));

        audioThread.scheduleOnce(() -> {
            AudioManager.INSTANCE.loadFiles("data" + File.separator + "sound" + File.separator + "effects");
            AudioManager.INSTANCE.loadFiles("data" + File.separator + "sound" + File.separator + "music");
            AudioManager.INSTANCE.play(new AudioSource(engineContext.getLoadedContext().findOne(CameraHolder.class).get().getCamera().getCameraComponent(), new Vector3f(0,0,0), true), "Stellardrone-Light_Years-05_In_Time");

        });




        audioThread.start();
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
        LightSource lightSource = new LightSource(new Vector3f(1.3f, 1.3f, 1.3f).mul(40));
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
            createSatellite(scene);
            Component lsource = new SceneComponent(scene);
            lsource.addProperty(new TransformProperty().move(new Vector3f(0, 0, 20)));
            createLight(lsource);
        });

        return ship;
    }

    private static void registerCommandsAndVariables(Context context) {
        SimpleCommand exit = new SimpleCommand("quit",
                Side.CLIENT,
                "Stops the engine and quits",
                "quit");
        exit.setExecutor((args) -> {
            context.findAll(EngineThread.class).forEach(EngineThread::interrupt);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
        consoleService.registerCommand(exit);

        CameraHolder ch = context.findOne(CameraHolder.class).get();
        consoleService.registerCommand(new MoveCameraCommand(ch, consoleService));


        consoleService.registerVariable(
                new CommandVariable("cameraPosX", ch.getCamera().getPosition(new Vector3f())));
    }

    private static void createSatellite(Scene scene) {
        SceneMesh mesh = ObjLoader.read(
                Test1.class.getResourceAsStream("satellite/satelita.obj"),
                true).toMesh();
        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("satellite/sat_tex.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D diffuse = new Texture2D(
                imageData.getWidth(),
                imageData.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                imageData.getData());
        Material material = new Material(diffuse);
        material.setShininess(0.05f);

        MeshProperty meshProperty = new MeshProperty(mesh);
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(new Vector3f(10f, 10f, -10f));
        MaterialProperty materialProperty = new MaterialProperty(material);

        Component sat = new SceneComponent(scene);
        sat.addProperty(meshProperty);
        sat.addProperty(transformProperty);
        sat.addProperty(materialProperty);
        sat.addScript(ConstantRotationScript.class);
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
                imageData.getWidth(),
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
                white.getWidth(),
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
        SceneLightManager sceneLightManager = scene.getContext().getLoadedContext().findOne(SceneLightManager.class).get();
        SceneMesh mugMesh = ObjLoader.read(
                Test1.class.getResourceAsStream("mug.obj"),
                true).toMesh();
        ImageData mugImageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("tex.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D mugDiffuse = new Texture2D(
                mugImageData.getWidth(),
                mugImageData.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                mugImageData.getData());
        Material mugMaterial = new Material(mugDiffuse);

        SceneMesh boundingBoxMesh = ObjLoader.read(
                Test1.class.getResourceAsStream("boundingbox.obj"),
                true).toMesh();
        ImageData boxImageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("palette.png"),
                PNGDecoder.Format.RGB
        );
        Texture2D boxDiffuse = new Texture2D(
                boxImageData.getWidth(),
                boxImageData.getHeight(),
                GL11.GL_RGB16,
                GL11.GL_RGB,
                true,
                boxImageData.getData());
        boxDiffuse.setParameter(GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        boxDiffuse.setParameter(GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        Material boxMaterial = new Material(boxDiffuse);
        boxMaterial.setShininess(1f);
        for(int x = 0; x < 2; x++){
            for(int y = 0; y < 2; y++) {
                for(int z = 0; z < 2; z++) {
                    MeshProperty mugMeshProperty = new MeshProperty(mugMesh);
                    MaterialProperty mugMaterialProperty = new MaterialProperty(mugMaterial);
                    TransformProperty mugTransformProperty = new TransformProperty();
                    mugTransformProperty.setTranslation(new Vector3f(x * 5, y * 5, z * 5));
                    mugTransformProperty.move(new Vector3f(0,0,-50));
                    mugTransformProperty.scale(new Vector3f(0.8f, 0.8f, 0.8f));
                    mugTransformProperty.rotate(x, y, z);

                    BoundingBox bb = calc.compute(mugMesh);
                    Component mugComponent = new SceneComponent(scene);
                    mugComponent.addScript(ConstantRotationScript.class);
                    mugComponent.addProperty(mugMeshProperty);
                    mugComponent.addProperty(mugMaterialProperty);
                    mugComponent.addProperty(mugTransformProperty);
                    mugComponent.addProperty(new BoundingBoxProperty(bb));

                    MeshProperty boxMeshProperty = new MeshProperty(boundingBoxMesh);
                    MaterialProperty boxMaterialProperty = new MaterialProperty(boxMaterial);
                    TransformProperty boxTransformProperty = new TransformProperty();
                    boxTransformProperty.setTranslation(new Vector3f((bb.max().x+bb.min().x)/2, (bb.max().y+bb.min().y)/2, (bb.max().z+bb.min().z)/2));
                    boxTransformProperty.scale(new Vector3f((bb.max().x-bb.min().x), (bb.max().y-bb.min().y), (bb.max().z-bb.min().z)));
                    Component boxComponent = new SceneComponent(mugComponent);
                    boxComponent.addProperty(boxMeshProperty);
                    boxComponent.addProperty(boxMaterialProperty);
                    boxComponent.addProperty(boxTransformProperty);

//                    LightSource lightSource = new LightSource(new Vector3f(1.3f, 0f, 0.5f).mul(20));
//                    LightSourceProperty lightSourceProperty = new LightSourceProperty(lightSource);
//                    boxComponent.addProperty(lightSourceProperty);
//                    sceneLightManager.addLight(lightSourceProperty); //CRASHES!!!!
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
                imageData.getWidth(),
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
                woodDiffuse.getWidth(),
                woodDiffuse.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                woodDiffuse.getData());

        Texture2D woodB = new Texture2D(
                woodBump.getWidth(),
                woodBump.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                woodBump.getData());

        Texture2D woodN = new Texture2D(
                woodNorm.getWidth(),
                woodNorm.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                woodNorm.getData());

        Texture2D stoneD = new Texture2D(
                stoneDiffuse.getWidth(),
                stoneDiffuse.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                stoneDiffuse.getData());

        Texture2D stoneB = new Texture2D(
                stoneBump.getWidth(),
                stoneBump.getHeight(),
                GL11.GL_RGBA16,
                GL11.GL_RGBA,
                true,
                stoneBump.getData());

        Texture2D stoneN = new Texture2D(
                stoneNorm.getWidth(),
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
        Camera camera = new QuaternionCamera(cameraComponent, projection);
        cameraHolder.setCamera(camera);
    }

    public static class ConstantRotationScript extends Script {

        @OwnerProperty(TransformProperty.NAME)
        TransformProperty transProp;

        public ConstantRotationScript(Component component) {
            super(component);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onUpdate(int delta) {
            transProp.rotate(0, delta*0.001f, 0);
        }
    }

}
