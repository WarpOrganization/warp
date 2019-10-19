package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.EngineContext;
import net.warpgame.engine.core.execution.EngineThread;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.runtime.EngineRuntime;
import net.warpgame.engine.graphics.material.MaterialProperty;
import net.warpgame.engine.graphics.material.Texture;
import net.warpgame.engine.graphics.memory.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.memory.scene.mesh.StaticMesh;

import java.io.File;

/**
 * @author MarconZet
 * Created 06.05.2019
 */
public class GraphicsTest {

    private static EngineContext context;
    private static SceneComponent testComponent;

    public static void start(EngineRuntime engineRuntime) {
        System.out.println();
        context = new EngineContext( "graphics", "local");
        context.getLoadedContext().addService(engineRuntime.getIdRegistry());
        SceneHolder sceneHolder = context.getLoadedContext().findOne(SceneHolder.class).get();
        try {
            createScene(sceneHolder.getScene());
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        MaterialProperty property = testComponent.getProperty(Property.getTypeId(MaterialProperty.class));
        Texture tex = property.getTexture();
        while(!tex.isLoaded()){
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
        testComponent = new SceneComponent(scene);

        File meshSource = new File(GraphicsTest.class.getResource("dragon.obj").getFile());
        StaticMesh mesh = new StaticMesh(meshSource);
        MeshProperty meshProperty = new MeshProperty(mesh);
        testComponent.addProperty(meshProperty);

        File texSource = new File(GraphicsTest.class.getResource("tex.png").getFile());
        Texture texture = new Texture(texSource);
        MaterialProperty materialProperty = new MaterialProperty(texture);
        testComponent.addProperty(materialProperty);
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
