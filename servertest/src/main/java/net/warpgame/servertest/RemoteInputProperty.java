package net.warpgame.servertest;

import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.server.RemoteInput;

/**
 * @author Hubertus
 * Created 05.01.2018
 */
public class RemoteInputProperty extends Property {

    public static final String NAME = "remoteInput";

    private RemoteInput remoteInput = new RemoteInput();

    public RemoteInputProperty() {
        super(NAME);
    }

    public RemoteInput getRemoteInput() {
        return remoteInput;
    }

    public void setRemoteInput(RemoteInput remoteInput) {
        this.remoteInput = remoteInput;
    }
}
