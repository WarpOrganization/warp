package net.warpgame.engine.net.internalmessage;

import java.io.Serializable;

/**
 * @author Hubertus
 * Created 01.06.2018
 */
public class InternalMessage implements Serializable {
    private InternalMessageContent messageContent;
    private int targetPeerId;

    public InternalMessage(InternalMessageContent messageContent, int targetPeerId) {
        this.messageContent = messageContent;
        this.targetPeerId = targetPeerId;
    }

    public InternalMessageContent getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(InternalMessageContent messageContent) {
        this.messageContent = messageContent;
    }

    public int getTargetPeerId() {
        return targetPeerId;
    }

    public void setTargetPeerId(int targetPeerId) {
        this.targetPeerId = targetPeerId;
    }
}
