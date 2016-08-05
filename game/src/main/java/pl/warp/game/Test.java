package pl.warp.game;

import org.apache.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.Script;
import pl.warp.engine.core.scene.SimpleComponent;
import pl.warp.engine.core.scene.listenable.SimpleListenableParent;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.script.ScriptTask;
import pl.warp.engine.graphics.Graphics;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.input.GLFWInput;
import pl.warp.engine.graphics.input.GLFWInputTask;
import pl.warp.engine.graphics.light.LightProperty;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.mesh.GraphicsCustomRendererProgramProperty;
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.shapes.Ring;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.particles.*;
import pl.warp.engine.graphics.postprocessing.lens.GraphicsLensFlareProperty;
import pl.warp.engine.graphics.postprocessing.lens.LensFlare;
import pl.warp.engine.graphics.postprocessing.lens.SingleFlare;
import pl.warp.engine.graphics.resource.mesh.ObjLoader;
import pl.warp.engine.graphics.resource.texture.ImageData;
import pl.warp.engine.graphics.resource.texture.ImageDataArray;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.skybox.GraphicsSkyboxProperty;
import pl.warp.engine.graphics.texture.Cubemap;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.GLFWWindowManager;
import pl.warp.engine.physics.DefaultCollisionStrategy;
import pl.warp.engine.physics.MovementTask;
import pl.warp.engine.physics.PhysicsTask;
import pl.warp.engine.physics.RayTester;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.program.gas.GasPlanetProgram;
import pl.warp.game.program.gas.PlanetaryRingProgram;

import java.util.Random;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 14
 *         CANCER CODE, ONLY FOR TESTING
 */
public class Test {

    private static Logger logger = Logger.getLogger(Test.class);
    private static final boolean FULLSCREEN = false;
    private static final int WIDTH = 1560, HEIGHT = 860;
    private static final float ROT_SPEED = 0.05f * 0.5f;
    private static final float MOV_SPEED = 0.2f * 0.5f;
    private static final float BRAKING_FORCE = 0.1f;
    private static final float ARROWS_ROTATION_SPEED = 2f;
    private static final int GUN_COOLDOWN = 5;
    private static Random random = new Random();

    public static void main(String... args) {

        EngineContext context = new EngineContext();
        Scene scene = new Scene(context);
        context.setScene(scene);
        Component root = new SimpleListenableParent(scene);

        Component controllableGoat = new SimpleComponent(root);
        Camera camera = new QuaternionCamera(controllableGoat, new PerspectiveMatrix(70, 0.01f, 20000f, WIDTH, HEIGHT));
        camera.move(new Vector3f(0, 4f, 15f));
        RenderingConfig settings = new RenderingConfig(60, new Display(FULLSCREEN, WIDTH, HEIGHT));
        Graphics graphics = new Graphics(context, camera, settings);
        EngineThread graphicsThread = graphics.getThread();
        graphics.enableUpsLogging();
        //CameraScript cameraScript = new CameraScript(camera);
        GLFWInput input = new GLFWInput();

        graphicsThread.scheduleOnce(() -> {
            Mesh goatMesh = ObjLoader.read(Test.class.getResourceAsStream("fighter_1.obj")).toVAOMesh();
            ImageData decodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_1.png"), PNGDecoder.Format.RGBA);
            Texture2D goatTexture = new Texture2D(decodedTexture.getWidth(), decodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTexture.getData());

            ImageDataArray lightSpritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            Texture2DArray lightSpritesheetTexture = new Texture2DArray(lightSpritesheet.getWidth(), lightSpritesheet.getHeight(), lightSpritesheet.getArraySize(), lightSpritesheet.getData());
            Component light = new SimpleComponent(root);
            LightProperty property = new LightProperty(light);
            SpotLight spotLight = new SpotLight(light, new Vector3f(0f, 0f, 0f), new Vector3f(2f, 2f, 2f), new Vector3f(0.6f, 0.6f, 0.6f), 0.1f, 0.1f);
            property.addSpotLight(spotLight);
            TransformProperty lightSourceTransform = new TransformProperty(light);
            lightSourceTransform.move(new Vector3f(30f, 0f, 0f));
            ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0), new Vector2f(0), 0);
            ParticleFactory factory = new RandomSpreadingParticleFactory(0.02f, 600, true, true);
            new GraphicsParticleEmitterProperty(light, new ParticleSystem(animator, factory, 1000, lightSpritesheetTexture));

            ImageDataArray lensSpritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("lens_flares.png"), PNGDecoder.Format.RGBA, 2, 1);
            Texture2DArray lensTexture = new Texture2DArray(lensSpritesheet.getWidth(), lensSpritesheet.getHeight(), lensSpritesheet.getArraySize(), lensSpritesheet.getData());
            SingleFlare[] flares = new SingleFlare[]{
                    new SingleFlare(0.75f, 0, 0.08f, new Vector3f(1)),
                    new SingleFlare(0.1f, 1, 0.02f, new Vector3f(1)),
                    new SingleFlare(-0.2f, 0, 0.06f, new Vector3f(1)),
                    new SingleFlare(-0.4f, 1, 0.08f, new Vector3f(1)),
                    new SingleFlare(-0.5f, 1, 0.05f, new Vector3f(1)),
                    new SingleFlare(-0.6f, 1, 0.08f, new Vector3f(1)),
                    new SingleFlare(-0.4f, 1, 0.25f, new Vector3f(2f, 0.5f, 2f)),
                    new SingleFlare(-0.1f, 1, 0.2f, new Vector3f(0.5f, 0.5f, 2f)),
                    new SingleFlare(0.2f, 1, 0.25f, new Vector3f(0.5f, 2f, 0.5f)),
                    new SingleFlare(0.6f, 1, 0.25f, new Vector3f(2f, 0.5f, 0.5f)),
            };
            LensFlare flare = new LensFlare(lensTexture, flares);
            new GraphicsLensFlareProperty(light, flare);



