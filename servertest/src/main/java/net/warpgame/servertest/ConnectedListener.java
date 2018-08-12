package net.warpgame.servertest;

import net.warpgame.content.BoardShipEvent;
import net.warpgame.content.LoadShipEvent;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.event.Event;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.net.messagetypes.event.ConnectedEvent;
import net.warpgame.engine.physics.FullPhysicsProperty;
import net.warpgame.engine.physics.PhysicsService;
import net.warpgame.engine.physics.RigidBodyConstructor;
import net.warpgame.engine.physics.shapeconstructors.RigidBodyBoxShapeConstructor;
import net.warpgame.engine.server.Client;
import net.warpgame.engine.server.ClientRegistry;
import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ConnectedListener extends Listener<ConnectedEvent> {

    private final ComponentRegistry componentRegistry;
    private final PhysicsService physicsService;
    private final ClientRegistry clientRegistry;
    private Component scene;

    ConnectedListener(Component owner,
                      ComponentRegistry componentRegistry,
                      PhysicsService physicsService,
                      ClientRegistry clientRegistry) {
        super(owner, Event.getTypeId(ConnectedEvent.class));

        this.componentRegistry = componentRegistry;
        this.physicsService = physicsService;
        this.clientRegistry = clientRegistry;
    }

    @Override
    public void handle(ConnectedEvent event) {
        if (event.getSourcePeerId() == 0)
            return;
        System.out.println("client connected");
        Component ship = new SceneComponent(getOwner());
        TransformProperty transformProperty = new TransformProperty();
        ship.addProperty(transformProperty);
        ship.addProperty(new RemoteInputProperty());
        ship.addListener(new ClientInputListener(ship));
        RigidBodyBoxShapeConstructor shapeConstructor = new RigidBodyBoxShapeConstructor(new Vector3f(2, 2, 2));
        RigidBodyConstructor constructor = new RigidBodyConstructor(shapeConstructor, 10f);
        FullPhysicsProperty physicsProperty = new FullPhysicsProperty(constructor.construct(transformProperty));

        ship.addProperty(physicsProperty);
        ship.addScript(MovementScript.class);

        Client client = clientRegistry.getClient(event.getSourcePeerId());

        if (client != null) {
            getOwner().triggerEvent(new LoadShipEvent(ship.getId(), transformProperty.getTranslation(), Client.ALL));
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
                property = c.getProperty(Property.getTypeId(TransformProperty.class));
                getOwner().triggerEvent(new LoadShipEvent(c.getId(), property.getTranslation(), client.getId()));
            }
        }
    }

}
