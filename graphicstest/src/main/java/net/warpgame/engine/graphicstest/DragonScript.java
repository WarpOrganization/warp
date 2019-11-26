package net.warpgame.engine.graphicstest;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.graphics.camera.CameraHolder;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MarconZet
 * Created 26.11.2019
 */
public class DragonScript extends Script {
    private Logger logger = LoggerFactory.getLogger(DragonScript.class);

    @OwnerProperty(@IdOf(TransformProperty.class))
    TransformProperty transformProperty;

    private CameraHolder cameraHolder;
    private TransformProperty cameraTransform;
    private int curr = 0;

    public DragonScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        cameraHolder = getContext().getLoadedContext().findOne(CameraHolder.class).get();
        cameraTransform = cameraHolder.getCameraComponent().getProperty(Property.getTypeId(TransformProperty.class));
    }

    @Override
    public void onUpdate(int delta) {

        if(curr++ == 100) {
            curr = 0;
            Vector4f position = new Vector4f(2.223100f, 3.388400f, -0.229999f, 1.0f)
                    .mul(transformProperty.getCachedNonrelativeTransform())
                    .mul(cameraHolder.getCameraProperty().getCameraMatrix())
                    .mul(cameraHolder.getCameraProperty().getProjectionMatrix());
            logger.info("Current position: " + position);
        }
    }
}
