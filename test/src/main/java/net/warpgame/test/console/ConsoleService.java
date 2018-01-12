package net.warpgame.test.console;

import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.lang3.ArrayUtils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KocproZ
 * Created 2018-01-09 at 22:06
 */
@Service
public class ConsoleService {

    private Map<String, Command> commands = new HashMap<>();
    private Map<String, CommandVariable> variables = new HashMap<>();
    private PrintStream output;

    public ConsoleService() {
        output = System.out;
        Command help = new Command("help", Command.Side.CLIENT, "Get help.");
        help.setExecutor((args) -> {
            if (args.length > 0)
                if (getHelpText(args[0]) != null)
                    output.println(getHelpText(args[0]));
                else
                    output.println("No such command");
            else
                output.println("Use help [command]");
        });
        registerDefinition(help);

        Command list = new Command("list", Command.Side.CLIENT, "lists all commands");
        list.setExecutor((args) -> {
            output.println("Available commands:");
            for (Command c : commands.values()) {
                output.printf("%.5s\n", c.getCommand());
            }
        });
        registerDefinition(list);

        Command print = new Command("print", Command.Side.CLIENT, "prints variables to console");
        print.setExecutor((args) -> {
            for (String s : args)
                output.printf("%s\n", s);
        });
        registerDefinition(print);
    }

    /**
     * Adds Command to list of available commands.
     *
     * @param c Command to add
     */
    public void registerDefinition(Command c) {
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
    public void execute(String line) {
        String[] args = line.split(" ");
        String commandString = args[0];
        args = ArrayUtils.removeElement(args, commandString);
        parseVariables(args);

        Command command = commands.get(commandString);
        if (command == null) {
            output.println("No such command. Type 'list' to list available commands");
            return;
        }

        if (command.getSide() == Command.Side.CLIENT) {
            command.execute(args);
        } else {
            //TODO send command to server
        }
    }

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

}
