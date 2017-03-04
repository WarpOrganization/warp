package pl.warp.test;

import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;
import pl.warp.game.script.OwnerProperty;

import javax.naming.Context;
import java.awt.event.KeyEvent;

/**
 * Created by Marcin on 04.03.2017.
 */
public class SecondCameraScript extends GameScript<GameComponent>{

    @OwnerProperty(name = CameraProperty.CAMERA_PROPERTY_NAME)
    private  CameraProperty secondCameraProperty;

    private CameraProperty mainCameraProperty;
    private RenderableMeshProperty trueGun;
    private RenderableMeshProperty fakeGun;

    public SecondCameraScript(GameComponent owner, RenderableMeshProperty fakeGun, CameraProperty mainCameraProperty) {
        super(owner);
        this.mainCameraProperty = mainCameraProperty;
        this.fakeGun = fakeGun;
    }

    @Override
    protected void init() {
        trueGun = this.getOwner().getParent().getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME);
    }

    @Override
    protected void update(int delta) {
        Input input = getContext().getInput();

        if(input.getScrollDelta() < 0){
            trueGun.disable();
            fakeGun.enable();
            this.getContext().getGraphics().setMainViewCamera(secondCameraProperty.getCamera());
        }else if(input.getScrollDelta() > 0){
            trueGun.enable();
            fakeGun.disable();
            this.getContext().getGraphics().setMainViewCamera(mainCameraProperty.getCamera());
        }

    }
}
