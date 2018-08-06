package net.warpgame.engine.net.message;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Hubertus
 * Created 06.08.2018
 */
public class MessageQueueTest {
    @Mock
    MessageSender messageSender;

    MessageQueue messageQueue;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
        messageQueue = new MessageQueue(messageSender);
    }

//    @Test
//    public void
}