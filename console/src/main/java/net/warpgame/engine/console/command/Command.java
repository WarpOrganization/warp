package net.warpgame.engine.console.command;

/**
 * @author KocproZ
 * Created 2018-01-12 at 22:58
 */
public abstract class Command {
    private String command;
    private String helpText;
    private String usageText;

    protected Command(String command, String helpText, String usageText) {
        this.command = command;
        this.helpText = helpText;
        this.usageText = usageText;
    }

    public abstract void execute(String... args);

    public String getHelpText() {
        return helpText;
    }

    public String getUsageText() {
        return usageText;
    }

    public String getCommand() {
        return command;
    }

}
