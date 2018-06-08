package net.warpgame.engine.ai;

import net.warpgame.engine.ai.behaviortree.BehaviorTreeNotFoundException;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentDeathEvent;
import net.warpgame.engine.core.component.SimpleListener;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.property.observable.PropertyAddedEvent;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Hubertus
 * Created 04.02.17
 */

@Service
public class AIManager {
    private Set<AIProperty> properties = new HashSet<>();
    private Set<AIProperty> propertiesToAdd = new HashSet<>();
    private Set<AIProperty> propertiesToRemove = new HashSet<>();

    private final Object AIContextMutex = new Object();

    public void init(Component scene) {
        SimpleListener.createListener(scene, Event.getTypeId(PropertyAddedEvent.class), this::handlePropertyCreated);
    }

    //TODO but why
    private void handlePropertyCreated(PropertyAddedEvent event) {
        if (event.getProperty() instanceof AIProperty) {
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
                Event.getTypeId(ComponentDeathEvent.class),
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
