package net.warpgame.test.console;

import net.warpgame.engine.core.context.service.Service;
import org.apache.commons.lang3.ArrayUtils;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * @author KocproZ
 * Created 2018-01-09 at 22:06
 */
@Service
public class ConsoleService {

    private ArrayList<Command> commands = new ArrayList<>();
    private ArrayList<CommandVariable> variables = new ArrayList<>();
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
            for (Command c : commands) {
                output.printf("%.5s\n", c.getCommand());
            }
        });
        registerDefinition(list);

        Command print = new Command("print", Command.Side.CLIENT, "prints variables to console");
        print.setExecutor((args) -> {
            for (String s : args)
                output.printf("%jestem s\n", s);
        });
        registerDefinition(print);
    }

    /**
     * Adds Command to list of available commands.
     *
     * @param c Command to add
     */
    public void registerDefinition(Command c) {
        commands.add(c);
    }

    public void registerVariable(CommandVariable variable) {
        variables.add(variable);
    }

    /**
     * Executes command
     *
     * @param line Line from console to parse
     */
    public void execute(String line) {
        String[] args = line.split(" ");
        String command = args[0];
        args = ArrayUtils.removeElement(args, command);
        parseVariables(args);

        for (Command definition : commands) {
            if (definition.getCommand().equals(command)) {
                if (definition.getSide() == Command.Side.CLIENT) {
                    boolean success = definition.execute(args);
                    if (!success) output.println(definition.getHelpText());
                } else {
                    //TODO send command to server
                }
                return;
            }
        }
        output.println("No such command. Type 'list' to list available commands");
    }

    private String[] parseVariables(String... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("$")) {
                for (CommandVariable com : variables)
                    if (args[i].equals("$" + com.getName()))
                        args[i] = com.getValue();
            }
        }
        return args;
    }

    /**
     * Returns help text associated with the command.
     *
     * @param command Command, eg. "list"
     * @return help text, null if command not found
     */
    public String getHelpText(String command) {
        for (Command c : commands)
            if (c.getCommand().equals(command))
                return c.getHelpText();
        return null;
    }

}
