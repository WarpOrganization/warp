package pl.warp.engine.core.script.metadata;

import pl.warp.engine.core.context.annotation.Service;
import pl.warp.engine.core.script.Script;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaca777
 * Created 2017-09-08 at 22
 */
@Service
public class ScriptMetadataSupplier {

    private Map<Class<? extends Script>, ScriptMetadata> scriptMetadata;
    private ScriptMetadataLoader metadataLoader;

    public ScriptMetadataSupplier(ScriptMetadataLoader metadataLoader) {
        this.scriptMetadata = new HashMap<>();
        this.metadataLoader = metadataLoader;
    }

    public ScriptMetadata getMetadata(Class<? extends Script> scriptClass) {
        return scriptMetadata.computeIfAbsent(scriptClass, metadataLoader::loadMetadata);
    }
}
