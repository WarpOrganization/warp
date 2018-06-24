package net.warpgame.engine.audio.command;

import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Function;

public class ManualCommand implements Command {

    private Runnable runnable;

    public ManualCommand(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void execute() {
        runnable.run();
    }
}
