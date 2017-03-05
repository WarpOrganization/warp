package pl.warp.test;

import pl.warp.engine.core.scene.input.Input;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.math.projection.PerspectiveMatrix;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;
import pl.warp.game.scene.GameComponent;
import pl.warp.game.script.GameScript;
import pl.warp.game.script.GameScriptWithInput;
import pl.warp.game.script.OwnerProperty;

import java.awt.event.MouseEvent;

/**
 * Created by Marcin on 04.03.2017.
 */
public class SecondCameraScript extends GameScriptWithInput<GameComponent>{

    @OwnerProperty(name = CameraProperty.CAMERA_PROPERTY_NAME)
    private  CameraProperty secondCameraProperty;

    private CameraProperty mainCameraProperty;
    private RenderableMeshProperty trueGun;
    private RenderableMeshProperty fakeGun;
    private RenderableMeshProperty turret;
    private PerspectiveMatrix secondCameraPerspectiveMatrix;

    private int currState;


    public SecondCameraScript(GameComponent owner, RenderableMeshProperty fakeGun, RenderableMeshProperty turret, CameraProperty mainCameraProperty) {
        super(owner);
        this.mainCameraProperty = mainCameraProperty;
        this.fakeGun = fakeGun;
        this.turret = turret;
    }

    @Override
    protected void init() {
        trueGun = this.getOwner().getParent().getProperty(RenderableMeshProperty.MESH_PROPERTY_NAME);
        secondCameraPerspectiveMatrix = (PerspectiveMatrix) secondCameraProperty.getCamera().getProjectionMatrix();
        currState = 0;
    }

    @Override
    protected void update(int delta) {
        if(super.getInputHandler().wasMouseButtonPressed(MouseEvent.BUTTON2))
            currState = ++currState%3;

        switch (currState) {
            case 0:
                turret.enable();
                trueGun.enable();
                fakeGun.disable();
                this.getContext().getGraphics().setMainViewCamera(mainCameraProperty.getCamera());
                break;
            case 1:
                turret.disable();
                trueGun.disable();
                fakeGun.enable();
                secondCameraPerspectiveMatrix.setFov(70);
                this.getContext().getGraphics().setMainViewCamera(secondCameraProperty.getCamera());
                break;
            case 2:
                secondCameraPerspectiveMatrix.setFov(30);
                break;
        }
    }
}
