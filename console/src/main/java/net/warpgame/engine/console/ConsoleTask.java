package net.warpgame.engine.console;

import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;

import java.util.Scanner;

/**
 * @author KocproZ
 * Created 2018-01-09 at 21:38
 */
@Service
@RegisterTask(thread = "console")
public class ConsoleTask extends EngineTask {

    private ConsoleService consoleService;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleTask(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    @Override
    protected void onInit() {

    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        String line = scanner.nextLine();
        consoleService.consoleComponent.triggerEvent(new ConsoleInputEvent(line));
//        consoleService.parseAndExecute(line);
    }
}
