package pl.warp.engine.ai;

import pl.warp.engine.ai.property.AIProperty;
import pl.warp.engine.core.EngineTask;
import pl.warp.engine.core.scene.Component;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public class AITask extends EngineTask {

    private Component root;

    public AITask(Component root){
        this.root = root;
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        root.forEachChildren(child ->{
               if(hasAi(child)){
                   AIProperty property = child.getProperty(AIProperty.AI_POPERTY_NAME);
                   property.getBehaviourTree().execute();
               }
           });
    }

    private boolean hasAi(Component c){
        return c.hasEnabledProperty(AIProperty.AI_POPERTY_NAME);
    }
}
