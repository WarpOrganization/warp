package pl.warp.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import pl.warp.engine.ai.AIProperty;
import pl.warp.engine.ai.loader.BehaviourTreeBuilder;
import pl.warp.engine.ai.loader.BehaviourTreeLoader;
import pl.warp.engine.audio.AudioManager;
import pl.warp.engine.audio.MusicSource;
import pl.warp.engine.audio.playlist.PlayList;
import pl.warp.engine.audio.playlist.PlayRandomPlayList;
import pl.warp.engine.core.execution.EngineThread;
import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.property.NameProperty;
import pl.warp.engine.core.event.PoolEventDispatcher;
import pl.warp.engine.core.property.Property;
import pl.warp.engine.common.transform.TransformProperty;
import pl.warp.engine.graphics.RenderingConfig;
import pl.warp.engine.graphics.animation.AnimatedTextureProperty;
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
import pl.warp.engine.graphics.mesh.shapes.QuadMesh;
import pl.warp.engine.graphics.mesh.shapes.Sphere;
import pl.warp.engine.graphics.particles.*;
import pl.warp.engine.graphics.particles.animator.DirectionalAccelerationAnimator;
import pl.warp.engine.graphics.particles.dot.DotParticleAttribute;
import pl.warp.engine.graphics.particles.dot.ParticleStage;
import pl.warp.engine.graphics.postprocessing.lens.GraphicsLensFlareProperty;
import pl.warp.engine.graphics.postprocessing.lens.LensFlare;
import pl.warp.engine.graphics.postprocessing.lens.SingleFlare;
import pl.warp.engine.graphics.postprocessing.sunshaft.SunshaftProperty;
import pl.warp.engine.graphics.program.rendering.component.animatedtexture.AnimatedTextureProgram;
import pl.warp.engine.graphics.program.rendering.component.terrain.TerrainProgram;
import pl.warp.engine.graphics.resource.mesh.ObjLoader;
import pl.warp.engine.graphics.resource.texture.ImageData;
import pl.warp.engine.graphics.resource.texture.ImageDataArray;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.skybox.GraphicsSkyboxProperty;
import pl.warp.engine.graphics.terrain.TerrainProperty;
import pl.warp.engine.graphics.texture.Cubemap;
import pl.warp.engine.graphics.texture.Texture1D;
import pl.warp.engine.graphics.texture.Texture2D;
import pl.warp.engine.graphics.texture.Texture2DArray;
import pl.warp.engine.graphics.window.Display;
import pl.warp.engine.physics.DupaProperty;
import pl.warp.engine.physics.property.GravityProperty;
import pl.warp.engine.physics.property.PhysicalBodyProperty;
import pl.warp.engine.game.GameContextBuilder;
import pl.warp.engine.game.graphics.effects.star.StarBuilder;
import pl.warp.engine.game.scene.GameComponent;
import pl.warp.engine.game.scene.GameScene;
import pl.warp.engine.game.scene.GameSceneComponent;
import pl.warp.engine.game.scene.GameSceneLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * @author MarconZet
 *         Created 2017-02-19 at 11
 *         MAINE PROJECTE
 *         DER TANKEN
 */
public class GroundSceneLoader implements GameSceneLoader {

    private static final float TANK_HULL_ROT_SPEED = 1.8f;
    private static final float TANK_HULL_ACC_SPEED = 1f;
    private static final float TANK_HULL_MAX_SPEED = 3000f;
    private static final float TANK_HULL_BRAKING_FORCE = 15.0f;
    private static final float TANK_TURRET_ROT_SPEED = 1.5f;
    private static final float TANK_BARREL_ELEVATION_SPEED = 2f;
    private static final float TANK_BARREL_ELEVATION_MAX = 20f;
    private static final float TANK_BARREL_ELEVATION_MIN = -5f;
    private static final float TANK_GUN_OUT_SPEED = 1000f;

    private static final int TANK_COOLDOWN = 300;
    private static final float BRAKING_FORCE = 0;
    private static final float ARROWS_ROTATION_SPEED = 2f;
    private static final int GUN_COOLDOWN = 200;

    private static final boolean smoothLighting = true;

    private RenderingConfig config;
    private GameContextBuilder contextBuilder;

