package net.warpgame.engine.physics.simplified;

import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * @author Hubertus
 * Created 17.06.2018
 */
@Service
@Profile("simplePhysics")
public class SimplifiedPhysicsSimulationService {

    private ComponentRegistry componentRegistry;

    public SimplifiedPhysicsSimulationService(ComponentRegistry componentRegistry) {
        this.componentRegistry = componentRegistry;
    }

    private ArrayList<Component> components = new ArrayList<>();

    void update(int delta) {
        fetchComponents();
        simulate(delta);
    }

    private void fetchComponents() {
        components.clear();
        componentRegistry.getComponents(components);
    }

    private Vector3f movementVector = new Vector3f();
    private Quaternionf rotation = new Quaternionf();

    private void simulate(int delta) {
        float secondsDelta = delta / 1000f;
        TransformProperty transformProperty;
        SimplifiedPhysicsProperty physicsProperty;
        for (Component component : components) {
            if (component.hasEnabledProperty(Property.getTypeId(SimplifiedPhysicsProperty.class))) {
                transformProperty = component.getProperty(Property.getTypeId(TransformProperty.class));
                physicsProperty = component.getProperty(Property.getTypeId(SimplifiedPhysicsProperty.class));
                transformProperty.move(movementVector
                        .set(physicsProperty.getVelocity())
                        .mul(secondsDelta));
                AxisAngle4f angularVelocity = physicsProperty.getAngularVelocity();
                rotation.set(0, 0, 0, 1);
                rotation.rotateAxis(
                        angularVelocity.angle * secondsDelta,
                        angularVelocity.x,
                        angularVelocity.y,
                        angularVelocity.z);
                transformProperty.getRotation().mul(rotation, rotation);
                transformProperty.setRotation(rotation);
            }
        }
    }
}
