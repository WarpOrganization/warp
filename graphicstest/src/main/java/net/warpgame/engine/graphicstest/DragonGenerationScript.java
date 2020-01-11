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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author MarconZet
 * Created 11.01.2020
 */
public class DragonGenerationScript extends Script {
    private List<Component> dragonList = new LinkedList<>();
    private Component floor;
    private Random random = new Random();
    private final float min = 0;
    private final float max = 100;
    private long lastDragonTime = 0;
    private long dragonInterval = 5000;

    public DragonGenerationScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        floor = getOwner();
    }

    @Override
    public void onUpdate(int delta) {
        if(System.currentTimeMillis()-lastDragonTime > dragonInterval){
            lastDragonTime = System.currentTimeMillis();
            dragonList.add(createDragon(floor));
        }
    }

    private Component createDragon(Component parent) {
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
        transformProperty.move(0, 3, 0);
        float x = min + random.nextFloat() * (max - min);
        float z = min + random.nextFloat() * (max - min);

        transformProperty.move(x, 10, z);
        dragon.addProperty(transformProperty);

        dragon.addScript(DragonScript.class);

        return dragon;
    }
}
