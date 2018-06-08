package net.warpgame.engine.ai;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.execution.task.EngineTask;
import net.warpgame.engine.core.property.Property;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public class AITask extends EngineTask {

    private AIManager manager;
    private Component scene;


    public AITask(AIManager manager, Component scene) {
        this.manager = manager;
        this.scene = scene;
    }

    @Override
    protected void onInit() {
//        manager.init(scene);
    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        manager.update();
        manager.getProperties().forEach((p) -> {
            if (p.isEnabled()) {
                if(!p.getBehaviorTree().isInitialized()) p.getBehaviorTree().init();
                p.getBehaviorTree().execute(delta);
            }
        });
    }

    private boolean hasAi(Component c) {
        return c.hasEnabledProperty(Property.getTypeId(AIProperty.class));
    }
}
