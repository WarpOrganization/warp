package net.warpgame.engine.graphics.rendering.ui.property;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.rendering.ui.UiRenderer;

/**
 * @author MarconZet
 * Created 15.08.2018
 */
public class CanvasProperty extends Property {
    UiRenderer uiRenderer;

    @Override
    public void init() {
        super.init();
        uiRenderer = getOwner().getContext().getLoadedContext().findOne(UiRenderer.class).get();

    }
}
