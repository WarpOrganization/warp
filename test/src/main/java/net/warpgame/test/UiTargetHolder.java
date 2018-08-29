package net.warpgame.test;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.graphics.texture.Texture2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MarconZet
 * Created 30.08.2018
 */

@Service
public class UiTargetHolder {
    private List<Component> componentList = new ArrayList<>();

    private Texture2D targetHighlight;

    public void addComponent(Component component){
        componentList.add(component);
    }

    public List<Component> getComponentList() {
        return componentList;
    }

    public Texture2D getTargetHighlight() {
        return targetHighlight;
    }

    public void setTargetHighlight(Texture2D targetHighlight) {
        this.targetHighlight = targetHighlight;
    }
}
