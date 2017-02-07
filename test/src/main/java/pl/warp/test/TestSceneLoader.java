package pl.warp.test;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.ai.behaviortree.SequenceNode;
import pl.warp.engine.ai.loader.BehaviourTreeBuilder;
import pl.warp.engine.ai.loader.BehaviourTreeLoader;
import pl.warp.engine.ai.property.AIProperty;
import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.core.EngineThread;
import pl.warp.engine.core.SimpleEngineTask;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.NameProperty;
import pl.warp.engine.core.scene.PoolEventDispatcher;
import pl.warp.engine.core.scene.properties.TransformProperty;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.camera.Camera;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.camera.QuaternionCamera;
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
import pl.warp.engine.graphics.particles.GraphicsParticleEmitterProperty;
import pl.warp.engine.graphics.particles.ParticleAnimator;
import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.SimpleParticleAnimator;
import pl.warp.engine.graphics.particles.dot.DotParticle;
import pl.warp.engine.graphics.particles.dot.DotParticleSystem;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.graphics.particles.dot.RandomSpreadingStageDotParticleFactory;
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
import pl.warp.engine.physics.CollisionType;
import pl.warp.engine.physics.collider.BasicCollider;
import pl.warp.engine.physics.property.ColliderProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.GameContextBuilder;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.scene.GameSceneLoader;
import pl.warp.test.ai.DroneMemoryProperty;
import pl.warp.test.program.gas.GasPlanetProgram;
import pl.warp.test.program.ring.PlanetaryRingProgram;
import pl.warp.test.program.ring.PlanetaryRingProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * @author Jaca777
 *         Created 2017-01-22 at 11
 */
public class TestSceneLoader implements GameSceneLoader {

    private static final float ROT_SPEED = 0.05f;
    private static final float MOV_SPEED = 0.2f * 10;
    private static final float BRAKING_FORCE = 0.2f * 10;
    private static final float ARROWS_ROTATION_SPEED = 2f;
    private static final int GUN_COOLDOWN = 200;
    public static GameComponent MAIN_GOAT;

    private boolean loaded = false;

    private RenderingConfig config;
    private GameContextBuilder contextBuilder;
    private GameScene scene;
    private GameSceneComponent controllableGoat;
    private GameComponent cameraComponent;
    private Mesh bulletMesh;
    private Texture2DArray boomSpritesheet;
    private Texture2D bulletTexture;
    private AudioManager audioManager;
    private Mesh goatMesh;
    private Texture2D goatTexture;
    private Texture2D goatBrightnessTexture;
    private Mesh friageMesh;
    private Texture2D frigateTexture;
    private Texture2D frigateBrightnessTexture;
    private Texture2DArray lightSpritesheetTexture;
    private SingleFlare[] flares;
    private Texture2DArray lensTexture;
    private Texture1D colorsTexture;
    private Texture1D ringColors;
    private GasPlanetProgram gasProgram;
    private EngineThread graphicsThread;
    private Sphere sphere;
    private Ring ringMesh;
    private PlanetaryRingProgram planetaryRingProgram;
    private ImageData brightnessTextureData;
    private ImageData brightnessTextureData2;
    private Texture2D goatTexture2;
    private Texture2D bulletTexture2;
    private Texture2D goatBrightnessTexture2;


    public TestSceneLoader(RenderingConfig config, GameContextBuilder contextBuilder) {
        this.config = config;
        this.contextBuilder = contextBuilder;
    }

