package net.warpgame.servertest;

import net.warpgame.content.BoardShipEvent;
import net.warpgame.content.LoadShipEvent;
import net.warpgame.engine.common.transform.TransformProperty;
import net.warpgame.engine.core.component.Component;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.event.Listener;
import net.warpgame.engine.server.ConnectedEvent;
import net.warpgame.engine.server.ServerEnvelope;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class ConnectedListener extends Listener<ConnectedEvent> {

    private Component scene;

    protected ConnectedListener(Component owner) {
        super(owner, "connectedEvent");

    }

    @Override
    public void handle(ConnectedEvent event) {
        Component ship = new SceneComponent(getOwner());
        TransformProperty transformProperty = new TransformProperty();
        ship.addProperty(transformProperty);
        ship.addProperty(new RemoteInputProperty());
        ship.addListener(new ClientInputListener(ship));
        ship.addScript(MovementScript.class);
        getOwner().triggerEvent(new ServerEnvelope(new LoadShipEvent(ship.getId())));
        getOwner().triggerEvent(new ServerEnvelope(new BoardShipEvent(ship.getId()), event.getConnectedClient()));

    }
}