    private GameScene scene;
    private GameComponent mainCameraComponent;
    private GameComponent secondCameraComponent;
    private Mesh bulletMesh;
    private Texture2DArray boomSpritesheet;
    private Texture2D bulletTexture;
    private AudioManager audioManager;
    private SingleFlare[] flares;
    private Texture2DArray lensTexture;
    private GameSceneComponent playerTankHull;
    private GameSceneComponent playerTankTurret;
    private GameSceneComponent playerTankBarrel;
    private GameSceneComponent playerTankBarrelFake;


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


        playerTankHull = new GameSceneComponent(scene);
        playerTankHull.addProperty(new NameProperty("player tank hull"));
        playerTankHull.addProperty(new PhysicalBodyProperty(10f, 10f, 10f, 10f));

        playerTankTurret = new GameSceneComponent(playerTankHull);
        playerTankTurret.addProperty(new NameProperty("player tank turret"));

        playerTankBarrel = new GameSceneComponent(playerTankTurret);
        playerTankBarrel.addProperty(new NameProperty("player tank barrel"));

        playerTankBarrelFake = new GameSceneComponent(playerTankBarrel);
        playerTankBarrelFake.addProperty(new NameProperty("player tank barrel fake"));

        mainCameraComponent = new GameSceneComponent(playerTankBarrel);
        mainCameraComponent.addProperty(new NameProperty("main camera"));


        TransformProperty mainCameraTransform = new TransformProperty();
        mainCameraTransform.rotateY((float) Math.PI);
        mainCameraComponent.addProperty(mainCameraTransform);

        Display display = config.getDisplay();
        Camera mainCamera = new QuaternionCamera(mainCameraComponent, mainCameraTransform, new PerspectiveMatrix(70, 0.01f, 20000f, display.getWidth(), display.getHeight()));
        mainCamera.move(new Vector3f(0, 2f, -9f));
        mainCameraComponent.addProperty(new CameraProperty(mainCamera));


        secondCameraComponent = new GameSceneComponent(playerTankBarrel);
        secondCameraComponent.addProperty(new NameProperty("second camera"));

        TransformProperty secondCameraTransform = new TransformProperty();
        secondCameraTransform.rotateY((float) Math.PI);
        secondCameraComponent.addProperty(secondCameraTransform);

