package net.warpgame.test;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.core.property.Transforms;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.graphics.camera.CameraProperty;
import net.warpgame.engine.graphics.rendering.ui.CanvasProperty;
import net.warpgame.engine.graphics.rendering.ui.RectTransformProperty;
import net.warpgame.engine.graphics.rendering.ui.image.ImageProperty;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MarconZet
 * Created 30.08.2018
 */
public class UiTargetScript extends Script {
    private UiTargetHolder uiTargetHolder;

    private List<Component> squares;
    private int size;

    @OwnerProperty(@IdOf(CanvasProperty.class))
    private CanvasProperty canvasProperty;

    private TransformProperty cameraTransform;
    private CameraProperty cameraProperty;
    private Vector3f targetPosition;

    public UiTargetScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        uiTargetHolder = getContext().getLoadedContext().findOne(UiTargetHolder.class).get();
        size = 0;
        squares = new ArrayList<>();
        cameraProperty = canvasProperty.getCameraProperty();
        cameraTransform = cameraProperty.getOwner().getProperty(Property.getTypeId(TransformProperty.class));
        targetPosition = new Vector3f();
    }

    @Override
    public void onUpdate(int delta) {
        while(size < uiTargetHolder.getComponentList().size()){
            createSquare();
            size++;
        }
        for (int i = 0; i < uiTargetHolder.getComponentList().size(); i++) {
            RectTransformProperty property = squares.get(i).getProperty(Property.getTypeId(RectTransformProperty.class));
            Component target = uiTargetHolder.getComponentList().get(i);
            property.setPosition(cameraProperty.getPositionOnCanvas(target));
            targetPosition = Transforms.getAbsolutePosition(target, targetPosition);
            float distance = targetPosition.distance(cameraProperty.getCameraPos());
            property.setWidthAndHeight((int)(10000/distance));
        }
    }

    private void createSquare() {
        Component square = new SceneComponent(getOwner());
        square.addProperty(new RectTransformProperty(100, 100));
        square.addProperty(new ImageProperty(uiTargetHolder.getTargetHighlight()));
        squares.add(square);
    }
}
