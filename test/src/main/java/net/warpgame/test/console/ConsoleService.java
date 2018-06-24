package net.warpgame.test.console;

import net.warpgame.engine.core.component.SceneComponent;
import net.warpgame.engine.core.component.SceneHolder;
import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.lang3.ArrayUtils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author KocproZ
 * Created 2018-01-09 at 22:06
 */
@Service
public class ConsoleService {

    private Map<String, Command> commands = new HashMap<>();
    private Map<String, CommandVariable> variables = new HashMap<>();
    private PrintStream output;
    private SceneHolder sceneHolder;
    private SceneComponent consoleComponent;

    public ConsoleService(SceneHolder holder) {
        sceneHolder = holder;
        output = System.out;
    }

    public void printToConsole(String text) {
        output.println(text);
    }

    /**
     * Registers basic commands
     */
    public void initConsole() {
        consoleComponent = new SceneComponent(sceneHolder.getScene());
        SimpleCommand help = new SimpleCommand("help", Side.CLIENT,
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

        SimpleCommand list = new SimpleCommand("list", Side.CLIENT,
                "lists all commands", "list");
        list.setExecutor((args) -> {
            output.println("Available commands:");
            for (Command c : commands.values()) {
                output.printf("%s\n", c.getCommand());
            }
        });

        SimpleCommand print = new SimpleCommand("print", Side.CLIENT,
                "prints variables to console", "print $variable1 [...]");
        print.setExecutor((args) -> {
            for (String s : args)
                output.printf("%s\n", s);
        });

        SimpleCommand variables = new SimpleCommand("variables", Side.CLIENT,
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
     * @param variable    CommandVariable to add
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
    void parseAndExecute(String line) {
        String[] args = line.split(" ");
        String commandString = args[0];
        args = ArrayUtils.removeElement(args, commandString);
        parseVariables(args);

        Command command = commands.get(commandString);
        if (command == null) {
            output.printf("Command '%s' not found. Type 'list' to list available commands\n", commandString);
            return;
        }

        if (command.getSide() == Side.CLIENT) {
            command.execute(args);
        } else {
//            consoleComponent.triggerEvent();
            //TODO send command to server
        }
    }

    /**
     * Replaces '$variable' with value.
     * @param args    Reference to String[] of variables to parse
     */
    private void parseVariables(String... args) {
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
    public String getHelpText(String command) {
        return commands.get(command).getHelpText();
    }

    public Set<String> getVariables() {
        return variables.keySet();
    }

}
