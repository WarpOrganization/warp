package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.runtime.EngineRuntime;
import net.warpgame.engine.graphics.camera.CameraHolder;
import net.warpgame.engine.graphics.camera.CameraProperty;
import net.warpgame.engine.graphics.memory.scene.material.MaterialProperty;
import net.warpgame.engine.graphics.memory.scene.material.Texture;
import net.warpgame.engine.graphics.memory.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.memory.scene.mesh.StaticMesh;
import net.warpgame.engine.graphics.window.Window;
import org.joml.Vector3f;

import java.io.File;
import java.util.List;

/**
 * @author MarconZet
 * Created 06.05.2019
 */
public class GraphicsTest {

    private static EngineContext context;
    private static Component dragon;
    private static Component camera;
    private static Component floor;

    public static void start(EngineRuntime engineRuntime) {
        System.out.println();
        context = new EngineContext( "graphics", "local", "input");
        context.getLoadedContext().addService(engineRuntime.getIdRegistry());
        SceneHolder sceneHolder = context.getLoadedContext().findOne(SceneHolder.class).get();
        try {
            createScene(sceneHolder.getScene());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        Window window = context.getLoadedContext().findOne(Window.class).get();
        try {
            while (!window.shouldClose()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        close();
    }

/*    public static void main(String... arg){
        System.exit(0);
    }*/

    private static void createScene(Component scene){
        camera = createCamera(scene);
        //dragon = createDragon(scene);
        floor = createFloor(scene);
    }

    private static Component createCamera(Component parent){
        Component camera = new SceneComponent(parent);

        Window window = context.getLoadedContext().findOne(Window.class).get();

        CameraProperty cameraProperty = new CameraProperty(CameraProperty.CameraType.PERSPECTIVE, 90f, window.getWidth(), window.getHeight(), 0.1f, 10000f);
        camera.addProperty(cameraProperty);

        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(0,1,0);
        transformProperty.rotateZ((float)Math.PI);
        camera.addProperty(transformProperty);

        camera.addScript(CameraScript.class);

        parent.getContext().getLoadedContext().findOne(CameraHolder.class).get().setCamera(camera);

        return camera;
    }

    private static Component createDragon(Component parent) {
        Component dragon = new SceneComponent(parent);

        File meshSource = new File(GraphicsTest.class.getResource("dragon.obj").getFile());
        StaticMesh mesh = new StaticMesh(meshSource);
        MeshProperty meshProperty = new MeshProperty(mesh);
        dragon.addProperty(meshProperty);

        File texSource = new File(GraphicsTest.class.getResource("tex.png").getFile());
        Texture texture = new Texture(texSource);
        MaterialProperty materialProperty = new MaterialProperty(texture);
        dragon.addProperty(materialProperty);

        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(0, 0, 10);
        transformProperty.scale(new Vector3f(1f));
        transformProperty.rotateLocalX((float)Math.PI);
        transformProperty.rotateZ((float)Math.PI);
        dragon.addProperty(transformProperty);

        dragon.addScript(DragonScript.class);

        return dragon;
    }

    private static Component createFloor(Component parent) {
        Component floor = new SceneComponent(parent);

        File meshSource = new File(GraphicsTest.class.getResource("floor.obj").getFile());
        StaticMesh mesh = new StaticMesh(meshSource);
        MeshProperty meshProperty = new MeshProperty(mesh);
        floor.addProperty(meshProperty);

        File texSource = new File(GraphicsTest.class.getResource("strips.png").getFile());
        Texture texture = new Texture(texSource);
        MaterialProperty materialProperty = new MaterialProperty(texture);
        floor.addProperty(materialProperty);

        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(0, -4, 0);
        floor.addProperty(transformProperty);

        floor.addScript(DragonGenerationScript.class);

        return floor;

    }

    private static void close() {
        List<EngineThread> engineThreads = context.getLoadedContext().findAll(EngineThread.class);
        engineThreads.forEach(EngineThread::interrupt);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
