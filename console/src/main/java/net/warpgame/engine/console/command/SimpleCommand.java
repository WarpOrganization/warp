package net.warpgame.engine.console.command;

import java.util.function.Consumer;

/**
 * @author KocproZ
 * Created 2018-01-10 at 07:33
 */
public class SimpleCommand extends Command {
    private Consumer<String[]> executor;

    public SimpleCommand(String command, String helpText, String usageText) {
        super(command, helpText, usageText);
    }

    /**
     * Executes the command with given parameters (if executor is set).
     *
     * @param args passed parameters
     */
    @Override
    public void execute(String... args) {
        if (executor == null)
            throw new IllegalStateException("Executor not defined.");

        executor.accept(args);
    }

    /**
     * Set lambda to be executed whe command is invoked.
     *
     * @param func lambda
     */
    public void setExecutor(Consumer<String[]> func) {
        executor = func;
    }


}
