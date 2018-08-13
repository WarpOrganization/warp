package net.warpgame.engine.net.message;

import net.warpgame.engine.net.Peer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


/**
 * @author Hubertus
 * Created 06.08.2018
 */
public class MessageQueueTest {
    @Mock
    MessageSender messageSender;
    @Mock
    Peer peer;

    private MessageQueue messageQueue;
    private MessageEnvelope envelope;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);

        doAnswer(invocation -> {
            MessageEnvelope envelope = invocation.getArgument(0);
            envelope.setSendTime(System.currentTimeMillis());
            return null;
        }).when(messageSender).sendMessage(any(MessageEnvelope.class));

        messageQueue = new MessageQueue(messageSender);

        DependencyIdGenerator idGenerator = new DependencyIdGenerator();
        when(peer.getDependencyIdGenerator())
                .thenReturn(idGenerator);

        envelope = new MessageEnvelope(new byte[1], 0);
        envelope.setTargetPeer(peer);

    }

    @Test
    public void shouldSendMessage() {
        messageQueue.pushEnvelope(envelope);
        messageQueue.update();
        verify(messageSender).sendMessage(envelope);
    }

    @Test
    public void shouldResendMessage() throws InterruptedException {
        messageQueue.pushEnvelope(envelope);
        messageQueue.update();

        sleepResendInterval();
        messageQueue.update();
        messageQueue.update();
        verify(messageSender, times(2)).sendMessage(any());

        sleepResendInterval();
        messageQueue.update();
        messageQueue.update();
        verify(messageSender, times(3)).sendMessage(any());
    }

    @Test
    public void shouldNotResendConfirmedMessage() {
        messageQueue.pushEnvelope(envelope);
        messageQueue.update();

        envelope.confirm();
        sleepResendInterval();
        messageQueue.update();
        verify(messageSender, times(1)).sendMessage(any());
    }

    private void sleepResendInterval() {
        try {
            Thread.sleep(650);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}