    @Override
    public void loadScene() {

        try {
            unpackResources();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new GameScene(contextBuilder.getGameContext());
        scene.addProperty(new NameProperty("Test universe"));
        contextBuilder.setScene(scene);
        contextBuilder.setEventDispatcher(new PoolEventDispatcher());


        controllableGoat = new GameSceneComponent(scene);
        controllableGoat.addProperty(new NameProperty("Player ship"));
        MAIN_GOAT = controllableGoat;
        Display display = config.getDisplay();

        cameraComponent = new GameSceneComponent(controllableGoat);
        TransformProperty cameraTransform = new TransformProperty();
        cameraComponent.addProperty(cameraTransform);
        Camera camera = new QuaternionCamera(cameraComponent, cameraTransform, new PerspectiveMatrix(70, 0.01f, 20000f, display.getWidth(), display.getHeight()));
        cameraComponent.addProperty(new NameProperty("Camera"));
        cameraComponent.addProperty(new CameraProperty(camera));
        camera.move(new Vector3f(0, 4f, 15f));
        controllableGoat.addProperty(new PhysicalBodyProperty(10f, 10.772f / 2, 1.8f / 2, 13.443f / 2));
        controllableGoat.addProperty(new TransformProperty());
        loaded = true;
    }

    public void enableControls() {
        new GoatControlScript(controllableGoat, MOV_SPEED, ROT_SPEED, BRAKING_FORCE, ARROWS_ROTATION_SPEED);
    }

    private static void unpackResources() throws IOException {
        String path = Test.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        String decodedPath = URLDecoder.decode(path, "UTF-8");
        File jarFile = new File(decodedPath);
        String parent = jarFile.getParent();
        String dataPath = parent + File.separator + "data";
        File dataDir = new File(dataPath);
        if (dataDir.exists()) FileUtils.deleteDirectory(dataDir);
        copy(dataPath + "" + File.separator + "sound" + File.separator + "effects" + File.separator + "gun.wav", "sound/effects/gun.wav");
        copy(dataPath + "" + File.separator + "sound" + File.separator + "music" + File.separator + "Stellardrone-Light_Years-01_Red_Giant.wav", "sound/music/Stellardrone-Light_Years-01_Red_Giant.wav");
        copy(dataPath + "" + File.separator + "sound" + File.separator + "music" + File.separator + "Stellardrone-Light_Years-05_In_Time.wav", "sound/music/Stellardrone-Light_Years-05_In_Time.wav");
        copy(dataPath + "" + File.separator + "ai" + File.separator + "droneAI.xml", "ai/droneAI.xml");

    }

    private static void copy(String decodedPath, String name) throws IOException {
        FileOutputStream fileOutputStream4 = getFileOutputStream(decodedPath);
        InputStream ai = Test.class.getResourceAsStream(name);
        IOUtils.copy(ai, fileOutputStream4);
    }


    private static FileOutputStream getFileOutputStream(String decodedPath) throws IOException {
        File file = new File(decodedPath);
        new File(file.getParent()).mkdirs();
        file.createNewFile();
        return new FileOutputStream(file);
    }

    @Override
    public void loadGraphics(EngineThread graphicsThread) {
        graphicsThread.scheduleOnce(() -> {

            //new ComponentLoggingScript(controllableGoat);
            goatMesh = ObjLoader.read(Test.class.getResourceAsStream("fighter_1.obj"), false).toVAOMesh();
            ImageData decodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_1.png"), PNGDecoder.Format.RGBA);
            goatTexture = new Texture2D(decodedTexture.getWidth(), decodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTexture.getData());

            ImageData decodedTexture2 = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_2.png"), PNGDecoder.Format.RGBA);
            goatTexture2 = new Texture2D(decodedTexture2.getWidth(), decodedTexture2.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTexture2.getData());


            ImageDataArray lensSpritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("lens_flares.png"), PNGDecoder.Format.RGBA, 2, 1);
            lensTexture = new Texture2DArray(lensSpritesheet.getWidth(), lensSpritesheet.getHeight(), lensSpritesheet.getArraySize(), lensSpritesheet.getData());
            flares = new SingleFlare[]{
                    new SingleFlare(0.75f, 0, 0.08f, new Vector3f(1)),
                    new SingleFlare(0.1f, 1, 0.02f, new Vector3f(1)),
                    new SingleFlare(-0.2f, 0, 0.06f, new Vector3f(1)),
                    new SingleFlare(-0.4f, 1, 0.08f, new Vector3f(1)),
                    new SingleFlare(-0.5f, 1, 0.05f, new Vector3f(1)),
                    new SingleFlare(-0.6f, 1, 0.08f, new Vector3f(1)),
                    new SingleFlare(-0.4f, 1, 0.25f, new Vector3f(1)),
                    new SingleFlare(-0.1f, 1, 0.2f, new Vector3f(1)),
                    new SingleFlare(0.2f, 1, 0.25f, new Vector3f(1)),
                    new SingleFlare(0.6f, 1, 0.25f, new Vector3f(1f)),
            };


            GameComponent gasSphere = new GameSceneComponent(scene);
            sphere = new Sphere(50, 50);
            gasSphere.addProperty(new GraphicsMeshProperty(sphere));
            ImageData decodedColorsTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("gas.png"), PNGDecoder.Format.RGBA);
            colorsTexture = new Texture1D(decodedColorsTexture.getWidth(), GL11.GL_RGBA, GL11.GL_RGBA, false, decodedColorsTexture.getData());
            gasProgram = new GasPlanetProgram(colorsTexture);
            gasSphere.addProperty(new GraphicsCustomRendererProgramProperty(gasProgram));
            TransformProperty gasSphereTransform = new TransformProperty();
            gasSphereTransform.move(new Vector3f(-1200f, -200f, -500f));
            gasSphereTransform.scale(new Vector3f(1000.0f));
            gasSphere.addProperty(gasSphereTransform);
            this.graphicsThread = graphicsThread;
            this.graphicsThread.scheduleTask(new SimpleEngineTask() {
                @Override
                public void update(int delta) {
                    gasProgram.use();
                    gasProgram.update(delta);
                    gasSphereTransform.rotateLocalY(delta * 0.00001f);
                }
            });

            float startR = 1.5f;
            float endR = 2.5f;
            Component ring = new GameSceneComponent(gasSphere);
            ringMesh = new Ring(20, startR, endR);
            ring.addProperty(new GraphicsMeshProperty(ringMesh));
            planetaryRingProgram = new PlanetaryRingProgram();
            ring.addProperty(new GraphicsCustomRendererProgramProperty(planetaryRingProgram));
            ImageData ringColorsData = ImageDecoder.decodePNG(Test.class.getResourceAsStream("ring_colors.png"), PNGDecoder.Format.RGBA);
            ringColors = new Texture1D(ringColorsData.getWidth(), GL11.GL_RGBA, GL11.GL_RGBA, true, ringColorsData.getData());
            ringColors.enableAnisotropy(4);
            ring.addProperty(new PlanetaryRingProperty(startR, endR, ringColors));

            brightnessTextureData = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_1_brightness.png"), PNGDecoder.Format.RGBA);
            goatBrightnessTexture = new Texture2D(brightnessTextureData.getWidth(), brightnessTextureData.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, brightnessTextureData.getData());

            brightnessTextureData2 = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_2_brightness.png"), PNGDecoder.Format.RGBA);
            goatBrightnessTexture2 = new Texture2D(brightnessTextureData2.getWidth(), brightnessTextureData2.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, brightnessTextureData2.getData());

            /*Mesh floorMesh = ObjLoader.read(Test.class.getResourceAsStream("floor.obj"), false).toVAOMesh();
            ImageData decodedFloorTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("floor_1.png"), PNGDecoder.Format.RGBA);
            Texture2D floorTexture = new Texture2D(decodedFloorTexture.getWidth(), decodedFloorTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedFloorTexture.getData());
            Material floorMaterial = new Material(floorTexture);
            Component floor = new GameSceneComponent(scene);
            new GraphicsMeshProperty(floor, floorMesh);
            new GraphicsMaterialProperty(floor,floorMaterial);
            new TransformProperty(floor);
            new PhysicalBodyProperty(floor, 100, 100, 100, 100);
*/

            ImageDataArray spritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            boomSpritesheet = new Texture2DArray(spritesheet.getWidth(), spritesheet.getHeight(), spritesheet.getArraySize(), spritesheet.getData());

            GraphicsMeshProperty graphicsMeshProperty = new GraphicsMeshProperty(goatMesh);
            controllableGoat.addProperty(graphicsMeshProperty);

            Material material = new Material(goatTexture);
            material.setBrightnessTexture(goatBrightnessTexture);
            controllableGoat.addProperty(new GraphicsMaterialProperty(material));

            bulletMesh = new Sphere(15, 15, 0.5f);
            ImageData bulletDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("bullet.png"), PNGDecoder.Format.RGBA);
            bulletTexture = new Texture2D(bulletDecodedTexture.getWidth(), bulletDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, bulletDecodedTexture.getData());
            controllableGoat.addProperty(new GunProperty(GUN_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture, audioManager));
            audioManager = AudioManager.INSTANCE;

            ImageData bullet2decodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("bullet_2.png"), PNGDecoder.Format.RGBA);
            bulletTexture2 = new Texture2D(bullet2decodedTexture.getWidth(), bullet2decodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, bullet2decodedTexture.getData());

