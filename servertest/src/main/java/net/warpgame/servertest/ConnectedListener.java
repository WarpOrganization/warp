package net.warpgame.servertest;

import net.warpgame.content.BoardShipEvent;
import net.warpgame.content.LoadShipEvent;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.ComponentRegistry;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.event.Listener;
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
    private Component scene;

    protected ConnectedListener(Component owner, ComponentRegistry componentRegistry) {
        super(owner, "connectedEvent");

        this.componentRegistry = componentRegistry;
    }

    @Override
    public void handle(ConnectedEvent event) {
        Component ship = new SceneComponent(getOwner());
        TransformProperty transformProperty = new TransformProperty();
        ship.addProperty(transformProperty);
        ship.addProperty(new RemoteInputProperty());
        ship.addListener(new ClientInputListener(ship));
        ship.addScript(MovementScript.class);


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
