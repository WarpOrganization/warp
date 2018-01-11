package net.warpgame.test.console;

/**
 * @author KocproZ
 * Created 2018-01-10 at 19:53
 */
public class CommandVariable {
    private String name;
    private Object value;

    public CommandVariable(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value.toString();
    }

}