            new GunScript(controllableGoat);

            SpotLight goatLight = new SpotLight(
                    controllableGoat,
                    new Vector3f(0, 0, 1),
                    new Vector3f(0, 0, 1), 0.30f, 0.3f,
                    new Vector3f(0.5f),
                    new Vector3f(0f, 0f, 0f),
                    0.1f, 0.1f);
            LightProperty directionalLightProperty = new LightProperty();
            controllableGoat.addProperty(directionalLightProperty);
            directionalLightProperty.addSpotLight(goatLight);

            ImageDataArray decodedCubemap = ImageDecoder.decodeCubemap("pl/warp/test/stars3");
            Cubemap cubemap = new Cubemap(decodedCubemap.getWidth(), decodedCubemap.getHeight(), decodedCubemap.getData());
            scene.addProperty(new GraphicsSkyboxProperty(cubemap));


            friageMesh = ObjLoader.read(GunScript.class.getResourceAsStream("frigate_1_heavy.obj"), false).toVAOMesh();
            ImageData frigateDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("frigate_1_heavy.png"), PNGDecoder.Format.RGBA);
            frigateTexture = new Texture2D(frigateDecodedTexture.getWidth(), frigateDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, frigateDecodedTexture.getData());
            Material frigateMaterial = new Material(frigateTexture);
            frigateMaterial.setShininess(20);
            ImageData frigateBrightnessDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("frigate_1_heavy_brightness.png"), PNGDecoder.Format.RGBA);
            frigateBrightnessTexture = new Texture2D(frigateBrightnessDecodedTexture.getWidth(), frigateBrightnessDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, frigateBrightnessDecodedTexture.getData());
            frigateMaterial.setBrightnessTexture(frigateBrightnessTexture);
            GameComponent frigate = new GameSceneComponent(scene);
            frigate.addProperty(new NameProperty("Frigate"));
            frigate.addProperty(new GraphicsMeshProperty(friageMesh));
            frigate.addProperty(new GraphicsMaterialProperty(frigateMaterial));
   /*         frigate.addProperty(new PhysicalBodyProperty(20.0f, 38.365f, 15.1f, 11.9f));*/
            TransformProperty transformProperty = new TransformProperty();
            transformProperty.move(new Vector3f(100, 0, 0));
            transformProperty.rotateLocalY((float) -(Math.PI / 2));
            transformProperty.scale(new Vector3f(3));
            frigate.addProperty(transformProperty);

            {
                Component light = new GameSceneComponent(frigate);
                TransformProperty lightSourceTransform = new TransformProperty();
                lightSourceTransform.move(new Vector3f(18f, 0.4f, 4.4f));
                light.addProperty(lightSourceTransform);
                ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0.00001f, 0.0f, 0), 0, 0);
                ParticleStage[] stages = {
                        new ParticleStage(4.0f, new Vector4f(0.2f, 0.5f, 1.0f, 2.0f)),
                        new ParticleStage(4.0f, new Vector4f(0.2f, 0.5f, 1.0f, 0.0f))
                };
                ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(0.002f, 800, 100, true, true, stages);
                light.addProperty(new GraphicsParticleEmitterProperty(new DotParticleSystem(animator, factory, 300)));
                LensFlare flare = new LensFlare(lensTexture, flares);
                new GraphicsLensFlareProperty(flare);
            }

            {
                Component light = new GameSceneComponent(frigate);
                TransformProperty lightSourceTransform = new TransformProperty();
                lightSourceTransform.move(new Vector3f(18f, 0.4f, -4.4f));
                light.addProperty(lightSourceTransform);
                ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0.00001f, 0.0f, 0), 0, 0);
                ParticleStage[] stages = {
                        new ParticleStage(4.0f, new Vector4f(0.2f, 0.5f, 1.0f, 2.0f)),
                        new ParticleStage(4.0f, new Vector4f(0.2f, 0.5f, 1.0f, 0.0f))
                };
                ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(0.002f, 800, 100, true, true, stages);
                light.addProperty(new GraphicsParticleEmitterProperty(new DotParticleSystem(animator, factory, 300)));
                LensFlare flare = new LensFlare(lensTexture, flares);
                new GraphicsLensFlareProperty(flare);
            }

            ImageDataArray lightSpritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            lightSpritesheetTexture = new Texture2DArray(lightSpritesheet.getWidth(), lightSpritesheet.getHeight(), lightSpritesheet.getArraySize(), lightSpritesheet.getData());
            {
                Component light = new GameSceneComponent(scene);
                LightProperty property = new LightProperty();
                light.addProperty(property);
                SpotLight spotLight = new SpotLight(light, new Vector3f(0f, 0f, 0f), new Vector3f(2f, 2f, 2f).mul(4), new Vector3f(0.6f, 0.6f, 0.6f), 0.1f, 0.1f);
                property.addSpotLight(spotLight);
                TransformProperty lightSourceTransform = new TransformProperty();
                light.addProperty(lightSourceTransform);
                ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0, 0.00002f, 0), 0, 0);
                ParticleStage[] stages = {
                        new ParticleStage(0.4f, new Vector4f(0.5f, 1.5f, 0.5f, 2.0f)),
                        new ParticleStage(0.85f, new Vector4f(0.5f, 0.5f, 2.0f, 0.0f))
                };
                ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(0.006f, 1500, 500, true, true, stages);
                light.addProperty(new GraphicsParticleEmitterProperty(new DotParticleSystem(animator, factory, 700)));
                LensFlare flare = new LensFlare(lensTexture, flares);
                new GraphicsLensFlareProperty(flare);
            }


            {
                Component light = new GameSceneComponent(scene);
                TransformProperty lightSourceTransform = new TransformProperty();
                lightSourceTransform.move(new Vector3f(15f, 0f, 0f));
                light.addProperty(lightSourceTransform);
                ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0, 0.00002f, 0), 0, 0);
                ParticleStage[] stages = {
                        new ParticleStage(0.4f, new Vector4f(1.5f, 0.5f, 0.5f, 2.0f)),
                        new ParticleStage(0.4f, new Vector4f(0.5f, 0.5f, 2.0f, 2.0f)),
                        new ParticleStage(0.4f, new Vector4f(0.5f, 2.0f, 0.5f, 0.0f))
                };
                ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(0.006f, 1500, 100, true, true, stages);
                light.addProperty(new GraphicsParticleEmitterProperty(new DotParticleSystem(animator, factory, 700)));
                LensFlare flare = new LensFlare(lensTexture, flares);
                new GraphicsLensFlareProperty(flare);
            }


            {
                Component light = new GameSceneComponent(scene);
                TransformProperty lightSourceTransform = new TransformProperty();
                lightSourceTransform.move(new Vector3f(-30f, 0f, 0f));
                light.addProperty(lightSourceTransform);
                ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0, 0.00005f, 0), 0, 0);
                ParticleStage[] stages = {
                        new ParticleStage(1.5f, new Vector4f(3.0f, 0.0f, 0.0f, 0.4f)),
                        new ParticleStage(1.5f, new Vector4f(2.0f, 2.0f, 0.1f, 0.0f)),
                };
                ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(0.01f, 1000, 100, true, true, stages);
                light.addProperty(new GraphicsParticleEmitterProperty(new DotParticleSystem(animator, factory, 400)));
                LensFlare flare = new LensFlare(lensTexture, flares);
                new GraphicsLensFlareProperty(flare);
            }

            {
                Component light = new GameSceneComponent(scene);
                TransformProperty lightSourceTransform = new TransformProperty();
                lightSourceTransform.move(new Vector3f(-30f, 0f, 0f));
                light.addProperty(lightSourceTransform);
                ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0, 0.00003f, 0), 0, 0);
                ParticleStage[] stages = {
                        new ParticleStage(4.0f, new Vector4f(0.5f, 0.5f, 0.5f, 0.0f)),
                        new ParticleStage(4.0f, new Vector4f(0.5f, 0.5f, 0.5f, 0.0f)),
                        new ParticleStage(4.0f, new Vector4f(0.5f, 0.5f, 0.5f, 0.2f)),
                        new ParticleStage(4.0f, new Vector4f(0.5f, 0.5f, 0.5f, 0.0f)),
                };
                ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(0.006f, 2500, 500, true, true, stages);
                light.addProperty(new GraphicsParticleEmitterProperty(new DotParticleSystem(animator, factory, 200)));
                LensFlare flare = new LensFlare(lensTexture, flares);
                new GraphicsLensFlareProperty(flare);
            }

            {
                Component light = new GameSceneComponent(scene);
                TransformProperty lightSourceTransform = new TransformProperty();
                lightSourceTransform.move(new Vector3f(-50f, 0f, 0f));
                light.addProperty(lightSourceTransform);
                ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0, 0.00003f, 0), 0, 0);
                ParticleStage[] stages = {
                        new ParticleStage(4.0f, new Vector4f(0.5f, 0.5f, 0.5f, 0.2f)),
                        new ParticleStage(4.0f, new Vector4f(0.5f, 0.5f, 0.5f, 0.0f)),
                };
                ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(0.006f, 2500, 500, true, true, stages);
                light.addProperty(new GraphicsParticleEmitterProperty(new DotParticleSystem(animator, factory, 200)));
                LensFlare flare = new LensFlare(lensTexture, flares);
                new GraphicsLensFlareProperty(flare);
            }

            generateGOATS(scene);

        });
    }

    @Override
    public GameComponent getCameraComponent() {
        return cameraComponent;
    }

    private void generateGOATS(GameComponent parent) {
        BehaviourTreeBuilder builder = BehaviourTreeLoader.loadXML("data/ai/droneAI.xml");
        ArrayList<Component> team1 = new ArrayList<>();
        ArrayList<Component> team2 = new ArrayList<>();
        //team1.add(controllableGoat);
        controllableGoat.addProperty(new DroneProperty(5, 1, team2));
        int nOfGoats = 50;
        for (int i = 0; i < nOfGoats; i++) {
            GameComponent goat = new GameSceneComponent(parent);
            goat.addProperty(new NameProperty("Ship " + i));
            goat.addProperty(new GraphicsMeshProperty(goatMesh));
            float x = 10 + random.nextFloat() * 200 - 100f;
            float y = random.nextFloat() * 200 - 100f;
            float z = random.nextFloat() * 200 - 100f;
            TransformProperty transformProperty = new TransformProperty();
            transformProperty.move(new Vector3f(x, y, z));
            goat.addProperty(transformProperty);
            SequenceNode basenode = new SequenceNode();
            //basenode.addChildren(new SpinLeaf());
            //BehaviorTree behaviourTree = builder.build(goat);
            if (i < nOfGoats / 2) {
                Material material = new Material(goatTexture);
                material.setShininess(20f);
                material.setBrightnessTexture(goatBrightnessTexture);
                goat.addProperty(new GraphicsMaterialProperty(material));
                goat.addProperty(new PhysicalBodyProperty(10f, 10.772f / 2, 1.8f / 2, 13.443f / 2));
                goat.addProperty(new GunProperty(GUN_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture, audioManager));
                goat.addProperty(new DroneProperty(5, 1, team2));
                team1.add(goat);
            } else {
                transformProperty.move(new Vector3f(0f, 0f, -500f));
                //transformProperty.getRotation().rotateY((float) Math.PI);
                Material material = new Material(goatTexture2);
                material.setShininess(20f);
                material.setBrightnessTexture(goatBrightnessTexture2);
                goat.addProperty(new GraphicsMaterialProperty(material));
                goat.addProperty(new PhysicalBodyProperty(10f, 10.772f / 2f, 1.8f / 2f, 13.443f / 2f));
                goat.addProperty(new GunProperty(GUN_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture2, audioManager));
                goat.addProperty(new DroneProperty(5, 1, team1));
                team2.add(goat);
            }
            goat.addProperty(new DroneMemoryProperty());
            goat.addProperty(new AIProperty(builder.build(goat)));
            new GunScript(goat);
        }
    }


    @Override
    public GameScene getScene() {
        return scene;
    }

    public GameComponent createShip(GameComponent parent) {
        GameComponent goat = new GameSceneComponent(parent);
        goat.addProperty(new GraphicsMeshProperty(goatMesh));
        Material material = new Material(goatTexture);
        material.setShininess(20f);
        material.setBrightnessTexture(goatBrightnessTexture);
        goat.addProperty(new GraphicsMaterialProperty(material));
        goat.addProperty(new PhysicalBodyProperty(10f, 10.772f / 2, 1.8f / 2, 13.443f / 2));
        TransformProperty transformProperty = new TransformProperty();
        goat.addProperty(transformProperty);
        ColliderProperty colliderProperty = new ColliderProperty(new BasicCollider(new btBoxShape(new Vector3(10.772f / 2, 1.8f / 2, 13.443f / 2)), goat, new Vector3f(0.0f, 0, 0), CollisionType.COLLISION_NORMAL, CollisionType.COLLISION_NORMAL));
        goat.addProperty(colliderProperty);
        return goat;
    }

    public GameComponent createFrigate(GameComponent parent) {
        GameComponent frigate = new GameSceneComponent(parent);
        frigate.addProperty(new NameProperty("Frigate"));
        frigate.addProperty(new GraphicsMeshProperty(friageMesh));
        Material frigateMaterial = new Material(frigateTexture);
        frigate.addProperty(new GraphicsMaterialProperty(frigateMaterial));
        frigate.addProperty(new PhysicalBodyProperty(20.0f, 38.365f * 3, 15.1f * 3, 11.9f * 3));
        TransformProperty transformProperty = new TransformProperty();
        frigate.addProperty(transformProperty);
        ColliderProperty colliderProperty = new ColliderProperty(new BasicCollider(new btBoxShape(new Vector3(38.365f * 3, 15.1f * 3, 11.9f * 3)), frigate, new Vector3f(0.0f, 0, 0), CollisionType.COLLISION_NORMAL, CollisionType.COLLISION_NORMAL));
        frigate.addProperty(colliderProperty);
        return frigate;
    }

    public GameComponent createPlanet(GameComponent parent) {
        GameComponent gasSphere = new GameSceneComponent(parent);
        gasSphere.addProperty(new GraphicsMeshProperty(sphere));
        gasSphere.addProperty(new GraphicsCustomRendererProgramProperty(gasProgram));
        TransformProperty gasSphereTransform = new TransformProperty();
        gasSphereTransform.scale(new Vector3f(1000.0f));
        gasSphere.addProperty(gasSphereTransform);
        graphicsThread.scheduleTask(new SimpleEngineTask() {
            @Override
            public void update(int delta) {
                gasProgram.use();
                gasProgram.update(delta);
                gasSphereTransform.rotateLocalY(delta * 0.00001f);
            }
        });

        float startR = 1.5f;
        float endR = 2.5f;
        Component ring = new GameSceneComponent(gasSphere);
        ring.addProperty(new GraphicsMeshProperty(ringMesh));
        ring.addProperty(new GraphicsCustomRendererProgramProperty(planetaryRingProgram));
        ring.addProperty(new PlanetaryRingProperty(startR, endR, ringColors));
        return gasSphere;
    }
}


