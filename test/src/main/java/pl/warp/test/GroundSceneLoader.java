package pl.warp.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.audio.MusicSource;
import pl.warp.engine.audio.playlist.PlayList;
import pl.warp.engine.audio.playlist.PlayRandomPlayList;
import pl.warp.engine.core.EngineThread;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.NameProperty;
import pl.warp.engine.core.scene.PoolEventDispatcher;
import pl.warp.engine.core.scene.Property;
import pl.warp.engine.core.scene.input.Input;
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
import pl.warp.engine.graphics.mesh.Mesh;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.engine.graphics.mesh.shapes.QuadMesh;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.particles.ParticleAnimator;
import pl.warp.engine.graphics.particles.ParticleEmitterProperty;
import pl.warp.engine.graphics.particles.ParticleFactory;
import pl.warp.engine.graphics.particles.SimpleParticleAnimator;
import pl.warp.engine.graphics.particles.dot.DotParticle;
import pl.warp.engine.graphics.particles.dot.DotParticleSystem;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.graphics.particles.dot.RandomSpreadingStageDotParticleFactory;
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
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.game.GameContextBuilder;
import pl.warp.game.graphics.effects.star.Star;
import pl.warp.game.graphics.effects.star.StarProperty;
import pl.warp.game.graphics.effects.star.corona.CoronaProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.scene.GameScene;
import pl.warp.game.scene.GameSceneComponent;
import pl.warp.game.scene.GameSceneLoader;
import pl.warp.game.script.GameScript;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * @author MarconZet
 *         Created 2017-02-19 at 11
 *         MAINE PROJECTE
 *         DER TANKEN
 */
public class GroundSceneLoader implements GameSceneLoader {

    private static final float ROT_SPEED = 0.05f;
    private static final float MOV_SPEED = 2.0f;
    private static final float TANK_ROT_SPEED = 0.5f;
    private static final float TANK_ACC_SPEED = 0.1f;
    private static final float TANK_MAX_SPEED = 2f;
    private static final float TANK_BRAKING_FORCE = 1.5f;
    private static final int TANK_COOLDOWN = 300;
    private static final float BRAKING_FORCE = 0.2f * 100;
    private static final float ARROWS_ROTATION_SPEED = 2f;
    private static final int GUN_COOLDOWN = 200;
    public static GameComponent MAIN_OBJECT;

    private RenderingConfig config;
    private GameContextBuilder contextBuilder;
    private GameScene scene;
    private GameSceneComponent playerObject;
    private GameComponent cameraComponent;
    private Mesh bulletMesh;
    private Texture2DArray boomSpritesheet;
    private Texture2D bulletTexture;
    private AudioManager audioManager;
    private Mesh goatMesh;
    private Texture2D goatTexture;
    private Texture2D goatBrightnessTexture;
    private SingleFlare[] flares;
    private Texture2DArray lensTexture;
    private ImageData brightnessTextureData;

    public GroundSceneLoader(RenderingConfig config, GameContextBuilder contextBuilder) {
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
        scene.addProperty(new NameProperty("Build earth"));
        contextBuilder.setScene(scene);
        contextBuilder.setEventDispatcher(new PoolEventDispatcher());


        playerObject = new GameSceneComponent(scene);
        playerObject.addProperty(new NameProperty("Player ship"));
        MAIN_OBJECT = playerObject;
        Display display = config.getDisplay();

        cameraComponent = new GameSceneComponent(playerObject);
        TransformProperty cameraTransform = new TransformProperty();
        cameraComponent.addProperty(cameraTransform);
        Camera camera = new QuaternionCamera(cameraComponent, cameraTransform, new PerspectiveMatrix(70, 0.01f, 20000f, display.getWidth(), display.getHeight()));
        cameraComponent.addProperty(new NameProperty("Camera"));
        cameraComponent.addProperty(new CameraProperty(camera));
        camera.move(new Vector3f(0, 4f, 15f));
        playerObject.addProperty(new PhysicalBodyProperty(10f, 10.772f / 2, 1.8f / 2, 13.443f / 2));
        TransformProperty playerTrasform = new TransformProperty();
        playerObject.addProperty(playerTrasform);
        //playerObject.addProperty(new GravityProperty(new Vector3f(0, -1, 0)));
        playerTrasform.move(new Vector3f(0, 100, 0));
    }

