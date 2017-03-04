package pl.warp.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
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
import pl.warp.engine.graphics.postprocessing.lens.GraphicsLensFlareProperty;
import pl.warp.engine.graphics.postprocessing.lens.LensFlare;
import pl.warp.engine.graphics.postprocessing.lens.SingleFlare;
import pl.warp.engine.graphics.postprocessing.sunshaft.SunshaftProperty;
import pl.warp.engine.graphics.resource.mesh.ObjLoader;
import pl.warp.engine.graphics.resource.texture.ImageData;
import pl.warp.engine.graphics.resource.texture.ImageDataArray;
import pl.warp.engine.graphics.resource.texture.ImageDecoder;
import pl.warp.engine.graphics.resource.texture.PNGDecoder;
import pl.warp.engine.graphics.shader.program.component.animatedtexture.AnimatedTextureProgram;
import pl.warp.engine.graphics.shader.program.component.terrain.TerrainProgram;
import pl.warp.engine.graphics.skybox.GraphicsSkyboxProperty;
import pl.warp.engine.graphics.terrain.TerrainProperty;
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

    public static GameComponent MAIN_OBJECT;

    private static final float TANK_HULL_ROT_SPEED = 0.3f;
    private static final float TANK_HULL_ACC_SPEED = 0.1f;
    private static final float TANK_HULL_MAX_SPEED = 10f;
    private static final float TANK_HULL_BRAKING_FORCE = 1.5f;
    private static final float TANK_TURRET_ROT_SPEED = 1.5f;
    private static final float TANK_BARREL_ELEVATION_SPEED = 2f;
    private static final float TANK_BARREL_ELEVATION_MAX = 20f;
    private static final float TANK_BARREL_ELEVATION_MIN = -5f;

    private static final int TANK_COOLDOWN = 300;
    private static final float BRAKING_FORCE = 0.2f * 100;
    private static final float ARROWS_ROTATION_SPEED = 2f;
    private static final int GUN_COOLDOWN = 200;

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
    private static boolean SmoothLighting = true;

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
        MAIN_OBJECT = playerTankHull;
        playerTankHull.addProperty(new NameProperty("player tank hull"));
        playerTankHull.addProperty(new PhysicalBodyProperty(10f, 10.772f / 2, 1.8f / 2, 13.443f / 2));
        playerTankHull.addProperty(new GravityProperty(new Vector3f(0, -1, 0)));

        playerTankTurret = new GameSceneComponent(playerTankHull);
        playerTankTurret.addProperty(new NameProperty("player tank turret"));

        playerTankBarrel = new GameSceneComponent(playerTankTurret);
        playerTankBarrel.addProperty(new NameProperty("player tank barrel"));

        playerTankBarrelFake = new GameSceneComponent(playerTankBarrel);
        playerTankBarrelFake.addProperty(new NameProperty("player tank barrel fake"));


        mainCameraComponent = new GameSceneComponent(playerTankBarrel);
        mainCameraComponent.addProperty(new NameProperty("main camera"));

        TransformProperty mainCameraTransform = new TransformProperty();
        mainCameraTransform.rotateY((float)Math.PI);
        mainCameraComponent.addProperty(mainCameraTransform);

        Display display = config.getDisplay();
        Camera mainCamera = new QuaternionCamera(mainCameraComponent, mainCameraTransform, new PerspectiveMatrix(70, 0.01f, 20000f, display.getWidth(), display.getHeight()));
        mainCamera.move(new Vector3f(0, 4f, -9f));
        mainCameraComponent.addProperty(new CameraProperty(mainCamera));


        secondCameraComponent = new GameSceneComponent(playerTankBarrel);
        secondCameraComponent.addProperty(new NameProperty("second camera"));

        TransformProperty secondCameraTransform = new TransformProperty();
        secondCameraTransform.rotateY((float)Math.PI);
        secondCameraComponent.addProperty(secondCameraTransform);

        Camera secondCamera = new QuaternionCamera(secondCameraComponent, secondCameraTransform, new PerspectiveMatrix(70, 0.01f, 20000f, display.getWidth(), display.getHeight()));
        secondCameraComponent.addProperty(new CameraProperty(secondCamera));
    }

    @Override
    public void loadGraphics(EngineThread graphicsThread) {
        graphicsThread.scheduleOnce(() -> {

            ImageDataArray decodedCubemap = ImageDecoder.decodeCubemap("pl/warp/test/clouds");
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
            sunSphereTransform.move(new Vector3f(2000f, 2000f, 5000f));
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

            ImageData decodedTerrainTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("terrain.png"), PNGDecoder.Format.RGBA);
            Texture2D terrainTexture = new Texture2D(decodedTerrainTexture.getWidth(), decodedTerrainTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTerrainTexture.getData());
            Material terrainMaterial = new Material(terrainTexture);
            floorTextureComponent.addProperty(new GraphicsMaterialProperty(terrainMaterial));
            ImageData decodedTerrainNormal = ImageDecoder.decodePNG(Test.class.getResourceAsStream("terrain_normal.png"), PNGDecoder.Format.RGBA);
            Texture2D terrainNormal = new Texture2D(decodedTerrainNormal.getWidth(), decodedTerrainNormal.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTerrainNormal.getData());
            floorTextureComponent.addProperty(new TerrainProperty(new Vector2f(50f), terrainNormal));
            floorTextureComponent.addProperty(new CustomProgramProperty(new TerrainProgram()));
            floor.addProperty(new TransformProperty());
            floor.addProperty(new PhysicalBodyProperty(10000, 1000f, 15, 1000f));

            GameComponent desertTank = createTank("tankModel/DesertTexture.png");
            TransformProperty desertTankTransform =  desertTank.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            desertTankTransform.move(new Vector3f(-300.0f, 0.0f, 0.0f));

            GameComponent plainsTank = createTank("tankModel/WoodlandTexture.png");
            TransformProperty plainsTankTransform =  plainsTank.getProperty(TransformProperty.TRANSFORM_PROPERTY_NAME);
            plainsTankTransform.move(new Vector3f(300.0f, 0.0f, 0.0f));
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
            GameComponent tracks= new GameSceneComponent(playerTankHull);
            GameSceneComponent trackWheels = new GameSceneComponent(playerTankHull);
            GameComponent spinnigWheel = new GameSceneComponent(playerTankHull);
            GameComponent turretAdditions = new GameSceneComponent(playerTankTurret);
            GameComponent minigunStand = new GameSceneComponent(playerTankTurret);
            GameComponent minigun = new GameSceneComponent(playerTankTurret);


            TransformProperty mainTransform = new TransformProperty();
            mainTransform.setScale(new Vector3f(10f,10f,10f));
            playerTankHull.addProperty(mainTransform);

            TransformProperty turretTransform = new TransformProperty();
            playerTankTurret.addProperty(turretTransform);
            //TurretTransform.rotate(0.0f, (float)Math.PI/2, 0.0f);

            TransformProperty mainGunTransform = new TransformProperty();
            playerTankBarrel.addProperty(mainGunTransform);
            mainGunTransform.move(new Vector3f(0.0f, 1.35f, 1.33f));//nie gdzie ci Szymon blender podaje offsety, bo mi poda� g�wno, a offsety robi�em r�cznie
            //MainGunTransform.rotate((float)Math.PI/8, 0.0f, 0.0f);

            TransformProperty mainGunFakeTransform = new TransformProperty();
            playerTankBarrelFake.addProperty(mainGunFakeTransform);
            mainGunFakeTransform.move(new Vector3f(-0.4f,0f,-0.131f));

            TransformProperty spinnigWheelTransform = new TransformProperty();
            spinnigWheel.addProperty(spinnigWheelTransform);
            spinnigWheelTransform.move(new Vector3f(0.0f, 0.66f, -2.44f));

            playerTankHull.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainBody.obj"), SmoothLighting).toMesh()));
            tracks.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Tracks.obj"), SmoothLighting).toMesh()));
            tracks.addProperty(new AnimatedTextureProperty(new Vector2f(0f, 1f)));
            tracks.addProperty(new CustomProgramProperty(new AnimatedTextureProgram()));
            trackWheels.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/TrackWheels.obj"), SmoothLighting).toMesh()));
            spinnigWheel.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/SpinnigWheel.obj"), SmoothLighting).toMesh()));
            playerTankTurret.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Turret.obj"), SmoothLighting).toMesh()));
            turretAdditions.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/TurretAdditions.obj"), SmoothLighting).toMesh()));
            minigunStand.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MinigunStand.obj"), SmoothLighting).toMesh()));
            minigun.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Minigun.obj"), SmoothLighting).toMesh()));
            playerTankBarrel.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainGun.obj"), SmoothLighting).toMesh()));
            playerTankBarrelFake.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainGun.obj"), SmoothLighting).toMesh()));
            playerTankBarrelFake.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME).disable();

            ImageData decodedTankTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tankModel/DesertTexture.png"), PNGDecoder.Format.RGBA);
            Material tankMaterial = new Material(new Texture2D(decodedTankTexture.getWidth(), decodedTankTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTankTexture.getData()));

            playerTankHull.addProperty(getGraphicsProperty(tankMaterial));
            trackWheels.addProperty(getGraphicsProperty(tankMaterial));
            spinnigWheel.addProperty(getGraphicsProperty(tankMaterial));
            playerTankTurret.addProperty(getGraphicsProperty(tankMaterial));
            turretAdditions.addProperty(getGraphicsProperty(tankMaterial));
            minigunStand.addProperty(getGraphicsProperty(tankMaterial));
            minigun.addProperty(getGraphicsProperty(tankMaterial));
            playerTankBarrel.addProperty(getGraphicsProperty(tankMaterial));
            playerTankBarrelFake.addProperty(getGraphicsProperty(tankMaterial));

            ImageData decodedTrackTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tankModel/TracksTexture.png"), PNGDecoder.Format.RGBA);
            tracks.addProperty(new GraphicsMaterialProperty(new Material(new Texture2D(decodedTrackTexture.getWidth(), decodedTrackTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTrackTexture.getData()))));


            ImageDataArray spritesheet = ImageDecoder.decodeSpriteSheetReverse(Test.class.getResourceAsStream("boom_spritesheet.png"), PNGDecoder.Format.RGBA, 4, 4);
            boomSpritesheet = new Texture2DArray(spritesheet.getWidth(), spritesheet.getHeight(), spritesheet.getArraySize(), spritesheet.getData());
            bulletMesh = new Sphere(15, 15, 0.5f);
            ImageData bulletDecodedTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("bullet.png"), PNGDecoder.Format.RGBA);
            bulletTexture = new Texture2D(bulletDecodedTexture.getWidth(), bulletDecodedTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, bulletDecodedTexture.getData());
            playerTankHull.addProperty(new GunProperty(GUN_COOLDOWN, scene, bulletMesh, boomSpritesheet, bulletTexture, audioManager));

            audioManager = AudioManager.INSTANCE;

            //new GunScript(playerTankHull);
            //new TankGunScript(playerTankHull, TANK_COOLDOWN, scene);

            //new GoatControlScript(playerTankHull, MOV_SPEED, ROT_SPEED, BRAKING_FORCE, ARROWS_ROTATION_SPEED);
            //new TankControlScript(playerTankHull, TANK_HULL_ACC_SPEED, TANK_HULL_ROT_SPEED, TANK_HULL_MAX_SPEED, TANK_HULL_BRAKING_FORCE);
            new HullControlScript(playerTankHull, spinnigWheel, tracks, TANK_HULL_ACC_SPEED, TANK_HULL_ROT_SPEED, TANK_HULL_MAX_SPEED, TANK_HULL_BRAKING_FORCE);
            new TurretControlScript(playerTankTurret, TANK_TURRET_ROT_SPEED);
            new BarrelControlScript(playerTankBarrel, TANK_BARREL_ELEVATION_SPEED, TANK_BARREL_ELEVATION_MAX, TANK_BARREL_ELEVATION_MIN);
            new SecondCameraScript(secondCameraComponent,
                    playerTankBarrelFake.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME),
                    playerTankTurret.getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME),
                    mainCameraComponent.getProperty(CameraProperty.CAMERA_PROPERTY_NAME));

            });
    }

    private GameComponent createTank(String texturePath) {
        boolean smoothLighting = true;

        GameComponent mainBody = new GameSceneComponent(scene);
        GameComponent tracks= new GameSceneComponent(mainBody);
        GameSceneComponent trackWheels = new GameSceneComponent(mainBody);
        GameComponent spinnigWheel = new GameSceneComponent(mainBody);

        GameComponent turret = new GameSceneComponent(mainBody);
        GameComponent turretAdditions = new GameSceneComponent(turret);
        GameComponent minigunStand = new GameSceneComponent(turret);
        GameComponent minigun = new GameSceneComponent(turret);
        GameComponent mainGun = new GameSceneComponent(turret);

        TransformProperty mainTransform = new TransformProperty();
        mainBody.addProperty(mainTransform);
        mainTransform.setScale(new Vector3f(100f,100f,100f));

        TransformProperty turretTransform = new TransformProperty();
        turret.addProperty(turretTransform);
        //TurretTransform.rotate(0.0f, (float)Math.PI/2, 0.0f);

        TransformProperty MainGunTransform = new TransformProperty();
        mainGun.addProperty(MainGunTransform);
        MainGunTransform.move(new Vector3f(0.0f, 1.35f, 1.33f));//nie gdzie ci Szymon blender podaje offsety, bo mi podał gówno, a offsety robiłem ręcznie
        //MainGunTransform.rotate((float)Math.PI/8, 0.0f, 0.0f);

        TransformProperty SpinnigWheelTransform = new TransformProperty();
        spinnigWheel.addProperty(SpinnigWheelTransform);
        SpinnigWheelTransform.move(new Vector3f(0.0f, 0.66f, -2.44f));

        mainBody.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainBody.obj"), smoothLighting).toMesh()));
        tracks.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Tracks.obj"), smoothLighting).toMesh()));
        trackWheels.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/TrackWheels.obj"), smoothLighting).toMesh()));
        spinnigWheel.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/SpinnigWheel.obj"), smoothLighting).toMesh()));
        turret.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Turret.obj"), smoothLighting).toMesh()));
        turretAdditions.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/TurretAdditions.obj"), smoothLighting).toMesh()));
        minigunStand.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MinigunStand.obj"), smoothLighting).toMesh()));
        minigun.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/Minigun.obj"), smoothLighting).toMesh()));
        mainGun.addProperty(new RenderableMeshProperty(ObjLoader.read(Test.class.getResourceAsStream("tankModel/MainGun.obj"), smoothLighting).toMesh()));

        ImageData decodedTankTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream(texturePath), PNGDecoder.Format.RGBA);

        Material tankMaterial = new Material(new Texture2D(decodedTankTexture.getWidth(), decodedTankTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTankTexture.getData()));

        mainBody.addProperty(getGraphicsProperty(tankMaterial));
        trackWheels.addProperty(getGraphicsProperty(tankMaterial));
        spinnigWheel.addProperty(getGraphicsProperty(tankMaterial));
        turret.addProperty(getGraphicsProperty(tankMaterial));
        turretAdditions.addProperty(getGraphicsProperty(tankMaterial));
        minigunStand.addProperty(getGraphicsProperty(tankMaterial));
        minigun.addProperty(getGraphicsProperty(tankMaterial));
        mainGun.addProperty(getGraphicsProperty(tankMaterial));

        ImageData decodedTrackTexture = ImageDecoder.decodePNG(Test.class.getResourceAsStream("tankModel/TracksTexture.png"), PNGDecoder.Format.RGBA);
        tracks.addProperty(new GraphicsMaterialProperty(new Material(new Texture2D(decodedTrackTexture.getWidth(), decodedTrackTexture.getHeight(), GL11.GL_RGBA, GL11.GL_RGBA, true, decodedTrackTexture.getData()))));

        return mainBody;
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
}
