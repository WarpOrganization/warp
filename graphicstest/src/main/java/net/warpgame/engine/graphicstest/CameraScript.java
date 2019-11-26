package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.input.Input;
import org.joml.Vector3f;

import java.awt.event.KeyEvent;

/**
 * @author MarconZet
 * Created 20.11.2019
 */
public class CameraScript extends Script {
    private static float sensitivity = 1f;
    private static float baseSpeed = 1f;
    private static float sprintSpeedMultiplier = 5f;


    @OwnerProperty(@IdOf(TransformProperty.class))
    TransformProperty transformProperty;
    private Input input;
    public CameraScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        input = getContext().getLoadedContext().findOne(Input.class).get();
    }

    @Override
    public void onUpdate(int delta) {
        if(input.isKeyDown(KeyEvent.VK_LEFT)){
            transformProperty.rotateY((float)(-sensitivity*delta*Math.PI/1000));
        }
        if(input.isKeyDown(KeyEvent.VK_RIGHT)){
            transformProperty.rotateY((float)(sensitivity*delta*Math.PI/1000));
        }
        if(input.isKeyDown(KeyEvent.VK_UP)){
            transformProperty.rotateX((float)(sensitivity*delta*Math.PI/1000));
        }
        if(input.isKeyDown(KeyEvent.VK_DOWN)){
            transformProperty.rotateX((float)(-sensitivity*delta*Math.PI/1000));
        }


        Vector3f movement = new Vector3f();

        if(input.isKeyDown(KeyEvent.VK_W)){
            movement.add(0, 0 ,1);
        }
        if(input.isKeyDown(KeyEvent.VK_S)){
            movement.add(0, 0 ,-1);
        }
        if(input.isKeyDown(KeyEvent.VK_D)){
            movement.add(1, 0 ,0);
        }
        if(input.isKeyDown(KeyEvent.VK_A)){
            movement.add(-1, 0 ,0);
        }
        if(input.isKeyDown(KeyEvent.VK_SPACE)){
            movement.add(0, -1 ,0);
        }
        if(input.isKeyDown(KeyEvent.VK_C)){
            movement.add(0, 1 ,0);
        }

        if(input.isKeyDown(KeyEvent.VK_SHIFT))
            movement.mul(sprintSpeedMultiplier);

        movement.mul(delta/1000f)
                .mul(baseSpeed)
                .rotate(transformProperty.getRotation());
        transformProperty.move(movement);
    }
}
