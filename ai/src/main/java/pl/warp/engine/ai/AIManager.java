package pl.warp.engine.ai;

import pl.warp.engine.ai.behaviortree.BehaviorTreeNotFoundException;
import pl.warp.engine.ai.property.AIProperty;
import pl.warp.engine.core.scene.Component;
import pl.warp.engine.core.scene.ComponentDeathEvent;
import pl.warp.engine.core.scene.SimpleListener;
import pl.warp.engine.core.scene.observable.PropertyAddedEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hubertus
 *         Created 04.02.17
 */
public class AIManager {
    private Set<AIProperty> properties = new HashSet<>();
    private Set<AIProperty> propertiesToAdd = new HashSet<>();
    private Set<AIProperty> propertiesToRemove = new HashSet<>();

    private final Object AIContextMutex = new Object();

    public void init(Component scene) {
        SimpleListener.createListener(scene, PropertyAddedEvent.PROPERTY_CREATED_EVENT_NAME, this::handlePropertyCreated);
    }

    private void handlePropertyCreated(PropertyAddedEvent event) {
        if(event.getProperty().getName().equals(AIProperty.AI_POPERTY_NAME)){
            addTree((AIProperty) event.getProperty(), event.getOwner());
        }
    }

    public void addTree(AIProperty property, Component owner) {
        synchronized (AIContextMutex) {
            propertiesToAdd.add(property);
            createDeathListener(property, owner);
        }
    }

    private void createDeathListener(AIProperty property, Component owner) {
        SimpleListener.createListener(
                owner,
                ComponentDeathEvent.COMPONENT_DEATH_EVENT_NAME,
                (e) -> removeProperty(property)
        );
    }

    private void removeProperty(AIProperty property) {
        synchronized (AIContextMutex) {
            propertiesToRemove.add(property);
        }
    }

    public Set<AIProperty> getProperties() {
        return properties;
    }

    public void update() {
        synchronized (AIContextMutex) {
            properties.addAll(propertiesToAdd);
            if (!properties.containsAll(propertiesToRemove))
                throw new BehaviorTreeNotFoundException("Unable to remove a behavior tree.");
            else properties.removeAll(propertiesToRemove);
        }
    }
}
