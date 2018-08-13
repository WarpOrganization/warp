package net.warpgame.engine.postbuild.source;

import net.warpgame.engine.postbuild.buildclass.BuildClassesParallelProcessor;
import net.warpgame.engine.postbuild.buildclass.CodeProcessor;
import net.warpgame.engine.postbuild.processing.Context;
import org.objectweb.asm.tree.ClassNode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Jaca777
 * Created 2018-08-11 at 23
 */
public class CoreCodeProcessor extends BuildClassesParallelProcessor {

    private List<CodeProcessor> codeProcessors;
    private Context context;
    private BuildclassCache cache;

    public CoreCodeProcessor(CodeProcessor... codeProcessors) {
        super("core code processor");
        this.codeProcessors = Arrays.asList(codeProcessors);
    }

    @Override
    protected void init(Context c) {
        this.context = c;
        this.cache = c.require(BuildclassCache.class);
    }

    @Override
    protected ClassNode processElement(ClassNode element) {
        Optional<ClassNode> cachedClass = cache.loadClassFromCache(element.name);
        return cachedClass
                .orElseGet(() -> executeProcessors(element));

    }

    private ClassNode executeProcessors(ClassNode element) {
        codeProcessors.forEach(cp -> cp.process(element, context));
        return element;
    }
}
