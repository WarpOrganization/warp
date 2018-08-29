package net.warpgame.test;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.IdOf;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.script.Script;
import net.warpgame.engine.core.script.annotation.OwnerProperty;
import net.warpgame.engine.graphics.rendering.ui.CanvasProperty;
import net.warpgame.engine.graphics.rendering.ui.RectTransformProperty;
import net.warpgame.engine.graphics.rendering.ui.image.ImageProperty;

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

    public UiTargetScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {
        uiTargetHolder = getContext().getLoadedContext().findOne(UiTargetHolder.class).get();
        size = 0;
        squares = new ArrayList<>();
    }

    @Override
    public void onUpdate(int delta) {
        while(size < uiTargetHolder.getComponentList().size()){
            createSquare();
            size++;
        }
        for (int i = 0; i < uiTargetHolder.getComponentList().size(); i++) {
            RectTransformProperty property = squares.get(i).getProperty(Property.getTypeId(RectTransformProperty.class));
            property.setPosition(canvasProperty.getCameraProperty().getPostitionOnCanvas(uiTargetHolder.getComponentList().get(i)));
        }
    }

    private void createSquare() {
        Component square = new SceneComponent(getOwner());
        square.addProperty(new RectTransformProperty(100, 100));
        square.addProperty(new ImageProperty(uiTargetHolder.getTargetHighlight()));
        squares.add(square);
    }
}
