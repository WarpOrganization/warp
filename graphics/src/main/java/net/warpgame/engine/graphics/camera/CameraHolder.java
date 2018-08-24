package net.warpgame.engine.graphics.camera;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.Property;

/**
 * @author Jaca777
 * Created 2017-09-23 at 14
 */

@Service
@Profile("graphics")
public class CameraHolder {
    private CameraProperty cameraProperty;
    private Component cameraComponent;

    public void setCamera(Component owner){
        cameraComponent = owner;
        cameraProperty = owner.getProperty(Property.getTypeId(CameraProperty.class));
    }

    public Component getCameraComponent() {
        return cameraComponent;
    }

    public CameraProperty getCameraProperty() {
        return cameraProperty;
    }
}
