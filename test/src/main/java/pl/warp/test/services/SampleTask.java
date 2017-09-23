package pl.warp.test.services;

import pl.warp.engine.core.context.service.Service;
import pl.warp.engine.core.context.task.RegisterTask;
import pl.warp.engine.core.execution.task.EngineTask;

/**
 * @author Jaca777
 * Created 2017-09-23 at 16
 */

@Service
@RegisterTask(thread = "sample")
public class SampleTask extends EngineTask{
    @Override
    protected void onInit() {
        System.out.println("INITTT");
    }

    @Override
    protected void onClose() {

    }

    @Override
    public void update(int delta) {
        System.out.println("UPDATEEE");
    }
}
