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

    private boolean currState;
    private boolean prevState;
    private boolean zoomedIN;


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
        currState = false;
        prevState = false;
        zoomedIN = false;
    }

    @Override
    protected void update(int delta) {
        Input input = getContext().getInput();

        currState = input.getScrollDelta() < 0 || !(input.getScrollDelta() > 0) && prevState;

        if (currState != prevState) {
            if (currState) {
                turret.disable();
                trueGun.disable();
                fakeGun.enable();
                secondCameraPerspectiveMatrix.setFov(70);
                zoomedIN = false;
                super.getInputHandler().wasMouseButtonPressed(MouseEvent.BUTTON2);
                this.getContext().getGraphics().setMainViewCamera(secondCameraProperty.getCamera());
            } else {
                turret.enable();
                trueGun.enable();
                fakeGun.disable();
                this.getContext().getGraphics().setMainViewCamera(mainCameraProperty.getCamera());
            }
        }

        if (currState && super.getInputHandler().wasMouseButtonPressed(MouseEvent.BUTTON2)){
            if (zoomedIN) {
                secondCameraPerspectiveMatrix.setFov(70);
                zoomedIN = false;
            } else {
                secondCameraPerspectiveMatrix.setFov(30);
                zoomedIN = true;
            }
        }

        prevState = currState;

    }
}