    @Override
    public void loadGraphics(EngineThread graphicsThread) {
        graphicsThread.scheduleOnce(() -> {

            ImageDataArray decodedCubemap = ImageDecoder.decodeCubemap("pl/warp/test/stars3");
            Cubemap cubemap = new Cubemap(decodedCubemap.getWidth(), decodedCubemap.getHeight(), decodedCubemap.getData());
            scene.addProperty(new GraphicsSkyboxProperty(cubemap));


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
                    new SingleFlare(0.6f, 1, 0.25f, new Vector3f(1f))
            };

            Star sun = new Star(scene, 5000f);
            new GameScript<Star>(sun) {

                private StarProperty starProperty;
                private CoronaProperty coronaProperty;

                @Override
                protected void init() {
                    starProperty = sun.getProperty(StarProperty.STAR_PROPERTY_NAME);
                    coronaProperty = sun.getChild(0).getProperty(CoronaProperty.CORONA_PROPERTY_NAME);
                }

                @Override
                protected void update(int delta) {
                    float temperature = starProperty.getTemperature();
                    Input input = getContext().getInput();
                    if (input.isKeyDown(KeyEvent.VK_O)) {
                        temperature += delta * 10;
                        System.out.println("Current temperature: " + temperature);
                    }
                    if (input.isKeyDown(KeyEvent.VK_L)) {
                        temperature = Math.max(4100f, temperature - delta * 10);
                        System.out.println("Current temperature: " + temperature);
                    }
                    starProperty.setTemperature(temperature);
                    coronaProperty.setTemperature(temperature);
                }
            };

            TransformProperty sunSphereTransform = new TransformProperty();
            sunSphereTransform.move(new Vector3f(200f, 16000f, 500f));
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

            GameComponent floor = new GameSceneComponent(scene);
            Component floorTextureComponent = new GameSceneComponent(floor);

            TransformProperty floorTransform = new TransformProperty();
            floorTextureComponent.addProperty(floorTransform);
            floorTransform.scale(new Vector3f(1000f, 1000f, 1000f));
            floorTransform.rotate(-(float) Math.PI / 2, 0, 0);
            floorTransform.move(new Vector3f(0, 15, 0));

            floorTextureComponent.addProperty(new RenderableMeshProperty(new QuadMesh()));

            ImageData decodedFloorTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("floor_1.png"), PNGDecoder.Format.RGBA);
            Texture2D floorTexture = new Texture2D(decodedFloorTexture.getWidth(), decodedFloorTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedFloorTexture.getData());
            Material floorMaterial = new Material(floorTexture);
            floorTextureComponent.addProperty(new GraphicsMaterialProperty(floorMaterial));
            floor.addProperty(new TransformProperty());
            floor.addProperty(new PhysicalBodyProperty(10000, 1000f, 15, 1000f));

            GameComponent tanken = CreateTank(true);

/*
            Vector3f movement = new Vector3f(0f, 100f, -60f);

            GameComponent tank = new GameSceneComponent(scene);

            TransformProperty tankTransform = new TransformProperty();
            tank.addProperty(tankTransform);
            tankTransform.move(movement);
            tankTransform.setScale(new Vector3f(10f, 10f, 10f));

            Mesh tankMesh = ObjLoader.read(Test.class.getResourceAsStream("tank.obj"), false).toVAOMesh();
            RenderableMeshProperty tankRenderableMeshProperty = new RenderableMeshProperty(tankMesh);
            tank.addProperty(tankRenderableMeshProperty);

            ImageData decodedTankTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("free-grey-camouflage-vector.png"), PNGDecoder.Format.BGRA);
            Texture2D tankTexture = new Texture2D(decodedTankTexture.getWidth(), decodedTankTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTankTexture.getData());
            Material tankMaterial = new Material(tankTexture);
            tank.addProperty(new GraphicsMaterialProperty(tankMaterial));

            tank.addProperty(new PhysicalBodyProperty(1, 1, 1, 1));
            tank.addProperty(new GravityProperty(new Vector3f(0, -1, 0)));
*/



            //tank.addProperty(new GunProperty(TANK_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture, audioManager));
            //new TankGunScript(tank, TANK_COOLDOWN);

            goatMesh = ObjLoader.read(Test.class.getResourceAsStream("fighter_1.obj"), false).toVAOMesh();
            RenderableMeshProperty renderableMeshProperty = new RenderableMeshProperty(goatMesh);
            playerObject.addProperty(renderableMeshProperty);

            ImageData decodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_1.png"), PNGDecoder.Format.RGBA);
            goatTexture = new Texture2D(decodedTexture.getWidth(), decodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTexture.getData());
            brightnessTextureData = ImageDecoder.decodePNG(Test.class.getResourceAsStream("fighter_1_brightness.png"), PNGDecoder.Format.RGBA);
            goatBrightnessTexture = new Texture2D(brightnessTextureData.getWidth(), brightnessTextureData.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, brightnessTextureData.getData());
            Material material = new Material(goatTexture);
            material.setBrightnessTexture(goatBrightnessTexture);
            playerObject.addProperty(new GraphicsMaterialProperty(material));

            ImageDataArray spritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            boomSpritesheet = new Texture2DArray(spritesheet.getWidth(), spritesheet.getHeight(), spritesheet.getArraySize(), spritesheet.getData());
            bulletMesh = new Sphere(15, 15, 0.5f);
            ImageData bulletDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("bullet.png"), PNGDecoder.Format.RGBA);
            bulletTexture = new Texture2D(bulletDecodedTexture.getWidth(), bulletDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, bulletDecodedTexture.getData());
            playerObject.addProperty(new GunProperty(GUN_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture, audioManager));

