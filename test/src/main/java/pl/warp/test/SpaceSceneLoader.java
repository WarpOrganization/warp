package pl.warp.test;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.ai.AIProperty;
import pl.warp.engine.ai.behaviortree.SequenceNode;
import pl.warp.engine.ai.loader.BehaviourTreeBuilder;
import pl.warp.engine.ai.loader.BehaviourTreeLoader;
import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.audio.MusicSource;
import pl.warp.engine.audio.playlist.PlayList;
import pl.warp.engine.audio.playlist.PlayRandomPlayList;
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
import pl.warp.engine.graphics.light.LightSourceProperty;
import pl.warp.engine.graphics.light.SpotLight;
import pl.warp.engine.graphics.material.GraphicsMaterialProperty;
import pl.warp.engine.graphics.material.Material;
import pl.warp.engine.graphics.math.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.mesh.CustomProgramProperty;
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.Ring;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.particles.*;
import pl.warp.engine.graphics.particles.animator.DirectionalAccelerationAnimator;
import pl.warp.engine.graphics.particles.dot.DotParticle;
import pl.warp.engine.graphics.particles.dot.DotParticleAttribute;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.graphics.postprocessing.lens.GraphicsLensFlareProperty;
import pl.warp.engine.graphics.postprocessing.lens.LensFlare;
import pl.warp.engine.graphics.postprocessing.lens.SingleFlare;
import pl.warp.engine.graphics.postprocessing.sunshaft.SunshaftProperty;
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
import pl.warp.game.graphics.effects.gas.GasPlanet;
import pl.warp.game.graphics.effects.gas.GasPlanetProgram;
import pl.warp.game.graphics.effects.ring.PlanetRing;
import pl.warp.game.graphics.effects.ring.PlanetRingProgram;
import pl.warp.game.graphics.effects.ring.PlanetRingProperty;
import pl.warp.game.graphics.effects.star.Star;
import pl.warp.game.graphics.effects.star.StarProgram;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.scene.GameSceneLoader;
import pl.warp.test.ai.DroneMemoryProperty;

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
 *         CANCER CODE, ONLY FOR TESTING
 *         TODO KILL IT WITH FIRE
 */
public class SpaceSceneLoader implements GameSceneLoader {

    private static final float ROT_SPEED = 0.05f;
    private static final float MOV_SPEED = 2.0f;
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
    private PlanetRingProgram planetRingProgram;
    private ImageData brightnessTextureData;
    private ImageData brightnessTextureData2;
    private Texture2D goatTexture2;
    private Texture2D bulletTexture2;
    private Texture2D goatBrightnessTexture2;
    private StarProgram starProgram;
    private GameSceneComponent enemyPortal;
    private GameSceneComponent allyPortal;
    private GasPlanet gasPlanet;


