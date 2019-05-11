package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.runtime.EngineRuntime;
import net.warpgame.engine.graphics.VulkanTask;
import net.warpgame.engine.graphics.mesh.MeshProperty;
import net.warpgame.engine.graphics.mesh.StaticMesh;

import java.io.File;
import java.net.URISyntaxException;

/**
 * @author MarconZet
 * Created 06.05.2019
 */
public class GraphicsTest {

    private static EngineContext context;

    public static void start(EngineRuntime engineRuntime) {
        System.out.println();
        context = new EngineContext("dev", "graphics", "local");
        context.getLoadedContext().addService(engineRuntime.getIdRegistry());
        SceneHolder sceneHolder = context.getLoadedContext().findOne(SceneHolder.class).get();
        try {
            createScene(sceneHolder.getScene());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        VulkanTask vulkanTask = context.getLoadedContext().findOne(VulkanTask.class).get();
        while(!vulkanTask.isInitialized()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        close();
    }

/*    public static void main(String... arg){
        System.exit(0);
    }*/

    private static void createScene(Component scene){
        SceneComponent testComponent = new SceneComponent(scene);
        try {
            File source = new File(GraphicsTest.class.getResource("dragon.obj").toURI());
            StaticMesh mesh = new StaticMesh(source);
            MeshProperty meshProperty = new MeshProperty(mesh);
            testComponent.addProperty(meshProperty);
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    private static void close() {
        context.getLoadedContext().findAll(EngineThread.class).forEach(EngineThread::interrupt);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
