package net.warpgame.engine.audio;

import net.warpgame.engine.audio.command.Command;
import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;
import net.warpgame.engine.core.context.task.ExecuteAfterTask;
import net.warpgame.engine.core.context.task.RegisterTask;
import net.warpgame.engine.core.execution.task.EngineTask;

import java.util.concurrent.BlockingQueue;


@Service
@Profile("client")
@RegisterTask(thread = "audio")
@ExecuteAfterTask(AudioUpdateTask.class)
public class AudioCommandTask extends EngineTask {

    private AudioContext audioContext;
    private BlockingQueue<Command> commandBlockingQueue;

    public AudioCommandTask(AudioContext audioContext) {
        this.audioContext = audioContext;
    }

    @Override
    protected void onInit() {
        commandBlockingQueue = audioContext.getCommands();
    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        try {
            while (!commandBlockingQueue.isEmpty()) {
                commandBlockingQueue.take().execute();
            }
        }
         catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

