package pl.warp.game;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import org.apache.log4j.Logger;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.core.*;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.Scene;
import pl.warp.engine.core.scene.SimpleComponent;
import pl.warp.engine.core.scene.listenable.ListenableParent;
import pl.warp.engine.core.scene.listenable.SimpleListenableParent;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.core.scene.script.ScriptTask;
import pl.warp.engine.graphics.RenderingSettings;
import pl.warp.engine.graphics.RenderingTask;
import pl.warp.engine.graphics.SceneRenderer;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.QuaternionCamera;
import pl.warp.engine.graphics.input.GLFWInput;
import pl.warp.engine.graphics.input.GLFWInputTask;
import pl.warp.engine.graphics.light.LightProperty;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.material.MaterialProperty;
import pl.warp.engine.graphics.math.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.MeshProperty;
import pl.warp.engine.graphics.pipeline.OnScreenRenderer;
import pl.warp.engine.graphics.pipeline.Pipeline;
import pl.warp.engine.graphics.pipeline.builder.PipelineBuilder;
import pl.warp.engine.graphics.resource.mesh.ObjLoader;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.shader.ComponentRendererProgram;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.graphics.window.GLFWWindowManager;
import pl.warp.engine.physics.MovementTask;
import pl.warp.engine.physics.PhysicsTask;
import pl.warp.engine.physics.property.BasicColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;

import java.util.Random;

/**
 * @author Jaca777
 *         Created 2016-06-27 at 14
 *         CANCER CODE, ONLY FOR TESTING
 */
public class Test {

    private static Logger logger = Logger.getLogger(Test.class);
    private static final int WIDTH = 1024, HEIGHT = 720;
    private static final float ROT_SPEED = 0.0002f;
    private static final float MOV_SPEED = 0.2f;
    private static SyncTimer timer = new SyncTimer(100);
    private static final int UPS_LOGGING_RATIO = 100;
    private static EngineTask fpsTask = new EngineTask() {
        @Override
        protected void onInit() {
        }

        @Override
        protected void onClose() {
        }

        private int i = 0;


        private float sum = 0;
        private float lowestUPS = Float.MAX_VALUE;

        @Override
        public void update(long delta) {
            sum += timer.getActualUPS();
            if (timer.getActualUPS() < lowestUPS || lowestUPS == 0)
                lowestUPS = timer.getActualUPS();
            if (i++ % UPS_LOGGING_RATIO == 0) {
                float averageUPS = sum / (float) UPS_LOGGING_RATIO;
                logger.info("Average UPS: " + averageUPS + ", Lowest UPS: " + lowestUPS);
                sum = 0;
                lowestUPS = Float.MAX_VALUE;
            }
        }
    };
    private static Random random = new Random();

    public static void main(String... args) {
        EngineContext context = new EngineContext();
        Component root = new SimpleListenableParent(context);
        Component controllableGoat = new SimpleComponent(root);
        Camera camera = new QuaternionCamera(controllableGoat, new PerspectiveMatrix(60, 0.01f, 200f, WIDTH, HEIGHT));
        camera.move(new Vector3f(0, 1f, 1));
        GLFWInput input = new GLFWInput();
        CameraScript cameraScript = new CameraScript(camera);
        Scene scene = new Scene(root);
        EngineThread graphicsThread = new SyncEngineThread(timer, new RapidExecutionStrategy());
        graphicsThread.scheduleOnce(() -> {
            Mesh goatMesh = ObjLoader.read(Test.class.getResourceAsStream("drone_1.obj")).toVAOMesh(ComponentRendererProgram.ATTRIBUTES);
            ImageDecoder.DecodedImage decodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("drone_1.png"), PNGDecoder.Format.RGBA);
            Texture2D goatTexture = new Texture2D(decodedTexture.getW(), decodedTexture.getH(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTexture.getData());

            generateGOATS(root, goatMesh, goatTexture);

            Component light = new SimpleComponent(root);
            LightProperty property = new LightProperty(light);
            property.addSpotLight(new SpotLight(light, new Vector3f(0f, 0f, 0f), new Vector3f(2f, 2f, 2f), new Vector3f(0.1f, 0.1f, 0.1f), 0.1f, 0.1f));
            new MeshProperty(light, goatMesh);
            MaterialProperty lightMaterial = new MaterialProperty(light, new Material(goatTexture));
            lightMaterial.getMaterial().setBrightness(100f);
            TransformProperty lightSourceTransform = new TransformProperty(light);
            lightSourceTransform.move(new Vector3f(50f, 50f, 50f));
            lightSourceTransform.scale(new Vector3f(0.25f, 0.25f, 0.25f));

            new MeshProperty(controllableGoat, goatMesh);
            new PhysicalBodyProperty(controllableGoat, 2f);
            new MaterialProperty(controllableGoat, new Material(goatTexture));
            new TransformProperty(controllableGoat);
            new GoatControlScript(controllableGoat, input, MOV_SPEED, ROT_SPEED);
        });
        graphicsThread.scheduleTask(fpsTask);
        RenderingSettings settings = new RenderingSettings(WIDTH, HEIGHT);
        Pipeline pipeline = PipelineBuilder.from(new SceneRenderer(scene, camera, settings)).to(new OnScreenRenderer());
        GLFWWindowManager windowManager = new GLFWWindowManager(graphicsThread::interrupt);
        graphicsThread.scheduleTask(new RenderingTask(context, new Display(WIDTH, HEIGHT), windowManager, pipeline));
        graphicsThread.start();
        EngineThread scriptsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        scriptsThread.scheduleTask(new ScriptTask(context.getScriptContext()));
        scriptsThread.scheduleTask(new GLFWInputTask(input, windowManager));
        graphicsThread.scheduleOnce(scriptsThread::start); //has to start after the window is created
        Bullet.init();
        EngineThread physicsThread = new SyncEngineThread(new SyncTimer(60), new RapidExecutionStrategy());
        physicsThread.scheduleTask(new MovementTask(root));
        physicsThread.scheduleTask(new PhysicsTask((ListenableParent) root));
        graphicsThread.scheduleOnce(physicsThread::start);
    }

    private static void generateGOATS(Component parent, Mesh goatMesh, Texture2D goatTexture) {
        for (int i = 0; i < 2000; i++) {
            Component goat = new SimpleComponent(parent);
            new MeshProperty(goat, goatMesh);
            Material material = new Material(goatTexture);
            material.setShininess(0.2f);
            new MaterialProperty(goat, material);
            new PhysicalBodyProperty(goat, 1).applyForce(new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random()));
            new BasicColliderProperty(goat, new btBoxShape(new Vector3(2.833f, 0.6255f, 2.1465f)), new Vector3f(-0.067f, 0, 0));
            float x = random.nextFloat() * 200 - 100f;
            float y = random.nextFloat() * 200 - 100f;
            float z = random.nextFloat() * 200 - 100f;
            TransformProperty transformProperty = new TransformProperty(goat);
            transformProperty.move(new Vector3f(x, y, z));
        }
    }
}
