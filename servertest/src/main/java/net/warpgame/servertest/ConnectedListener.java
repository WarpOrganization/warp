package net.warpgame.servertest;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import net.warpgame.content.BoardShipEvent;
import net.warpgame.content.LoadShipEvent;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.physics.FullPhysicsProperty;
import net.warpgame.engine.physics.PhysicsManager;
import net.warpgame.engine.physics.PhysicsMotionState;
import net.warpgame.engine.server.Client;
import net.warpgame.engine.server.ConnectedEvent;
import net.warpgame.engine.server.ServerEnvelope;

import java.util.ArrayList;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ConnectedListener extends Listener<ConnectedEvent> {

    private final ComponentRegistry componentRegistry;
    private final PhysicsManager physicsManager;
    private Component scene;

    protected ConnectedListener(Component owner, ComponentRegistry componentRegistry, PhysicsManager physicsManager) {
        super(owner, "connectedEvent");

        this.componentRegistry = componentRegistry;
        this.physicsManager = physicsManager;
    }

    @Override
    public void handle(ConnectedEvent event) {
        Component ship = new SceneComponent(getOwner());
        TransformProperty transformProperty = new TransformProperty();
        ship.addProperty(transformProperty);
        ship.addProperty(new RemoteInputProperty());
        ship.addListener(new ClientInputListener(ship));
        ship.addScript(MovementScript.class);
        FullPhysicsProperty physicsProperty = new FullPhysicsProperty(
                new btRigidBody(
                        10,
                        new PhysicsMotionState(transformProperty),
                        new btBoxShape(new Vector3(2, 2, 2))));
        ship.addProperty(physicsProperty);
        physicsManager.addRigidBody(ship);
        Vector3 inertia = new Vector3();
        physicsProperty.getRigidBody().getCollisionShape().calculateLocalInertia(10, inertia);
        physicsProperty.getRigidBody().setMassProps(10, inertia);
        physicsProperty.getRigidBody().activate();

        getOwner().triggerEvent(new ServerEnvelope(new LoadShipEvent(ship.getId(), transformProperty.getTranslation())));
        sendScene(event.getConnectedClient(), ship.getId());
        getOwner().triggerEvent(new ServerEnvelope(new BoardShipEvent(ship.getId()), event.getConnectedClient()));
    }

    private void sendScene(Client client, int currentShip) {
        ArrayList<Component> components = new ArrayList<>();
        componentRegistry.getComponents(components);
        TransformProperty property;
        for (Component c : components) {
            if (c.getId() != 0 && c.getId() != currentShip) {
                property = c.getProperty(TransformProperty.NAME);
                getOwner().triggerEvent(new ServerEnvelope(new LoadShipEvent(c.getId(), property.getTranslation()), client));
            }
        }
    }

}
