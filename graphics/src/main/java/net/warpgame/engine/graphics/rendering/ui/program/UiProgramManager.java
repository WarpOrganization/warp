package net.warpgame.engine.graphics.rendering.ui.program;

import net.warpgame.engine.core.context.service.Profile;
import net.warpgame.engine.core.context.service.Service;

/**
 * @author MarconZet
 * Created 18.08.2018
 */
@Service
@Profile("graphics")
public class UiProgramManager {
    private UiProgram uiProgram;

    public UiProgram getUiProgram() {
        return uiProgram;
    }

    public void setUiProgram(UiProgram uiProgram) {
        this.uiProgram = uiProgram;
    }
}