    public SpaceSceneLoader(RenderingConfig config, GameContextBuilder contextBuilder) {
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

    @Override
    public void loadGraphics(EngineThread graphicsThread) {
        graphicsThread.scheduleOnce(() -> {

            this.graphicsThread = graphicsThread;
            //new ComponentLoggingScript(controllableGoat);
            goatMesh = ObjLoader.read(Test.class.getResourceAsStream("sw_fighter.obj"), false).toVAOMesh();
            ImageData decodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("sw_fighter.png"), PNGDecoder.Format.RGBA);
            goatTexture = new Texture2D(decodedTexture.getWidth(), decodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTexture.getData());

            ImageData decodedTexture2 = ImageDecoder.decodePNG(Test.class.getResourceAsStream("sw_fighter.png"), PNGDecoder.Format.RGBA);
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
            };


            ImageData decodedColorsTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("gas.png"), PNGDecoder.Format.RGBA);
            colorsTexture = new Texture1D(decodedColorsTexture.getWidth(), GL11.GL_RGBA, GL11.GL_RGBA, false, decodedColorsTexture.getData());
            gasPlanet = new GasPlanet(scene, colorsTexture);
            TransformProperty gasSphereTransform = gasPlanet.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            gasSphereTransform.move(new Vector3f(-1600f, -200f, -500f));
            gasSphereTransform.scale(new Vector3f(1000.0f));


            float startR = 1.5f;
            float endR = 2.5f;

            ImageData ringColorsData = ImageDecoder.decodePNG(Test.class.getResourceAsStream("ring_colors.png"), PNGDecoder.Format.RGBA);
            ringColors = new Texture1D(ringColorsData.getWidth(), GL11.GL_RGBA, GL11.GL_RGBA, true, ringColorsData.getData());
            ringColors.enableAnisotropy(4);

            PlanetRing ring = new PlanetRing(gasPlanet, startR, endR, ringColors);

            Star sun = new Star(scene, 5000f);
            TransformProperty sunSphereTransform = new TransformProperty();
            sunSphereTransform.move(new Vector3f(10000f, 200f, 500f));
            sunSphereTransform.scale(new Vector3f(2000.0f));
            sun.addProperty(sunSphereTransform);
            SpotLight spotLight = new SpotLight(sun, new Vector3f(0), new Vector3f(1.0f).mul(4), new Vector3f(1.0f).mul(0.3f), 0.00001f, 0.0001f);
            LightSourceProperty lightSourceProperty = new LightSourceProperty();
            sun.addProperty(lightSourceProperty);
            lightSourceProperty.addSpotLight(spotLight);
            LensFlare flare = new LensFlare(lensTexture, flares);
            GraphicsLensFlareProperty flareProperty = new GraphicsLensFlareProperty(flare);
            sun.addProperty(flareProperty);
            scene.<SunshaftProperty>getPropertyIfExists(SunshaftProperty.SUNSHAFT_PROPERTY_NAME).ifPresent(p -> p.getSource().setComponent(sun));

            brightnessTextureData = ImageDecoder.decodePNG(Test.class.getResourceAsStream("sw_fighter_brightness.png"), PNGDecoder.Format.RGBA);
            goatBrightnessTexture = new Texture2D(brightnessTextureData.getWidth(), brightnessTextureData.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, brightnessTextureData.getData());

            brightnessTextureData2 = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_2_brightness.png"), PNGDecoder.Format.RGBA);
            goatBrightnessTexture2 = new Texture2D(brightnessTextureData2.getWidth(), brightnessTextureData2.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, brightnessTextureData2.getData());

            ImageDataArray spritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            boomSpritesheet = new Texture2DArray(spritesheet.getWidth(), spritesheet.getHeight(), spritesheet.getArraySize(), spritesheet.getData());

            RenderableMeshProperty renderableMeshProperty = new RenderableMeshProperty(goatMesh);
            controllableGoat.addProperty(renderableMeshProperty);
            //renderableMeshProperty.disable();
            allyEngineParticles(controllableGoat);

            new KabooomScript(controllableGoat, gasPlanet, 1000.0f);

            Material material = new Material(goatTexture);
            material.setBrightnessTexture(goatBrightnessTexture);
            controllableGoat.addProperty(new GraphicsMaterialProperty(material));

            bulletMesh = ObjLoader.read(Test.class.getResourceAsStream("bullet.obj"), false).toVAOMesh();
            ImageData bulletDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("bullet.png"), PNGDecoder.Format.RGBA);
            bulletTexture = new Texture2D(bulletDecodedTexture.getWidth(), bulletDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, bulletDecodedTexture.getData());
            controllableGoat.addProperty(new GunProperty(GUN_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture, audioManager));
            audioManager = AudioManager.INSTANCE;

