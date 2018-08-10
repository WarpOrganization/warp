package net.warpgame.engine.net.messagetypes.event;

import net.warpgame.engine.net.Peer;

import java.util.function.Consumer;

public abstract class ConfirmableNetworkEvent extends NetworkEvent {

    private Consumer<Peer> confirmationConsumer;

    public ConfirmableNetworkEvent(Consumer<Peer> confirmationConsumer) {
        this.confirmationConsumer = confirmationConsumer;
    }

    public ConfirmableNetworkEvent(int targetId, Consumer<Peer> confirmationConsumer) {
        super(targetId);
        this.confirmationConsumer = confirmationConsumer;
    }

    public Consumer<Peer> getConfirmationConsumer() {
        return confirmationConsumer;
    }

    public void setConfirmationConsumer(Consumer<Peer> confirmationConsumer) {
        this.confirmationConsumer = confirmationConsumer;
    }
}
