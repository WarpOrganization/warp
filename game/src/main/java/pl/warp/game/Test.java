package pl.warp.game;

import org.apache.log4j.Logger;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;
import pl.warp.engine.core.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
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
import pl.warp.engine.graphics.mesh.GraphicsMeshProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.particles.*;
import pl.warp.engine.graphics.postprocessing.lens.GraphicsLensFlareProperty;
import pl.warp.engine.graphics.postprocessing.lens.LensFlare;
import pl.warp.engine.graphics.postprocessing.lens.SingleFlare;
import pl.warp.engine.graphics.resource.mesh.ObjLoader;
import pl.warp.engine.graphics.resource.texture.ImageData;
import pl.warp.engine.graphics.resource.texture.ImageDataArray;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.skybox.GraphicsSkyboxProperty;
import pl.warp.engine.graphics.texture.Cubemap;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.graphics.window.GLFWWindowManager;
import pl.warp.engine.physics.DefaultCollisionStrategy;
import pl.warp.engine.physics.MovementTask;
import pl.warp.engine.physics.PhysicsTask;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.Random;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 14
 *         CANCER CODE, ONLY FOR TESTING
 */
public class Test {

    private static Logger logger = Logger.getLogger(Test.class);
    private static final int WIDTH = 1800, HEIGHT = 1060;
    private static final float ROT_SPEED = 0.05f * 200;
    private static final float MOV_SPEED = 0.2f * 200;
    private static final float BRAKING_FORCE = 0.1f * 200;
    private static final float ARROWS_ROTATION_SPEED = 2f;
    private static final int GUN_COOLDOWN = 1;
    private static Random random = new Random();

    public static void main(String... args) {

        EngineContext context = new EngineContext();
        Scene scene = new Scene(context);
        context.setScene(scene);
        Component root = new SimpleListenableParent(scene);
        Component controllableGoat = new SimpleComponent(root);
        Camera camera = new QuaternionCamera(controllableGoat, new PerspectiveMatrix(70, 0.01f, 1000f, WIDTH, HEIGHT));
        camera.move(new Vector3f(0, 1f, 1));
        RenderingConfig settings = new RenderingConfig(60, WIDTH, HEIGHT);
        Graphics graphics = new Graphics(context, camera, settings);
        EngineThread graphicsThread = graphics.getThread();
        graphics.enableUpsLogging();
        CameraScript cameraScript = new CameraScript(camera);
        GLFWInput input = new GLFWInput();

        graphicsThread.scheduleOnce(() -> {
            Mesh goatMesh = ObjLoader.read(Test.class.getResourceAsStream("drone_1.obj")).toVAOMesh();
            ImageData decodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("drone_1.png"), PNGDecoder.Format.RGBA);
            Texture2D goatTexture = new Texture2D(decodedTexture.getWidth(), decodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTexture.getData());

            Component light = new SimpleComponent(root);
            LightProperty property = new LightProperty(light);
            SpotLight spotLight = new SpotLight(light, new Vector3f(0f, 0f, 0f), new Vector3f(2f, 2f, 2f), new Vector3f(0.6f, 0.6f, 0.6f), 0.1f, 0.1f);
            property.addSpotLight(spotLight);
            new GraphicsMeshProperty(light, goatMesh);
            GraphicsMaterialProperty lightMaterial = new GraphicsMaterialProperty(light, new Material(goatTexture));
            lightMaterial.getMaterial().setBrightness(100f);
            TransformProperty lightSourceTransform = new TransformProperty(light);
            lightSourceTransform.move(new Vector3f(50f, 50f, 50f));
            lightSourceTransform.scale(new Vector3f(0.25f, 0.25f, 0.25f));
            Mesh bulletMesh = ObjLoader.read(GunScript.class.getResourceAsStream("bullet.obj")).toVAOMesh();

            ImageDataArray spritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            Texture2DArray spritesheetTexture = new Texture2DArray(spritesheet.getWidth(), spritesheet.getHeight(), spritesheet.getArraySize(), spritesheet.getData());
            ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0), new Vector2f(0), 0);
            ParticleFactory factory = new RandomSpreadingParticleFactory(0.1f, 400, true, true);
            new GraphicsParticleSystemProperty(light, new ParticleSystem(animator, factory, 2000, spritesheetTexture));

            ImageDataArray lensSpritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("lens_flares.png"), PNGDecoder.Format.RGBA, 2, 1);
            Texture2DArray lensTexture = new Texture2DArray(lensSpritesheet.getWidth(), lensSpritesheet.getHeight(), lensSpritesheet.getArraySize(), lensSpritesheet.getData());
            SingleFlare flare1 = new SingleFlare(0.5f, 1, 0.02f);
            SingleFlare flare2 = new SingleFlare(-0.1f, 0, 0.02f);
            SingleFlare flare3 = new SingleFlare(0.52f, 0, 0.05f);
            SingleFlare flare4 = new SingleFlare(0.2f, 1, 0.02f);
            LensFlare flare = new LensFlare(lensTexture, new SingleFlare[]{flare1, flare2, flare3, flare4});
            new GraphicsLensFlareProperty(light, flare);

            generateGOATS(root, goatMesh, goatTexture, spritesheetTexture);

            new GraphicsMeshProperty(controllableGoat, goatMesh);
            new PhysicalBodyProperty(controllableGoat, 200f, 2.833f);
            new GraphicsMaterialProperty(controllableGoat, new Material(goatTexture));
            new TransformProperty(controllableGoat);
            new GoatControlScript(controllableGoat, input, MOV_SPEED, ROT_SPEED, BRAKING_FORCE, ARROWS_ROTATION_SPEED);
            new GunScript(controllableGoat, GUN_COOLDOWN, input, root, bulletMesh);

            SpotLight goatLight = new SpotLight(
                    controllableGoat,
                    new Vector3f(0, 0, 1),
                    new Vector3f(0, 0, 1), 0.15f, 0.2f,
                    new Vector3f(5f, 5f, 5f),
                    new Vector3f(0f, 0f, 0f),
                    0.1f, 0.1f);
            LightProperty directionalLightProperty = new LightProperty(controllableGoat);
            directionalLightProperty.addSpotLight(goatLight);

            SpotLight laser = new SpotLight(
                    controllableGoat,
                    new Vector3f(0, 0, 1),
                    new Vector3f(0, 0, 1), 0.001f, 0.002f,
                    new Vector3f(0f, 30f, 0f),
                    new Vector3f(0f, 0f, 0f),
                    0f, 0f);
            directionalLightProperty.addSpotLight(laser);

            ImageDataArray decodedCubemap = ImageDecoder.decodeCubemap("pl/warp/game/stars2");
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
        physicsThread.scheduleOnce(() -> {
            physicsThread.scheduleTask(new MovementTask(root));
            physicsThread.scheduleTask(new PhysicsTask(new DefaultCollisionStrategy(), root));
        });
        graphicsThread.scheduleOnce(physicsThread::start);
        graphics.create();

    }

    private static void generateGOATS(Component parent, Mesh goatMesh, Texture2D goatTexture, Texture2DArray spritesheetTexture) {
        for (int i = 0; i < 1000; i++) {
            Component goat = new SimpleComponent(parent);
            new GraphicsMeshProperty(goat, goatMesh);
            Material material = new Material(goatTexture);
            material.setShininess(4f);
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
