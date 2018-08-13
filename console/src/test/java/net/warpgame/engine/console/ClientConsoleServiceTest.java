package net.warpgame.engine.console;

import net.warpgame.engine.console.command.CommandVariable;
import net.warpgame.engine.core.component.Scene;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.Context;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ClientConsoleServiceTest {

    @Mock
    Context context;
    @Mock
    SceneHolder holder;
    @Mock
    Scene consoleComponent;

    private ClientConsoleService service;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        when(holder.getScene())
                .thenReturn(consoleComponent);
        service = new ClientConsoleService(holder, context);
        service.consoleComponent = consoleComponent;
        service.registerVariable(new CommandVariable("var", "value"));
    }

    @Test
    public void registerVariableTest() {
        service.registerVariable(new CommandVariable("a", "asdf"));
        service.registerVariable(new CommandVariable("b", "bsdf"));
        assertTrue(service.getVariables().contains("$a"));
        assertTrue(service.getVariables().contains("$b"));
    }

    @Test
    public void sendChatMessageTest() {
        service.sendChatMessage("User", "Hello");
        verify(service.consoleComponent).triggerEvent(any());
    }

    @Test
    public void parseVariablesTest() {
        String[] line = new String[]{"Hello", "World", "$var"};
        service.parseVariables(line);
        assertArrayEquals(new String[]{"Hello", "World", "value"}, line);
    }

}