package net.warpgame.engine.console;

import net.warpgame.engine.console.command.Command;
import net.warpgame.engine.console.command.CommandVariable;
import net.warpgame.engine.console.command.SimpleCommand;
import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.Context;
import org.apache.commons.lang3.ArrayUtils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author KocproZ
 * Created 2018-01-09 at 22:06
 */
public abstract class ConsoleService {

    private Map<String, Command> commands = new HashMap<>();
    private Map<String, CommandVariable> variables = new HashMap<>();
    private PrintStream output;
    private SceneHolder sceneHolder; //Temporary, until consoleComponent can be used
    private Context context;
    SceneComponent consoleComponent;


    public ConsoleService(Context context, SceneHolder holder) {
        this.context = context;
        this.sceneHolder = holder;
        output = System.out;
    }

    public void print(String text) {
        output.println(text);
    }

    /**
     * Registers basic commands
     */
    public void init() {
//        consoleComponent = new SceneComponent(sceneHolder.getScene());
        consoleComponent = sceneHolder.getScene();

        consoleComponent.addListener(new ConsoleInputEventListener(consoleComponent, this));
        consoleComponent.addListener(new ChatMessageEventListener(consoleComponent, context,  this));

        SimpleCommand help = new SimpleCommand("help",
                "Get help", "help [command]");
        help.setExecutor((args) -> {
            if (args.length > 0) {
                if (getHelpText(args[0]) != null) {
                    output.println(getHelpText(args[0]));
                } else {
                    output.println("No such command");
                }
            } else {
                output.println(help.getUsageText());
            }
        });

        SimpleCommand list = new SimpleCommand("list",
                "lists all commands", "list");
        list.setExecutor((args) -> {
            output.println("Available commands:");
            for (Command c : commands.values()) {
                output.printf("%s\n", c.getCommand());
            }
        });

        SimpleCommand print = new SimpleCommand("print",
                "prints variables to console", "print $variable1 [...]");
        print.setExecutor((args) -> {
            for (String s : args)
                output.printf("%s\n", s);
        });

        SimpleCommand variables = new SimpleCommand("variables",
                "Lists all variables", "variables");
        variables.setExecutor((args) -> {
            output.println("Variables:");
            for (String var : getVariables())
                output.printf("%s\n", var);
        });

        registerCommand(help);
        registerCommand(list);
        registerCommand(print);
        registerCommand(variables);
    }

    /**
     * Adds Command to list of available commands.
     *
     * @param c Command to add
     */
    public void registerCommand(Command c) {
        commands.put(c.getCommand(), c);
    }

    /**
     * Registers variable and adds '$' at the beginning
     *
     * @param variable CommandVariable to add
     */
    public void registerVariable(CommandVariable variable) {
        if (!variable.getName().startsWith("$"))
            variable = new CommandVariable("$" + variable.getName(), variable.getValue());
        variables.put(variable.getName(), variable);
    }

    /**
     * Executes command
     *
     * @param line Line from console to parse
     */
    public void parseAndExecute(String line) {
        line = line.substring(1);
        String[] args = line.split(" ");
        String commandString = args[0];
        args = ArrayUtils.removeElement(args, commandString);
        parseVariables(args);

        Command command = commands.get(commandString);
        if (command == null)
            output.printf("Command '%s' not found. Type 'list' to list available commands\n", commandString);
        else
            command.execute(args);
    }

    abstract void sendChatMessage(String sender, String msg);

    /**
     * Replaces '$variable' with value.
     *
     * @param args Reference to String[] of variables to parse
     */
    void parseVariables(String... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("$") && variables.containsKey(args[i])) {
                args[i] = variables.get(args[i]).getValue();
            }
        }
    }

    /**
     * Returns help text associated with the command.
     *
     * @param command Command, eg. "list"
     * @return help text, null if command not found
     */
    private String getHelpText(String command) {
        return commands.get(command).getHelpText();
    }

    Set<String> getVariables() {
        return variables.keySet();
    }

    public SceneComponent getConsoleComponent() {
        return consoleComponent;
    }

}
