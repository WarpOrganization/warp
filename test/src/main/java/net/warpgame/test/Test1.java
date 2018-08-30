package net.warpgame.test;

import net.warpgame.engine.audio.AudioClip;
import net.warpgame.engine.audio.AudioListenerProperty;
import net.warpgame.engine.audio.AudioSourceProperty;
import net.warpgame.engine.console.ConsoleService;
import net.warpgame.engine.console.command.CommandVariable;
import net.warpgame.engine.console.command.SimpleCommand;
import net.warpgame.engine.core.component.*;
import net.warpgame.engine.core.context.Context;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.runtime.EngineRuntime;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.graphics.GraphicsThread;
import net.warpgame.engine.graphics.animation.*;
import net.warpgame.engine.graphics.animation.colladaloader.ColladaLoader;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.AnimatedModelData;
import net.warpgame.engine.graphics.animation.colladaloader.datastructures.AnimationData;
import net.warpgame.engine.graphics.animation.dataloader.AnimatedModelLoader;
import net.warpgame.engine.graphics.animation.dataloader.AnimationLoader;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.camera.CameraProperty;
import net.warpgame.engine.graphics.material.Material;
import net.warpgame.engine.graphics.material.MaterialProperty;
import net.warpgame.engine.graphics.mesh.MeshProperty;
import net.warpgame.engine.graphics.mesh.StaticMesh;
import net.warpgame.engine.graphics.mesh.shapes.PlainMesh;
import net.warpgame.engine.graphics.mesh.shapes.SphereBuilder;
import net.warpgame.engine.graphics.rendering.culling.BoundingBox;
import net.warpgame.engine.graphics.rendering.culling.BoundingBoxCalculator;
import net.warpgame.engine.graphics.rendering.culling.BoundingBoxProperty;
import net.warpgame.engine.graphics.rendering.screenspace.cubemap.CubemapProperty;
import net.warpgame.engine.graphics.rendering.screenspace.light.LightSourceProperty;
import net.warpgame.engine.graphics.rendering.ui.CanvasProperty;
import net.warpgame.engine.graphics.rendering.ui.image.ImageProperty;
import net.warpgame.engine.graphics.rendering.ui.RectTransformProperty;
import net.warpgame.engine.graphics.resource.mesh.ObjLoader;
import net.warpgame.engine.graphics.resource.texture.ImageData;
import net.warpgame.engine.graphics.resource.texture.ImageDataArray;
import net.warpgame.engine.graphics.resource.texture.ImageDecoder;
import net.warpgame.engine.graphics.resource.texture.PNGDecoder;
import net.warpgame.engine.graphics.texture.Cubemap;
import net.warpgame.engine.graphics.texture.Texture2D;
import net.warpgame.engine.graphics.window.Display;
import net.warpgame.engine.graphics.window.WindowManager;
import net.warpgame.test.command.MoveCameraCommand;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Jaca777
 * Created 2017-09-23 at 13
 */
public class Test1 {

    public static final Display DISPLAY = new Display(false, 1280, 720);
    private static BoundingBoxCalculator calc;
    private static ConsoleService consoleService;

    public static void start(EngineRuntime engineRuntime) {
        System.out.println();
        EngineContext engineContext = new EngineContext("dev", "client", "graphics", "sound");
        engineContext.getLoadedContext().addService(engineRuntime.getIdRegistry());
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
        setupGui(engineContext, thread);
        setupCamera(engineContext);
        createAudioSources(engineContext, thread);
        registerCommandsAndVariables(engineContext.getLoadedContext());
        thread.scheduleOnce(() -> createShip(engineContext.getScene()));
    }

    public static class Sup extends Property {
        public Supplier<Float> supplier;

        public Sup(Supplier<Float> supplier) {
            this.supplier = supplier;
        }

    }

    private static void createAudioSources(EngineContext context, GraphicsThread thread) {
        try {
            Component component = new SceneComponent(context);
            AudioSourceProperty property = new AudioSourceProperty();
            AudioClip audioClip = new AudioClip(getResourcePath("sound/szum.ogg"));
            property.setAudioClip(audioClip).setLooping(true).setPlayOnStartup(true);
            component.addProperty(property);
            component.addProperty(new TransformProperty());
            //component.addScript(Sc.class);
            //component.addProperty(new Sup(() -> 5f));

            //component.addScript(ZoneDrawerScript.class);
        } catch (Exception e) {
            System.out.println("Failed to init Audio module");
            e.printStackTrace();
        }
    }