        Camera secondCamera = new QuaternionCamera(secondCameraComponent, secondCameraTransform, new PerspectiveMatrix(70, 0.01f, 20000f, display.getWidth(), display.getHeight()));
        secondCameraComponent.addProperty(new CameraProperty(secondCamera));

    }

    @Override
    public void loadGraphics(EngineThread graphicsThread) {
        graphicsThread.scheduleOnce(() -> {

            ImageDataArray decodedCubemap = ImageDecoder.decodeCubemap("pl/warp/test/clouds2", PNGDecoder.Format.RGBA);
            Cubemap cubemap = new Cubemap(decodedCubemap.getWidth(), decodedCubemap.getHeight(), decodedCubemap.getData());
            scene.addProperty(new GraphicsSkyboxProperty(cubemap, 1.4f));


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

            Texture1D starColors = getStarTemperatureTexture();
            GameComponent sun = new StarBuilder(scene, starColors).build();

            TransformProperty sunSphereTransform = new TransformProperty();
            sunSphereTransform.move(new Vector3f(2000f, 2000f, 5000f));
            sunSphereTransform.scale(new Vector3f(1200f));
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
            floorTransform.scale(new Vector3f(5000f));
            floorTransform.rotate(-(float) Math.PI / 2, 0, 0);
            floorTransform.move(new Vector3f(0, 16.3f, 0));

            floorTextureComponent.addProperty(new RenderableMeshProperty(new QuadMesh()));

            ImageData decodedTerrainTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("terrain.png"), PNGDecoder.Format.RGBA);
            Texture2D terrainTexture = new Texture2D(decodedTerrainTexture.getWidth(), decodedTerrainTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTerrainTexture.getData());
            terrainTexture.enableAnisotropy(8);
            Material terrainMaterial = new Material(terrainTexture);
            floorTextureComponent.addProperty(new GraphicsMaterialProperty(terrainMaterial));
            ImageData decodedTerrainNormal = ImageDecoder.decodePNG(Test.class.getResourceAsStream("terrain_normal.png"), PNGDecoder.Format.RGBA);
            Texture2D terrainNormal = new Texture2D(decodedTerrainNormal.getWidth(), decodedTerrainNormal.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTerrainNormal.getData());
            floorTextureComponent.addProperty(new TerrainProperty(new Vector2f(5 * 150f), terrainNormal));
            floorTextureComponent.addProperty(new CustomProgramProperty(new TerrainProgram()));
            floor.addProperty(new TransformProperty());
            floor.addProperty(new PhysicalBodyProperty(10000, 1000f, 15, 1000f));

            BehaviourTreeBuilder builder = BehaviourTreeLoader.loadXML("data/ai/tankAI.xml");
            ArrayList<Component> targetList1 = new ArrayList<>();
            ArrayList<Component> targetList2 = new ArrayList<>();

            playerTankHull.addProperty(new TransformProperty());
            playerTankHull.addProperty(new TankProperty(true, playerTankTurret, playerTankBarrel));

            GameComponent desertTank = createAiTank(scene, "tankModel/DesertTexture.png", builder, true, targetList1);
            TransformProperty desertTankTransform = desertTank.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            desertTankTransform.move(new Vector3f(-300.0f, 0.0f, 0.0f));
            targetList2.add(playerTankHull);

            GameComponent plainsTank = createAiTank(scene, "tankModel/WoodlandTexture.png", builder, false, targetList2);
            TransformProperty plainsTankTransform = plainsTank.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            plainsTankTransform.move(new Vector3f(300.0f, 0.0f, 0.0f));
            //new TankDestructionParticleManagmentScript(plainsTank);
            targetList1.add(playerTankHull);
            //new TankDestructionParticleManagmentScript(plainsTank);

            ImageDataArray spritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            boomSpritesheet = new Texture2DArray(spritesheet.getWidth(), spritesheet.getHeight(), spritesheet.getArraySize(), spritesheet.getData());
            bulletMesh = new Sphere(15, 15, 0.5f);
            ImageData bulletDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tank_bullet.png"), PNGDecoder.Format.RGBA);
            bulletTexture = new Texture2D(bulletDecodedTexture.getWidth(), bulletDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, bulletDecodedTexture.getData());
            playerTankBarrel.addProperty(new GunProperty(GUN_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture, audioManager));

            playerTankBarrel.addProperty(new TankGunProperty(TANK_COOLDOWN, TANK_GUN_OUT_SPEED, scene));
            playerTankBarrel.addScript(TankGunScript.class);


            createTankModel("tankModel/DesertTexture.png", playerTankHull, playerTankTurret, playerTankBarrel);

            playerTankBarrelFake.addProperty(new TransformProperty());
            ((TransformProperty) playerTankBarrelFake.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME)).move(new Vector3f(-0.4f, 0f, -0.131f));

            playerTankBarrelFake.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainGun.obj"), smoothLighting).toMesh()));
            playerTankBarrelFake.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME).disable();

            ImageData decodedTankTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tankModel/DesertTexture.png"), PNGDecoder.Format.RGBA);
            playerTankBarrelFake.addProperty(getMaterialProperty(new Material(new Texture2D(decodedTankTexture.getWidth(), decodedTankTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTankTexture.getData()))));


            GameComponent city = createCity();

            TransformProperty property = new TransformProperty();
            property.setScale(new Vector3f(70f));
            property.setTranslation(new Vector3f(200, -50, 0));
            property.getRotation().rotate(0, (float) Math.PI + 0.4f, 0);
            city.addProperty(property);

            TransformProperty playerTransform = playerTankHull.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            playerTransform.move(new Vector3f(90f, 0f, 0f));

            playerTankHull.addProperty(new HullProperty(playerTankHull.getChild(3).getChild(0), playerTankHull.getChild(1), TANK_HULL_ACC_SPEED, TANK_HULL_ROT_SPEED, TANK_HULL_MAX_SPEED, TANK_HULL_BRAKING_FORCE));
            playerTankHull.addScript(HullControlScript.class);
            playerTankTurret.addProperty(new TurretProperty(TANK_TURRET_ROT_SPEED));
           playerTankTurret.addScript(TurretControlScript.class);
            playerTankBarrel.addProperty(new BarrelControlProperty(TANK_BARREL_ELEVATION_SPEED, TANK_BARREL_ELEVATION_MAX, TANK_BARREL_ELEVATION_MIN));
            playerTankBarrel.addScript(BarrelControlScript.class);
            secondCameraComponent.addProperty(new SecondCameraProperty(
                    playerTankBarrelFake.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME),
                    playerTankTurret.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME),
                    mainCameraComponent.getProperty(CameraProperty.CAMERA_PROPERTY_NAME)));
            secondCameraComponent.addScript(SecondCameraScript.class);

            audioManager = AudioManager.INSTANCE;
        });
    }

    private static Texture1D getStarTemperatureTexture() {
        ImageData starTemperatureImage = ImageDecoder.decodePNG(StarBuilder.class.getResourceAsStream("star_temperature.png"), PNGDecoder.Format.RGBA);
        return new Texture1D(starTemperatureImage.getWidth(), GL11.GL_RGBA, GL11.GL_RGBA, false, starTemperatureImage.getData());
    }

    protected GameComponent createCity() {
        GameComponent city = new GameSceneComponent(scene);
        GameComponent city0 = new GameSceneComponent(city);
        city0.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("city0.obj"), false).toVAOMesh()));
        ImageData city0texture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("ang1.png"), PNGDecoder.Format.RGBA);
        city0.addProperty(getMaterialProperty(new Material(new Texture2D(city0texture.getWidth(), city0texture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, city0texture.getData()))));

        GameComponent city1 = new GameSceneComponent(city);
        city1.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("city1.obj"), false).toVAOMesh()));
        ImageData city1texture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("cty2x.png"), PNGDecoder.Format.RGBA);
        city1.addProperty(getMaterialProperty(new Material(new Texture2D(city1texture.getWidth(), city1texture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, city1texture.getData()))));

        GameComponent city2 = new GameSceneComponent(city);
        city2.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("city2.obj"), false).toVAOMesh()));
        ImageData city2texture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("cty1.png"), PNGDecoder.Format.RGBA);
        city2.addProperty(getMaterialProperty(new Material(new Texture2D(city2texture.getWidth(), city2texture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, city2texture.getData()))));

        return city;
    }

    private GameComponent createAiTank(GameComponent parent, String texturePath, BehaviourTreeBuilder builder, boolean team, ArrayList<Component> targets) {

        GameComponent mainBody = new GameSceneComponent(parent);
        GameComponent turret = new GameSceneComponent(mainBody);
        GameComponent mainGun = new GameSceneComponent(turret);

        GameComponent hull = createTankModel(texturePath, mainBody, turret, mainGun);
        TankProperty tankProperty = new TankProperty(team, turret, mainGun);
        tankProperty.setTargets(targets);
        hull.addProperty(tankProperty);
        hull.addProperty(new AIProperty(builder.build(hull)));
        hull.addProperty(new DupaProperty(turret, mainGun));
        return hull;
    }

    private GameComponent createTankModel(String texturePath, GameComponent mainBody, GameComponent turret, GameComponent mainGun) {

        GameComponent tracks = new GameSceneComponent(mainBody);
        GameSceneComponent trackWheels = new GameSceneComponent(mainBody);
        GameComponent spinnigWheelParent = new GameSceneComponent(mainBody);
        GameComponent spinnigWheel = new GameSceneComponent(spinnigWheelParent);
        GameComponent turretAdditions = new GameSceneComponent(turret);
        GameComponent minigunStand = new GameSceneComponent(turret);
        GameComponent minigun = new GameSceneComponent(turret);


        TransformProperty mainTransform = new TransformProperty();
        mainTransform.setScale(new Vector3f(10f, 10f, 10f));
        mainTransform.move(new Vector3f(0, 100, 0));
        mainBody.addProperty(mainTransform);


        TransformProperty turretTransform = new TransformProperty();
        turret.addProperty(turretTransform);

        TransformProperty mainGunTransform = new TransformProperty();
        mainGun.addProperty(mainGunTransform);
        mainGunTransform.move(new Vector3f(0.0f, 1.35f, 1.33f));

        TransformProperty spinnigWheelTransform = new TransformProperty();
        spinnigWheelParent.addProperty(spinnigWheelTransform);
        spinnigWheelTransform.move(new Vector3f(0.0f, 0.66f, -2.44f));
        spinnigWheel.addProperty(new TransformProperty());

        mainBody.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainBody.obj"), smoothLighting).toMesh()));
        tracks.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Tracks.obj"), smoothLighting).toMesh()));
        tracks.addProperty(new AnimatedTextureProperty(new Vector2f(0f, 1f)));
        tracks.addProperty(new CustomProgramProperty(new AnimatedTextureProgram()));
        trackWheels.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/TrackWheels.obj"), smoothLighting).toMesh()));
        spinnigWheel.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/SpinnigWheel.obj"), smoothLighting).toMesh()));
        turret.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Turret.obj"), smoothLighting).toMesh()));
        turretAdditions.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/TurretAdditions.obj"), smoothLighting).toMesh()));
        minigunStand.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MinigunStand.obj"), smoothLighting).toMesh()));
        minigun.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Minigun.obj"), smoothLighting).toMesh()));
        mainGun.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainGun.obj"), smoothLighting).toMesh()));

        ImageData decodedTankTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream(texturePath), PNGDecoder.Format.RGBA);
        Material tankMaterial = new Material(new Texture2D(decodedTankTexture.getWidth(), decodedTankTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTankTexture.getData()));

        mainBody.addProperty(getMaterialProperty(tankMaterial));
        trackWheels.addProperty(getMaterialProperty(tankMaterial));
        spinnigWheel.addProperty(getMaterialProperty(tankMaterial));
        turret.addProperty(getMaterialProperty(tankMaterial));
        turretAdditions.addProperty(getMaterialProperty(tankMaterial));
        minigunStand.addProperty(getMaterialProperty(tankMaterial));
        minigun.addProperty(getMaterialProperty(tankMaterial));
        mainGun.addProperty(getMaterialProperty(tankMaterial));

        ImageData decodedTrackTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tankModel/TracksTexture.png"), PNGDecoder.Format.RGBA);
        tracks.addProperty(new GraphicsMaterialProperty(new Material(new Texture2D(decodedTrackTexture.getWidth(), decodedTrackTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTrackTexture.getData()))));

        mainBody.addProperty(new PhysicalBodyProperty(10f, 10.772f / 2, 1.8f / 2, 13.443f / 2));
        mainBody.addProperty(new GravityProperty(new Vector3f(0, -1, 0)));


        createTracksParticles(tracks);
        turret.addProperty(new PhysicalBodyProperty(1, 1, 1, 1));



        return mainBody;
    }

    private void createTracksParticles(GameComponent tracks) {
        GameComponent source1 = new GameSceneComponent(tracks);
        TransformProperty transform1 = new TransformProperty();
        transform1.move(new Vector3f(0.9f, 0f, -1.75f));
        source1.addProperty(transform1);
        createTrackParticles(source1);

        GameComponent source2 = new GameSceneComponent(tracks);
        TransformProperty transform2 = new TransformProperty();
        transform2.move(new Vector3f(-0.9f, 0f, -1.75f));
        source2.addProperty(transform2);
        createTrackParticles(source2);
    }

    private void createTrackParticles(GameComponent component) {
        ParticleAnimator dustAnimator = new DirectionalAccelerationAnimator(new Vector3f(0, -0.00003f, 0));
        ParticleStage[] firedSmokeStages = {
                new ParticleStage(0.03f, new Vector4f(1.2f, 1.0f, 1.0f, 1.0f)),
                new ParticleStage(0.03f, new Vector4f(1.2f, 1.0f, 1.0f, 1.0f)),
                new ParticleStage(0.03f, new Vector4f(1.2f, 1.0f, 1.0f, 0.0f)),
        };
        ParticleEmitter emitter = new SpreadingParticleEmitter(400, new Vector3f(0, 0.001f, -0.004f), new Vector3f(0.004f), 500, 200, true);
        component.addProperty(new ParticleEmitterProperty(new ParticleSystem(new DotParticleAttribute(firedSmokeStages), emitter, dustAnimator)));
    }




    @Override
    public GameScene getScene() {
        return scene;
    }

    public GameComponent getMainCameraComponent() {
        return mainCameraComponent;
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

    private Property getMaterialProperty(Material material) {
        return new GraphicsMaterialProperty(material);
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
}
