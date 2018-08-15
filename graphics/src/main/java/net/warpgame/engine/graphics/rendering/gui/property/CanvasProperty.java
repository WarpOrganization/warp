package net.warpgame.engine.graphics.rendering.gui.property;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.graphics.rendering.gui.GuiRenderer;

/**
 * @author MarconZet
 * Created 15.08.2018
 */
public class CanvasProperty extends Property {
    GuiRenderer guiRenderer;

    @Override
    public void init() {
        super.init();
        guiRenderer = getOwner().getContext().getLoadedContext().findOne(GuiRenderer.class).get();

    }
}
