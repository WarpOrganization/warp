package net.warpgame.engine.postbuild.filters;

import net.warpgame.engine.postbuild.buildclass.BuildClasses;
import net.warpgame.engine.postbuild.processing.Processor;
import org.objectweb.asm.tree.ClassNode;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * @author Jaca777
 * Created 2018-08-05 at 23
 */
public abstract class ClassFilter implements Processor<BuildClasses, BuildClasses> {

    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);

    @Override
    public BuildClasses process(BuildClasses buildClasses) {
        CompletableFuture<Optional<ClassNode>>[] completableFutures = runProcessingTasks(buildClasses);
        awaitTasks(completableFutures);
        return getResults(completableFutures);
    }

    private CompletableFuture[] runProcessingTasks(BuildClasses buildClasses) {
        return buildClasses.getBuildClasses()
                .stream()
                .map(this::toCallable)
                .map(c -> CompletableFuture.supplyAsync(c, executorService))
                .toArray(CompletableFuture[]::new);
    }

    private void awaitTasks(CompletableFuture<Optional<ClassNode>>[] completableFutures) {
        CompletableFuture.allOf(completableFutures)
                .join();
    }

    private BuildClasses getResults(CompletableFuture<Optional<ClassNode>>[] completableFutures) {
        BuildClasses result = new BuildClasses();
        Arrays.stream(completableFutures)
                .map(f -> f.getNow(null))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(result::putClass);
        return result;
    }

    private Supplier<Optional<ClassNode>> toCallable(ClassNode classNode) {
        return () -> getOptionalClass(classNode);
    }

    private Optional<ClassNode> getOptionalClass(ClassNode classNode) {
        return passesFilter(classNode)
                ? Optional.of(classNode)
                : Optional.empty();
    }

    protected abstract boolean passesFilter(ClassNode classNode);
}
