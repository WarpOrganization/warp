package net.warpgame.engine.console.command;

/**
 * @author KocproZ
 * Created 2018-01-12 at 22:58
 */
public abstract class Command {
    private String command;
    private String helpText;
    private String usageText;
    private Side side;

    protected Command(String command, Side side, String helpText, String usageText) {
        this.command = command;
        this.side = side;
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

    public Side getSide() {
        return side;
    }

    public enum Side {
        CLIENT, //Executed on client
        SERVER, //Executed on server
        LOCAL   //Executed on side where it was run
    }

}
