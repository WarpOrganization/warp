package net.warpgame.engine.net.message;

import io.netty.buffer.ByteBuf;
import net.warpgame.engine.net.Peer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * @author Hubertus
 * Created 06.08.2018
 */
public class IncomingMessageQueueTest {

    @Mock
    MessageProcessorsService processorsService;
    @Mock
    MessageProcessor messageProcessor;
    @Mock
    MessageProcessor otherMessageProcessor;
    @Mock(stubOnly = true)
    Peer stubPeer;
    @Mock(stubOnly = true)
    ByteBuf stubContent;

    private IncomingMessageQueue messageQueue;

    private static int MESSAGE_ID = 0;
    private static int OTHER_MESSAGE_ID = 1;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(processorsService.getMessageProcessor(MESSAGE_ID))
                .thenReturn(messageProcessor);
        when(processorsService.getMessageProcessor(OTHER_MESSAGE_ID))
                .thenReturn(otherMessageProcessor);
        messageQueue = new IncomingMessageQueue(processorsService);
    }

    @Test
    public void shouldProcessMessage() {
        messageQueue.addMessage(stubPeer, MESSAGE_ID, 1, stubContent);

        verify(messageProcessor, times(1)).processMessage(eq(stubPeer), any());
    }

    @Test(expected = UnknownMessageTypeException.class)
    public void shouldThrowUnknownMessageType() {
        messageQueue.addMessage(stubPeer, 2, 1, stubContent);
    }


    @Test
    public void shouldNotProcessMessageWithTooHighDependencyId() {
        messageQueue.addMessage(stubPeer, 1, 2, stubContent);
        verifyZeroInteractions(processorsService);
    }

    @Test
    public void shouldDiscardMessageWithTooLowDependencyId() {
        messageQueue.addMessage(stubPeer, MESSAGE_ID, 1, stubContent);
        messageQueue.addMessage(stubPeer, OTHER_MESSAGE_ID, 2, stubContent);
        verify(processorsService, times(2)).getMessageProcessor(anyInt());

        messageQueue.addMessage(stubPeer, OTHER_MESSAGE_ID, 2, stubContent);
        verify(processorsService, times(2)).getMessageProcessor(anyInt());
    }

    @Test
    public void shouldProcessMessagesInCorrectOrder() {
        messageQueue.addMessage(stubPeer, MESSAGE_ID, 2, stubContent);
        messageQueue.addMessage(stubPeer, OTHER_MESSAGE_ID, 1, stubContent);

        InOrder inOrder = Mockito.inOrder(processorsService);
        inOrder.verify(processorsService).getMessageProcessor(OTHER_MESSAGE_ID);
        inOrder.verify(processorsService).getMessageProcessor(MESSAGE_ID);
    }
}