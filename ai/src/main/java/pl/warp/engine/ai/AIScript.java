package pl.warp.engine.ai;

import pl.warp.engine.core.component.Component;
import pl.warp.engine.core.script.Script;
import pl.warp.engine.core.script.annotation.OwnerProperty;

/**
 * @author Hubertus
 * Created 01.10.2017
 */
public class AIScript extends Script {

    @OwnerProperty(name = AIProperty.AI_POPERTY_NAME)
    private AIProperty aiProperty;

    public AIScript(Component owner) {
        super(owner);
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onUpdate(int delta) {
        if (aiProperty.isEnabled()) {
            if (!aiProperty.getBehaviorTree().isInitialized()) aiProperty.getBehaviorTree().init();
            aiProperty.getBehaviorTree().execute(delta);
        }
    }
}