            ImageData bullet2decodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("bullet_2.png"), PNGDecoder.Format.RGBA);
            bulletTexture2 = new Texture2D(bullet2decodedTexture.getWidth(), bullet2decodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, bullet2decodedTexture.getData());

            new GunScript(controllableGoat);

            ImageDataArray decodedCubemap = ImageDecoder.decodeCubemap("pl/warp/test/stars3", PNGDecoder.Format.RGBA);
            Cubemap cubemap = new Cubemap(decodedCubemap.getWidth(), decodedCubemap.getHeight(), decodedCubemap.getData());
            scene.addProperty(new GraphicsSkyboxProperty(cubemap, 1f));

            allyPortal = new GameSceneComponent(scene);
            TransformProperty allyPortalTransform = new TransformProperty();
            allyPortal.addProperty(allyPortalTransform);
            allyPortalTransform.move(new Vector3f(0, 0, 200));
            {
                ParticleStage[] stages = {
                        new ParticleStage(10.0f, new Vector4f(0.2f, 0.5f, 1.0f, 1.0f)),
                        new ParticleStage(10.0f, new Vector4f(0.2f, 0.5f, 1.0f, 0.0f))
                };
                ParticleEmitter<DotParticle> emitter = new SpreadingParticleEmitter<>(50, new Vector3f(0), new Vector3f(.01f, .01f, 0f), 1000, 100, true);
                allyPortal.addProperty(new ParticleEmitterProperty(new ParticleSystem(new DotParticleAttribute(stages), emitter)));
            }

            enemyPortal = new GameSceneComponent(scene);
            TransformProperty enemyPortalTransform = new TransformProperty();
            enemyPortal.addProperty(enemyPortalTransform);
            enemyPortalTransform.move(new Vector3f(0, 0, -600));
            {
                ParticleStage[] stages = {
                        new ParticleStage(10.0f, new Vector4f(1.0f, 0.2f, 0.2f, 1.0f)),
                        new ParticleStage(10.0f, new Vector4f(1.0f, 0.2f, 0.2f, 0.0f))
                };
                ParticleEmitter<DotParticle> emitter = new SpreadingParticleEmitter<>(50, new Vector3f(0), new Vector3f(.01f, .01f, 0f), 1000, 100, true);
                enemyPortal.addProperty(new ParticleEmitterProperty(new ParticleSystem(new DotParticleAttribute(stages), emitter)));
            }

            friageMesh = ObjLoader.read(GunScript.class.getResourceAsStream("frigate_1_heavy.obj"), false).toVAOMesh();
            ImageData frigateDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("frigate_1_heavy.png"), PNGDecoder.Format.RGBA);
            frigateTexture = new Texture2D(frigateDecodedTexture.getWidth(), frigateDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, frigateDecodedTexture.getData());
            Material frigateMaterial = new Material(frigateTexture);
            frigateMaterial.setShininess(20);
            ImageData frigateBrightnessDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("frigate_1_heavy_brightness.png"), PNGDecoder.Format.RGBA);
            frigateBrightnessTexture = new Texture2D(frigateBrightnessDecodedTexture.getWidth(), frigateBrightnessDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, frigateBrightnessDecodedTexture.getData());
            frigateMaterial.setBrightnessTexture(frigateBrightnessTexture);
            GameComponent frigate = new GameSceneComponent(scene);
            allyEngineParticles(frigate);
            frigate.addProperty(new NameProperty("Frigate"));
            frigate.addProperty(new RenderableMeshProperty(friageMesh));
            frigate.addProperty(new GraphicsMaterialProperty(frigateMaterial));
            //frigate.addProperty(new PhysicalBodyProperty(10000.0f, 38.365f, 15.1f, 11.9f));
            TransformProperty frigateTransform = new TransformProperty();
            frigateTransform.move(new Vector3f(100, 0, 0));
            frigateTransform.scale(new Vector3f(3));
            frigateTransform.rotateY((float) (Math.PI / 2));
            frigate.addProperty(frigateTransform);



            generateGOATS(scene);
            //spawnFrigates();
            new GoatControlScript(controllableGoat, MOV_SPEED, ROT_SPEED, BRAKING_FORCE, ARROWS_ROTATION_SPEED);
        });
    }

    @Override
    public GameScene getScene() {
        return scene;
    }

    public GameComponent getMainCameraComponent() {
        return cameraComponent;
    }

    @Override
    public void loadSound(EngineThread audioThread) {
        audioThread.scheduleOnce(() -> {
            AudioManager.INSTANCE.loadFiles("data" + File.separator + "sound" + File.separator + "effects");
            PlayList playList = new PlayRandomPlayList();
            playList.add("data" + File.separator + "sound" + File.separator + "music" + File.separator + "Stellardrone-Light_Years-01_Red_Giant.wav");
            playList.add("data" + File.separator + "sound" + File.separator + "music" + File.separator + "Stellardrone-Light_Years-05_In_Time.wav");
            MusicSource musicSource = AudioManager.INSTANCE.createMusicSource(new Vector3f(), playList);
            AudioManager.INSTANCE.play(musicSource);
        });
    }

    public GameComponent createShip(GameComponent parent) {
        GameComponent goat = new GameSceneComponent(parent);
        goat.addProperty(new RenderableMeshProperty(goatMesh));
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
        frigate.addProperty(new RenderableMeshProperty(friageMesh));
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
        gasSphere.addProperty(new RenderableMeshProperty(sphere));
        gasSphere.addProperty(new CustomProgramProperty(gasProgram));
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
        ring.addProperty(new RenderableMeshProperty(ringMesh));
        ring.addProperty(new CustomProgramProperty(planetRingProgram));
        ring.addProperty(new PlanetRingProperty(startR, endR, ringColors, true));
        return gasSphere;
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


    private void generateGOATS(GameComponent parent) {
        BehaviourTreeBuilder builder = BehaviourTreeLoader.loadXML("data/ai/droneAI.xml");
        ArrayList<Component> team1 = new ArrayList<>();
        ArrayList<Component> team2 = new ArrayList<>();
        team1.add(controllableGoat);
        controllableGoat.addProperty(new DroneProperty(5, 1, team2, allyPortal));
        int nOfGoats = 30;
        for (int i = 0; i < nOfGoats; i++) {
            GameComponent goat = new GameSceneComponent(parent);
            new KabooomScript(goat, gasPlanet, 1000.0f);
            goat.addProperty(new NameProperty("Ship " + i));
            goat.addProperty(new RenderableMeshProperty(goatMesh));
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
                goat.addProperty(new DroneProperty(5, 1, team2, allyPortal));
                team1.add(goat);
                allyEngineParticles(goat);
            } else {
                transformProperty.move(new Vector3f(0f, 0f, -500f));
                //transformProperty.getRotation().rotateY((float) Math.PI);
                Material material = new Material(goatTexture2);
                material.setShininess(20f);
                material.setBrightnessTexture(goatBrightnessTexture2);
                goat.addProperty(new GraphicsMaterialProperty(material));
                goat.addProperty(new PhysicalBodyProperty(10f, 10.772f / 2f, 1.8f / 2f, 13.443f / 2f));
                goat.addProperty(new GunProperty(GUN_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture2, audioManager));
                goat.addProperty(new DroneProperty(5, 1, team1, enemyPortal));
                team2.add(goat);
                enemyEngineParticles(goat);
            }
            goat.addProperty(new DroneMemoryProperty());
            goat.addProperty(new AIProperty(builder.build(goat)));
            new GunScript(goat);
        }
    }

    private void allyEngineParticles(GameComponent goat) {
        engineParticles(goat, new Vector4f(0.2f, 0.5f, 1.0f, 0.5f), new Vector4f(0.2f, 0.5f, 1.0f, 0.0f));
    }

    private void enemyEngineParticles(GameComponent goat) {
        engineParticles(goat, new Vector4f(1.0f, 0.5f, 0.2f, 0.5f), new Vector4f(1.0f, 0.5f, 0.2f, 0.0f));
    }

    private void engineParticles(GameComponent goat, Vector4f color, Vector4f color1) {
        {
            Component particles = new GameSceneComponent(goat);
            TransformProperty lightSourceTransform = new TransformProperty();
            lightSourceTransform.move(new Vector3f(-2.1f, 0.3f, -1f));
            particles.addProperty(lightSourceTransform);
            ParticleAnimator animator = new DirectionalAccelerationAnimator(new Vector3f(0f, 0.0f, 0.00001f));
            ParticleStage[] stages = {
                    new ParticleStage(1.3f, color),
                    new ParticleStage(1.3f, color1)
            };
            ParticleEmitter emitter = new SpreadingParticleEmitter(70, new Vector3f(0), new Vector3f(0.00001f, 0.00001f, 0f), 1150, 500, true);
            particles.addProperty(new ParticleEmitterProperty(new ParticleSystem(new DotParticleAttribute(stages), emitter, animator)));
        }
        {
            Component particles = new GameSceneComponent(goat);
            TransformProperty lightSourceTransform = new TransformProperty();
            lightSourceTransform.move(new Vector3f(2.1f, 0.3f, -1f));
            particles.addProperty(lightSourceTransform);
            ParticleAnimator animator = new DirectionalAccelerationAnimator(new Vector3f(0f, 0.0f, 0.00001f));
            ParticleStage[] stages = {
                    new ParticleStage(1.3f, color),
                    new ParticleStage(1.3f, color1)
            };
            ParticleEmitter emitter = new SpreadingParticleEmitter(70, new Vector3f(0), new Vector3f(0.00001f, 0.00001f, 0f), 1150, 500, true);
            particles.addProperty(new ParticleEmitterProperty(new ParticleSystem(new DotParticleAttribute(stages), emitter, animator)));
        }
    }
}