    private static String getResourcePath(String resource) throws URISyntaxException {
        return Paths.get(Test1.class.getResource(resource).toURI()).toFile().getAbsolutePath();
    }

    private static void setupGui(EngineContext context, GraphicsThread thread) {

        thread.scheduleOnce(() -> {
            Component component = new SceneComponent(context);
            component.addProperty(new CanvasProperty(context.getLoadedContext().findOne(CameraHolder.class).get().getCameraProperty()));
            component.addProperty(new RectTransformProperty(DISPLAY.getWidth(), DISPLAY.getHeight()));

            context.getLoadedContext()
                    .findOne(UiTargetHolder.class)
                    .get()
                    .setTargetHighlight(
                            new Texture2D(
                                    ImageDecoder.decodePNG(Test1.class.getResourceAsStream("square.png"), PNGDecoder.Format.RGBA))
                    );

            component.addScript(UiTargetScript.class);
            createCross(new SceneComponent(component));
        });
    }

    private static void createCross(Component component) {
        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("cross.png"),
                PNGDecoder.Format.RGBA
        );

        Texture2D texture2D = new Texture2D(imageData);

        component.addProperty(new ImageProperty(texture2D));
        RectTransformProperty rectTransformProperty = new RectTransformProperty(imageData.getWidth(), imageData.getHeight());
        rectTransformProperty.setPosition(new Vector2f(DISPLAY.getWidth() >> 1, DISPLAY.getHeight() >> 1));
        component.addProperty(rectTransformProperty);
    }

    private static void createShip(Component scene) {
        Component shipComponent = new SceneComponent(scene);
        StaticMesh mesh = ObjLoader.read(
                Test1.class.getResourceAsStream("ship/ship1.obj"),
                true).toMesh();
        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("ship/Color.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D diffuse = new Texture2D(imageData);
        Material material = new Material(diffuse);
        material.setShininess(100f);

        MeshProperty meshProperty = new MeshProperty(mesh);
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(new Vector3f(-20f, 10f, -10f));
        MaterialProperty materialProperty = new MaterialProperty(material);

        shipComponent.addProperty(meshProperty);
        shipComponent.addProperty(transformProperty);
        shipComponent.addProperty(materialProperty);
        scene.getContext().getLoadedContext().findOne(UiTargetHolder.class).get().addComponent(shipComponent);
    }

    public static class UiConstRotScript extends Script {

        @OwnerProperty(@IdOf(RectTransformProperty.class))
        private RectTransformProperty transformProperty;

        public UiConstRotScript(Component owner) {
            super(owner);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onUpdate(int delta) {
            transformProperty.setRotation(transformProperty.getRotation() + (float) Math.PI / 10000 * delta);
        }
    }

    private static void createGray(Component component) {
        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("grey.png"),
                PNGDecoder.Format.RGBA
        );

        Texture2D texture2D = new Texture2D(imageData);

        component.addProperty(new ImageProperty(texture2D));
        RectTransformProperty rectTransformProperty = new RectTransformProperty(imageData.getWidth(), imageData.getHeight());
        rectTransformProperty.setPosition(new Vector2f(-300, 200));
        component.addProperty(rectTransformProperty);
    }


    private static void setupScene(EngineContext engineContext, GraphicsThread thread) {
        SceneHolder sceneHolder = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get();
        Scene scene = sceneHolder.getScene();
        createModels(scene, thread);
        createCubemap(scene, thread);
        scene.addListener(new TestKeyboardListener(scene, engineContext.getLoadedContext().findOne(WindowManager.class).get()));
    }

    private static void createCubemap(Scene scene, GraphicsThread thread) {
        thread.scheduleOnce(() -> {
            ImageDataArray imageDataArray = ImageDecoder.decodeCubemap("net/warpgame/test/stars3", PNGDecoder.Format.RGBA);
            Cubemap cubemap = new Cubemap(imageDataArray.getWidth(), imageDataArray.getHeight(), imageDataArray.getData());
            CubemapProperty cubemapProperty = new CubemapProperty(cubemap);
            scene.addProperty(cubemapProperty);
        });
    }

    private static void createLight(Component scene) {

        Component lsource = new SceneComponent(scene);
        lsource.addProperty(new TransformProperty().move(new Vector3f(0, 0, 20)));

        LightSourceProperty lightSourceProperty = new LightSourceProperty(new Vector3f(1.3f, 1.3f, 1.3f).mul(40));
        lsource.addProperty(lightSourceProperty);
    }

    private static Component createModels(Scene scene, GraphicsThread graphicsThread) {
        Component ship = new SceneComponent(scene);
        graphicsThread.scheduleOnce(() -> {
            createSpheres(scene);
            createMugs(scene);
            createCastle(scene);
            createFloor(scene);
            createSatellite(scene);
            createAnimated(scene);
            createLight(scene);
        });

        return ship;
    }

    private static void createAnimated(Scene scene) {
        AnimatedModelData animatedModelData = ColladaLoader.loadColladaModel(Test1.class.getResourceAsStream("model.dae"), 3);
        AnimationData animationData = ColladaLoader.loadColladaAnimation(Test1.class.getResourceAsStream("model.dae"));
        AnimatedModel model = AnimatedModelLoader.loadData(animatedModelData);

        Map<String, Integer> nameToJointId = new HashMap<>();
        genJointNameToJointIds(model.getRootJoint(), nameToJointId);
        Animation animation = AnimationLoader.loadAnimation(animationData, nameToJointId);

        AnimatedModelProperty animatedModelProperty = new AnimatedModelProperty(model);
        Component c = new SceneComponent(scene);
        c.addProperty(new TransformProperty().move(new Vector3f(0, 0, 10)));
        c.addProperty(animatedModelProperty);
        AnimatorTask animatorTask = c.getContext()
                .getLoadedContext()
                .findOne(AnimatorTask.class)
                .get();
        animatorTask.addAnimator(model.getAnimator());

        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("gosciu.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D diffuse = new Texture2D(imageData);
        Material material = new Material(diffuse);
        material.setShininess(0.05f);
        MaterialProperty materialProperty = new MaterialProperty(material);
        c.addProperty(materialProperty);

        model.getAnimator().startAnimation(animation);
    }

    private static void genJointNameToJointIds(Joint joint, Map<String, Integer> nameToJointId) {
        nameToJointId.put(joint.getName(), joint.getIndex());
        for (Joint childJoint : joint.getChildren()) {
            genJointNameToJointIds(childJoint, nameToJointId);
        }
    }

    private static void registerCommandsAndVariables(Context context) {
        consoleService.init();
        SimpleCommand exit = new SimpleCommand("quit",
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
        SceneHolder sh = context.findOne(SceneHolder.class).get();
        consoleService.registerCommand(new MoveCameraCommand(ch, consoleService));


        consoleService.registerVariable(
                new CommandVariable("cameraPosX", ch.getCameraProperty().getCameraPos()));
    }

    private static void createSatellite(Scene scene) {
        StaticMesh mesh = ObjLoader.read(
                Test1.class.getResourceAsStream("satellite/satelita.obj"),
                true).toMesh();
        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("satellite/sat_tex.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D diffuse = new Texture2D(imageData);
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
        StaticMesh mesh = ObjLoader.read(
                Test1.class.getResourceAsStream("castle2.obj"),
                true).toMesh();


        ImageData imageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("castle_tex.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D diffuse = new Texture2D(imageData);
        Material material = new Material(diffuse);
        MeshProperty meshProperty = new MeshProperty(mesh);
        MaterialProperty materialProperty = new MaterialProperty(material);
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(new Vector3f(0, -30, 0));
        Component component = new SceneComponent(scene);
        component.addProperty(meshProperty);
        component.addProperty(materialProperty);
        component.addProperty(transformProperty);
    }

    private static void createFloor(Scene scene) {
        StaticMesh plainMesh = new PlainMesh();
        Texture2D diffuse = new Texture2D(white);
        TransformProperty transformProperty = new TransformProperty();
        transformProperty.rotateX((float) -(Math.PI / 2));
        transformProperty.scale(new Vector3f(100f));
        transformProperty.move(new Vector3f(0, -30, 0));
        Material material = new Material(diffuse);
        MaterialProperty materialProperty = new MaterialProperty(material);
        MeshProperty meshProperty = new MeshProperty(plainMesh);
        Component component = new SceneComponent(scene);
        component.addProperty(meshProperty);
        component.addProperty(materialProperty);
        component.addProperty(transformProperty);

    }

    private static void createMugs(Scene scene) {
        StaticMesh mugMesh = ObjLoader.read(
                Test1.class.getResourceAsStream("mug.obj"),
                true).toMesh();
        ImageData mugImageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("tex.png"),
                PNGDecoder.Format.RGBA
        );
        Texture2D mugDiffuse = new Texture2D(mugImageData);
        Material mugMaterial = new Material(mugDiffuse);

        StaticMesh boundingBoxMesh = ObjLoader.read(
                Test1.class.getResourceAsStream("boundingbox.obj"),
                true).toMesh();
        ImageData boxImageData = ImageDecoder.decodePNG(
                Test1.class.getResourceAsStream("palette.png"),
                PNGDecoder.Format.RGB
        );
        Texture2D boxDiffuse = new Texture2D(boxImageData);
        boxDiffuse.setParameter(GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        boxDiffuse.setParameter(GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        Material boxMaterial = new Material(boxDiffuse);
        boxMaterial.setShininess(1f);
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    MeshProperty mugMeshProperty = new MeshProperty(mugMesh);
                    MaterialProperty mugMaterialProperty = new MaterialProperty(mugMaterial);
                    TransformProperty mugTransformProperty = new TransformProperty();
                    mugTransformProperty.setTranslation(new Vector3f(x * 5, y * 5, z * 5));
                    mugTransformProperty.move(new Vector3f(0, 0, -50));
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
                    boxTransformProperty.setTranslation(new Vector3f((bb.max().x + bb.min().x) / 2, (bb.max().y + bb.min().y) / 2, (bb.max().z + bb.min().z) / 2));
                    boxTransformProperty.scale(new Vector3f((bb.max().x - bb.min().x), (bb.max().y - bb.min().y), (bb.max().z - bb.min().z)));
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

    private static ImageData white = ImageDecoder.decodePNG(
            Test1.class.getResourceAsStream("tex.png"),
            PNGDecoder.Format.RGB
    );

//    private static ImageData woodDiffuse = ImageDecoder.decodePNG(
//            Test1.class.getResourceAsStream("wood/wood-stack-1-DIFFUSE.png"),
//            PNGDecoder.Format.RGBA
//    );
//
//    private static ImageData woodBump = ImageDecoder.decodePNG(
//            Test1.class.getResourceAsStream("wood/wood-stack-1-DISP.png"),
//            PNGDecoder.Format.RGBA
//    );
//
//    private static ImageData woodNorm = ImageDecoder.decodePNG(
//            Test1.class.getResourceAsStream("wood/wood-stack-1-NORM.png"),
//            PNGDecoder.Format.RGBA
//    );
//
//    private static ImageData stoneDiffuse = ImageDecoder.decodePNG(
//            Test1.class.getResourceAsStream("stone/stone-redstone-2.png"),
//            PNGDecoder.Format.RGBA
//    );
//
//    private static ImageData stoneBump = ImageDecoder.decodePNG(
//            Test1.class.getResourceAsStream("stone/stone-redstone-2-DISP.png"),
//            PNGDecoder.Format.RGBA
//    );
//
//    private static ImageData stoneNorm = ImageDecoder.decodePNG(
//            Test1.class.getResourceAsStream("stone/stone-redstone-2-NORM.png"),
//            PNGDecoder.Format.RGBA
//    );

    private static void createSpheres(Component scene) {

//
//        Texture2D woodD = new Texture2D(
//                woodDiffuse.getWidth(),
//                woodDiffuse.getHeight(),
//                GL11.GL_RGBA16,
//                GL11.GL_RGBA,
//                true,
//                woodDiffuse.getData());
//
//        Texture2D woodB = new Texture2D(
//                woodBump.getWidth(),
//                woodBump.getHeight(),
//                GL11.GL_RGBA16,
//                GL11.GL_RGBA,
//                true,
//                woodBump.getData());
//
//        Texture2D woodN = new Texture2D(
//                woodNorm.getWidth(),
//                woodNorm.getHeight(),
//                GL11.GL_RGBA16,
//                GL11.GL_RGBA,
//                true,
//                woodNorm.getData());
//
//        Texture2D stoneD = new Texture2D(
//                stoneDiffuse.getWidth(),
//                stoneDiffuse.getHeight(),
//                GL11.GL_RGBA16,
//                GL11.GL_RGBA,
//                true,
//                stoneDiffuse.getData());
//
//        Texture2D stoneB = new Texture2D(
//                stoneBump.getWidth(),
//                stoneBump.getHeight(),
//                GL11.GL_RGBA16,
//                GL11.GL_RGBA,
//                true,
//                stoneBump.getData());
//
//        Texture2D stoneN = new Texture2D(
//                stoneNorm.getWidth(),
//                stoneNorm.getHeight(),
//                GL11.GL_RGBA16,
//                GL11.GL_RGBA,
//                true,
//                stoneNorm.getData());
//
//
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < 10; j++) {
//
//                createSphere(scene,
//                        new Vector3f(i * 15, j * 15, 0),
//                        ((i + j) % 2 == 0) ? woodD : stoneD,
//                        ((i + j) % 2 == 0) ? woodB : stoneB,
//                        ((i + j) % 2 == 0) ? woodN : stoneN,
//                        0.15f
//                );
//            }
//        }
//
//        Texture2D whiteD = new Texture2D(
//                white.getWidth(),
//                white.getHeight(),
//                GL11.GL_RGB16,
//                GL11.GL_RGB,
//                true,
//                white.getData());
//
//
//        Component a = new SceneComponent(scene);
//        a.addProperty(new TransformProperty().rotateY((float) (Math.PI / 2.0f)).move(new Vector3f(20, 0, 20)));
//        Component b = new SceneComponent(scene);
//        b.addProperty(new TransformProperty().rotateX((float) (Math.PI / 2.0f)).move(new Vector3f(0, 0, 20)));
//        Component sphere = createSphere(b, new Vector3f(0, 0, 40), whiteD, null, null, 0.0f);
//        Vector3f t = Transforms.getAbsolutePosition(sphere, new Vector3f());
//        t.add(0, -3, 0);
//        createSphere(scene, t.negate(), stoneD, null, null, 0.3f).addScript(S.class);
    }

    public static class S extends Script {

        @OwnerProperty(@IdOf(TransformProperty.class))
        private TransformProperty p;

        public S(Component owner) {
            super(owner);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onUpdate(int delta) {
            p.rotateY(delta / 1000f);
        }
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

    private static void setupCamera(EngineContext engineContext) {
        Scene scene = engineContext.getLoadedContext()
                .findOne(SceneHolder.class)
                .get()
                .getScene();
        Component cameraComponent = new SceneComponent(scene);
        cameraComponent.addProperty(new TransformProperty().move(new Vector3f(-10, -20, 60))
                .rotate((float) (Math.PI / 4), -(float) (Math.PI / 4), (float) 0));
        cameraComponent.addProperty(new AudioListenerProperty());
        cameraComponent.addScript(SimpleControlScript.class);

        CameraHolder cameraHolder = engineContext.getLoadedContext()
                .findOne(CameraHolder.class)
                .get();
        CameraProperty cameraProperty = new CameraProperty(CameraProperty.CameraType.PERSPECTIVE,55f, DISPLAY.getWidth(),  DISPLAY.getHeight(), 0.1f, 10000f);
        cameraComponent.addProperty(cameraProperty);
        cameraHolder.setCamera(cameraComponent);
    }

    private static String resourceToPath(String resource) {
        URL url = Test1.class.getResource(resource);
        String path = null;
        try {
            path = Paths.get(url.toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static class ConstantRotationScript extends Script {

        @OwnerProperty(@IdOf(TransformProperty.class))
        TransformProperty transProp;

        public ConstantRotationScript(Component component) {
            super(component);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onUpdate(int delta) {
            transProp.rotate(0, delta * 0.001f, 0);
        }
    }

}
