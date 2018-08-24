package net.warpgame.test.command;

import net.warpgame.engine.console.ConsoleService;
import net.warpgame.engine.console.command.Command;
import net.warpgame.engine.console.command.CommandVariable;
import net.warpgame.engine.core.property.Property;
import net.warpgame.engine.core.property.TransformProperty;
import net.warpgame.engine.graphics.camera.CameraHolder;
import org.joml.Vector3f;

/**
 * @author KocproZ
 * Created 2018-01-10 at 21:02
 */
public class MoveCameraCommand extends Command {

    private CameraHolder cameraHolder;
    private ConsoleService consoleService;

    public MoveCameraCommand(CameraHolder holder, ConsoleService consoleService) {
        super("move", "Moves camera", "move [x] [y] [z]");
        this.cameraHolder = holder;
        this.consoleService = consoleService;

        consoleService.registerVariable(new CommandVariable("camera", holder));
    }

    public void execute(String... args) {
        if (args.length == 3) {
            ((TransformProperty) cameraHolder.getCameraComponent().getProperty(Property.getTypeId(TransformProperty.class)))
                    .move(new Vector3f(Float.valueOf(args[0]), Float.valueOf(args[1]), Float.valueOf(args[2])));
        } else {
            consoleService.print(getUsageText());
        }
    }

}
