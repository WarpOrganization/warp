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
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.net.event.ConnectedEvent;
import net.warpgame.engine.physics.FullPhysicsProperty;
import net.warpgame.engine.physics.PhysicsManager;
import net.warpgame.engine.physics.PhysicsMotionState;
import net.warpgame.engine.server.Client;
import net.warpgame.engine.server.ClientRegistry;

import java.util.ArrayList;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ConnectedListener extends Listener<ConnectedEvent> {

    private final ComponentRegistry componentRegistry;
    private final PhysicsManager physicsManager;
    private final ClientRegistry clientRegistry;
    private Component scene;

    ConnectedListener(Component owner,
                      ComponentRegistry componentRegistry,
                      PhysicsManager physicsManager,
                      ClientRegistry clientRegistry) {
        super(owner, Event.getTypeId(ConnectedEvent.class));

        this.componentRegistry = componentRegistry;
        this.physicsManager = physicsManager;
        this.clientRegistry = clientRegistry;
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

        Client client = clientRegistry.getClient(event.getSourceClientId());

        if (client != null) {
            getOwner().triggerEvent(new LoadShipEvent(ship.getId(), transformProperty.getTranslation(), client.getId()));
            sendScene(client, ship.getId());
            getOwner().triggerEvent(new BoardShipEvent(ship.getId(), client.getId()));

        }
    }

    private void sendScene(Client client, int currentShip) {
        ArrayList<Component> components = new ArrayList<>();
        componentRegistry.getComponents(components);
        TransformProperty property;
        for (Component c : components) {
            if (c.getId() != 0 && c.getId() != currentShip) {
                property = c.getProperty(TransformProperty.NAME);
                getOwner().triggerEvent(new LoadShipEvent(c.getId(), property.getTranslation(), client.getId()));
            }
        }
    }

}
