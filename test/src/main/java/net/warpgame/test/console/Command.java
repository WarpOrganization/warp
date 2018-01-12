package net.warpgame.test.console;

import java.util.function.Consumer;

/**
 * @author KocproZ
 * Created 2018-01-10 at 07:33
 */
public class Command {
    private String command;
    private String helpText;
    private Consumer<String[]> executor;
    private Side side;

    public Command(String command, Side side) {
        this(command, side, "Does something, idk. what");
    }

    public Command(String command, Side side, String helpText) {
        this.command = command;
        this.helpText = helpText;
        this.side = side;
    }

    /**
     * Executes the command with given parameters (if executor is set).
     * @param args      passed parameters
     * @return          true if execution succeeded
     */
    public void execute(String... args) {
        if (executor == null)
        throw new IllegalStateException("Executor not defined.");

        executor.accept(args);
    }

    /**
     * Set lambda to be executed whe command is invoked.
     * @param func    lambda
     */
    public void setExecutor(Consumer<String[]> func) {
        executor = func;
    }

    public Side getSide() {
        return side;
    }

    public String getHelpText() {
        return helpText;
    }

    public String getCommand() {
        return command;
    }

    public enum Side {
        CLIENT, SERVER
    }

}
