package net.warpgame.engine.console.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.function.Consumer;

import static org.mockito.Mockito.verify;

public class SimpleCommandTest {

    @Mock
    Consumer<String[]> consumer;

    private SimpleCommand command;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        command = new SimpleCommand("command", "help", "usage");
    }

    @Test(expected = IllegalStateException.class)
    public void executeShouldThrowException() {
        command.execute("arg1", "arg2");
    }

    @Test
    public void shouldExecute() {
        command.setExecutor(consumer);
        command.execute("arg1", "arg2");
        verify(consumer).accept(new String[]{"arg1", "arg2"});
    }
}