package net.warpgame.engine.net;

import net.warpgame.engine.core.component.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.warpgame.engine.net.ConnectionState.CONNECTING;

/**
 * @author Hubertus
 * Created 16.05.2018
 */
public class ConnectionStateHolder {
    private ConnectionState connectionState;
    private ConnectionState requestedConnectionState;
    private ConnectionState partnerRequestedConnectionState;
    private Component rootComponent;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionStateHolder.class);


    public ConnectionStateHolder(Component rootComponent) {
        this.rootComponent = rootComponent;
        this.connectionState = CONNECTING;
    }


    public synchronized void setRequestedConnectionState(ConnectionState newRequestedConnectionState) {
        this.requestedConnectionState = newRequestedConnectionState;
        update();
    }

    public synchronized void setPartnerRequestedConnectionState(ConnectionState newPartnerRequestedConnectionState) {
        this.partnerRequestedConnectionState = newPartnerRequestedConnectionState;
        update();
    }

    private void update() {
        if (requestedConnectionState == partnerRequestedConnectionState) {
            connectionState = requestedConnectionState;
            logger.info("Connection state changed to " + connectionState);
        }
    }

    public ConnectionState getConnectionState() {
        return connectionState;
    }
}