            audioManager = AudioManager.INSTANCE;

            //new GunScript(playerObject);
            //new TankGunScript(playerObject, TANK_COOLDOWN, scene);

            engineParticles(playerObject, new Vector4f(0.2f, 0.5f, 1.0f, 2.0f), new Vector4f(0.2f, 0.5f, 1.0f, 0.0f));
            new GoatControlScript(playerObject, MOV_SPEED, ROT_SPEED, BRAKING_FORCE, ARROWS_ROTATION_SPEED);
            //new TankControlScript(playerObject, TANK_ACC_SPEED, TANK_ROT_SPEED, TANK_MAX_SPEED, TANK_BRAKING_FORCE);
        });
    }

    private GameComponent CreateTank(Boolean Texture) {
        Boolean SmoothLighting = true;

        GameComponent MainBody = new GameSceneComponent(scene);
        GameComponent Tracks= new GameSceneComponent(MainBody);
        GameSceneComponent TrackWheels = new GameSceneComponent(MainBody);
        GameComponent SpinnigWheel = new GameSceneComponent(MainBody);

        GameComponent Turret = new GameSceneComponent(MainBody);
        GameComponent TurretAdditions = new GameSceneComponent(Turret);
        GameComponent MinigunStand = new GameSceneComponent(Turret);
        GameComponent Minigun = new GameSceneComponent(Turret);
        GameComponent MainGun = new GameSceneComponent(Turret);

        TransformProperty MainTransform = new TransformProperty();
        MainBody.addProperty(MainTransform);
        MainTransform.setScale(new Vector3f(100f,100f,100f));

        TransformProperty TurretTransform = new TransformProperty();
        Turret.addProperty(TurretTransform);
        TurretTransform.rotate(0.0f, (float)Math.PI/2, 0.0f);

        TransformProperty MainGunTransform = new TransformProperty();
        MainGun.addProperty(MainGunTransform);
        MainGunTransform.move(new Vector3f(0.0f, 1.35f, 1.33f));//nie gdzie ci szymon blender podaje offsety, bo mi podał gówno, a offsety robiłem ręcznie
        MainGunTransform.rotate((float)Math.PI/8, 0.0f, 0.0f);

        TransformProperty SpinnigWheelTransform = new TransformProperty();
        SpinnigWheel.addProperty(SpinnigWheelTransform);
        SpinnigWheelTransform.move(new Vector3f(0.0f, 0.66f, -2.44f));

        MainBody.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainBody.obj"), SmoothLighting).toMesh()));
        Tracks.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Tracks.obj"), SmoothLighting).toMesh()));
        TrackWheels.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/TrackWheels.obj"), SmoothLighting).toMesh()));
        SpinnigWheel.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/SpinnigWheel.obj"), SmoothLighting).toMesh()));
        Turret.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Turret.obj"), SmoothLighting).toMesh()));
        TurretAdditions.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/TurretAdditions.obj"), SmoothLighting).toMesh()));
        MinigunStand.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MinigunStand.obj"), SmoothLighting).toMesh()));
        Minigun.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Minigun.obj"), SmoothLighting).toMesh()));
        MainGun.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainGun.obj"), SmoothLighting).toMesh()));

        ImageData decodedTankTexture;
        if(Texture) {
            decodedTankTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tankModel/DesertTexture.png"), PNGDecoder.Format.RGBA);
        }else{
            decodedTankTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tankModel/WoodlandTexture.png"), PNGDecoder.Format.RGBA);
        }
        Material TankMaterial = new Material(new Texture2D(decodedTankTexture.getWidth(), decodedTankTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTankTexture.getData()));

        MainBody.addProperty(getGraphicsProperty(TankMaterial));
        TrackWheels.addProperty(getGraphicsProperty(TankMaterial));
        SpinnigWheel.addProperty(getGraphicsProperty(TankMaterial));
        Turret.addProperty(getGraphicsProperty(TankMaterial));
        TurretAdditions.addProperty(getGraphicsProperty(TankMaterial));
        MinigunStand.addProperty(getGraphicsProperty(TankMaterial));
        Minigun.addProperty(getGraphicsProperty(TankMaterial));
        MainGun.addProperty(getGraphicsProperty(TankMaterial));

        ImageData decodedTrackTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tankModel/TracksTexture.png"), PNGDecoder.Format.RGBA);
        Tracks.addProperty(new GraphicsMaterialProperty(new Material(new Texture2D(decodedTrackTexture.getWidth(), decodedTrackTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTrackTexture.getData()))));

        return MainBody;
    }

    @Override
    public GameScene getScene() {
        return scene;
    }

    @Override
    public GameComponent getCameraComponent() {
        return cameraComponent;
    }

    @Override
    public void loadSound(EngineThread audioThread) {
        audioThread.scheduleOnce(() -> {
            AudioManager.INSTANCE.loadFiles("data" + File.separator + "sound" + File.separator + "effects");
            PlayList playList = new PlayRandomPlayList();
/*            playList.add("data" + File.separator + "sound" + File.separator + "music" + File.separator + "Stellardrone-Light_Years-01_Red_Giant.wav");
            playList.add("data" + File.separator + "sound" + File.separator + "music" + File.separator + "Stellardrone-Light_Years-05_In_Time.wav");*/
            MusicSource musicSource = AudioManager.INSTANCE.createMusicSource(new Vector3f(), playList);
            AudioManager.INSTANCE.play(musicSource);
        });
    }

    private Property getGraphicsProperty(Material tankMaterial) {
        return new GraphicsMaterialProperty(tankMaterial);
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

    private void engineParticles(GameComponent goat, Vector4f color, Vector4f color1) {
        Component light = new GameSceneComponent(goat);
        TransformProperty lightSourceTransform = new TransformProperty();
        lightSourceTransform.move(new Vector3f(0f, -0.35f, 3.5f));
        light.addProperty(lightSourceTransform);
        ParticleAnimator animator = new SimpleParticleAnimator(new Vector3f(0.000f, 0.0f, 0.00001f), 0, 0);
        ParticleStage[] stages = {
                new ParticleStage(0.5f, color),
                new ParticleStage(0.5f, color1)
        };
        ParticleFactory<DotParticle> factory = new RandomSpreadingStageDotParticleFactory(new Vector3f(0.004f, 0.0001f, 0f), 400, 100, true, true, stages);
        light.addProperty(new ParticleEmitterProperty(new DotParticleSystem(animator, factory, 300)));
    }

}
