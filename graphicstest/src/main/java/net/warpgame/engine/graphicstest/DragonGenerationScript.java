package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.graphics.memory.scene.material.MaterialProperty;
import net.warpgame.engine.graphics.memory.scene.material.Texture;
import net.warpgame.engine.graphics.memory.scene.mesh.MeshProperty;
import net.warpgame.engine.graphics.memory.scene.mesh.StaticMesh;

import java.io.File;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author MarconZet
 * Created 11.01.2020
 */
public class DragonGenerationScript extends Script {
    private Deque<Component> dragonList = new LinkedList<>();
    private Component floor;
    private Random random = new Random();
    private final float min = 0;
    private final float max = 100;
    private long lastDragonTime = 0;
    private long dragonInterval = 5000;
    private StaticMesh mesh;
    private Texture texture;

    public DragonGenerationScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        floor = getOwner();
        prepareStaticResources();
        for (int i = 0; i < 5; i++) {
            dragonList.add(createDragon(floor));
        }
        lastDragonTime = System.currentTimeMillis();

    }

    private void prepareStaticResources() {
        File meshSource = new File(GraphicsTest.class.getResource("dragon.obj").getFile());
        mesh = new StaticMesh(meshSource);

        File texSource = new File(GraphicsTest.class.getResource("tex.png").getFile());
        texture = new Texture(texSource);
    }

    @Override
    public void onUpdate(int delta) {
        if(System.currentTimeMillis()-lastDragonTime > dragonInterval){
            lastDragonTime = System.currentTimeMillis();
            Component dragon = dragonList.pollLast();
            if(dragon != null) {
                dragon.destroy();
                System.gc();
            }
            /*dragonList.add(createDragon(floor));
            dragonList.pollFirst().destroy();*/
        }
    }

    private Component createDragon(Component parent) {
        Component dragon = new SceneComponent(parent);

        dragon.addProperty(new MeshProperty(mesh));
        dragon.addProperty(new MaterialProperty(texture));

        TransformProperty transformProperty = new TransformProperty();
        transformProperty.move(0, 3, 0);
        float x = min + random.nextFloat() * (max - min);
        float z = min + random.nextFloat() * (max - min);

        transformProperty.move(x, 10, z);
        dragon.addProperty(transformProperty);

        //dragon.addScript(DragonScript.class);

        return dragon;
    }
}
