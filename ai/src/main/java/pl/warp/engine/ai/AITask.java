package pl.warp.engine.ai;

import pl.warp.engine.ai.property.AIProperty;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Component;

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
        manager.init(scene);
    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        manager.update();
        manager.getProperties().forEach((p) -> {
            if(p.isEnabled()) p.getBehaviorTree().execute(delta);
        });
    }

    private boolean hasAi(Component c) {
        return c.hasEnabledProperty(AIProperty.AI_POPERTY_NAME);
    }
}
