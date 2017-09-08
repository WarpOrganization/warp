package pl.warp.test;

import pl.warp.engine.core.property.Property;
import pl.warp.engine.graphics.camera.CameraProperty;
import pl.warp.engine.graphics.mesh.RenderableMeshProperty;

/**
 * @author Jaca777
 * Created 2017-09-08 at 15
 */
public class SecondCameraProperty extends Property{
    public static final String SECOND_CAMERA_PROPERTY_NAME = "secondCamera";

    private CameraProperty mainCameraProperty;
    private RenderableMeshProperty fakeGun;
    private RenderableMeshProperty turret;

    public SecondCameraProperty(CameraProperty mainCameraProperty, RenderableMeshProperty fakeGun, RenderableMeshProperty turret) {
        super(SECOND_CAMERA_PROPERTY_NAME);
        this.mainCameraProperty = mainCameraProperty;
        this.fakeGun = fakeGun;
        this.turret = turret;
    }

    public CameraProperty getMainCameraProperty() {
        return mainCameraProperty;
    }

    public SecondCameraProperty setMainCameraProperty(CameraProperty mainCameraProperty) {
        this.mainCameraProperty = mainCameraProperty;
        return this;
    }

    public RenderableMeshProperty getFakeGun() {
        return fakeGun;
    }

    public SecondCameraProperty setFakeGun(RenderableMeshProperty fakeGun) {
        this.fakeGun = fakeGun;
        return this;
    }

    public RenderableMeshProperty getTurret() {
        return turret;
    }

    public SecondCameraProperty setTurret(RenderableMeshProperty turret) {
        this.turret = turret;
        return this;
    }
}