            Component gasSphere = new SimpleComponent(root);
            Sphere sphere = new Sphere(50, 50);
            new GraphicsMeshProperty(gasSphere, sphere);
            ImageData decodedColorsTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("gas.png"), PNGDecoder.Format.RGBA);
            Texture1D colorsTexture = new Texture1D(decodedColorsTexture.getWidth(), GL11.GL_RGBA, GL11.GL_RGBA, false, decodedColorsTexture.getData());
            GasPlanetProgram gasProgram = new GasPlanetProgram(colorsTexture);
            new GraphicsCustomRendererProgramProperty(gasSphere, gasProgram);
            TransformProperty gasSphereSourceTransform = new TransformProperty(gasSphere);
            gasSphereSourceTransform.move(new Vector3f(-1200f, 0f, -500f));
            gasSphereSourceTransform.scale(new Vector3f(1000.0f));
            graphicsThread.scheduleTask(new SimpleEngineTask() {
                @Override
                public void update(int delta) {
                    gasProgram.use();
                    gasProgram.update(delta);
                }
            });

            Component ring = new SimpleComponent(gasSphere);
            Ring ringMesh = new Ring(20, 1.5f, 3f);
            new GraphicsMeshProperty(ring, ringMesh);
            new GraphicsCustomRendererProgramProperty(ring, new PlanetaryRingProgram(null));

            ImageData brightnessTextureData = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_1_brightness.png"), PNGDecoder.Format.RGBA);
            Texture2D brightnessTexture = new Texture2D(brightnessTextureData.getWidth(), brightnessTextureData.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, brightnessTextureData.getData());

            generateGOATS(root, goatMesh, goatTexture, brightnessTexture);

            ImageDataArray spritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            Texture2DArray spritesheetTexture = new Texture2DArray(spritesheet.getWidth(), spritesheet.getHeight(), spritesheet.getArraySize(), spritesheet.getData());

            new GraphicsMeshProperty(controllableGoat, goatMesh);
            new PhysicalBodyProperty(controllableGoat, 1f, 2.833f);
            Material material = new Material(goatTexture);
            material.setBrightnessTexture(brightnessTexture);
            new GraphicsMaterialProperty(controllableGoat, material);
            new TransformProperty(controllableGoat);
            new GoatControlScript(controllableGoat, input, MOV_SPEED, ROT_SPEED, BRAKING_FORCE, ARROWS_ROTATION_SPEED);

            Mesh bulletMesh = ObjLoader.read(GunScript.class.getResourceAsStream("bullet.obj")).toVAOMesh();
            new GunScript(controllableGoat, GUN_COOLDOWN, input, root, bulletMesh, spritesheetTexture, controllableGoat);

            SpotLight goatLight = new SpotLight(
                    controllableGoat,
                    new Vector3f(0, 0, 1),
                    new Vector3f(0, 0, 1), 0.15f, 0.2f,
                    new Vector3f(5f, 5f, 5f),
                    new Vector3f(0f, 0f, 0f),
                    0.1f, 0.1f);
            LightProperty directionalLightProperty = new LightProperty(controllableGoat);
            directionalLightProperty.addSpotLight(goatLight);

            ImageDataArray decodedCubemap = ImageDecoder.decodeCubemap("pl/warp/game/stars3");
            Cubemap cubemap = new Cubemap(decodedCubemap.getWidth(), decodedCubemap.getHeight(), decodedCubemap.getData());
            new GraphicsSkyboxProperty(scene, cubemap);
        });
        graphicsThread.scheduleOnce(() -> {
            EngineThread scriptsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
            scriptsThread.scheduleTask(new ScriptTask(context.getScriptContext()));
            GLFWWindowManager windowManager = graphics.getWindowManager();
            scriptsThread.scheduleTask(new GLFWInputTask(input, windowManager));
            scriptsThread.start(); //has to start after the window is created
        });
        EngineThread physicsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        RayTester rayTester = new RayTester();
        physicsThread.scheduleOnce(() -> {
            physicsThread.scheduleTask(new MovementTask(root));
            physicsThread.scheduleTask(new PhysicsTask(new DefaultCollisionStrategy(), root, rayTester));
        });

        new Script(root) {
            @Override
            public void onInit() {

            }

            @Override
            public void onUpdate(int delta) {
                if (input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                    graphicsThread.scheduleOnce(graphicsThread::interrupt);
                    physicsThread.scheduleOnce(physicsThread::interrupt);
                }
            }
        };

        graphicsThread.scheduleOnce(physicsThread::start);
        graphics.create();


    }

    private static void generateGOATS(Component parent, Mesh goatMesh, Texture2D goatTexture, Texture2D brightnessTexture) {
        for (int i = 0; i < 300; i++) {
            Component goat = new SimpleComponent(parent);
            new GraphicsMeshProperty(goat, goatMesh);
            Material material = new Material(goatTexture);
            material.setShininess(4f);
            material.setBrightnessTexture(brightnessTexture);
            new GraphicsMaterialProperty(goat, material);
            new PhysicalBodyProperty(goat, 1f, 2.833f);
            float x = random.nextFloat() * 200 - 100f;
            float y = random.nextFloat() * 200 - 100f;
            float z = random.nextFloat() * 200 - 100f;
            TransformProperty transformProperty = new TransformProperty(goat);
            transformProperty.move(new Vector3f(x, y, z));
        }
    }
}
