package net.warpgame.engine.postbuild.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * @author Jaca777
 * Created 2018-08-11 at 22
 */
public abstract class ParallelProcessor<T, R, E, S> implements Processor<T, R> {

    private static final Logger logger = LoggerFactory.getLogger(ParallelProcessor.class);
    private static final float PROCESSOR_PROGRESS_LOGGING_INTERVAL = 0.1f;

    private String name;
    private ExecutorService executorService;

    public ParallelProcessor(String name) {
        this.name = name;
    }

    public ParallelProcessor() {
        this(null);
    }

    @Override
    public R process(T t, Context context) {
        init(context);
        CompletionService completionService = prepareExecutorService();
        List<E> elements = getElements(t);
        logger.debug("Parallel processor [{}] starts processing {} elements", getName(), elements.size());
        submitElements(completionService, elements);
        return processResults(completionService, elements.size());
    }

    private CompletionService prepareExecutorService() {
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 4
        );
        return new ExecutorCompletionService(executorService);
    }

    private void submitElements(CompletionService completionService, List<E> elements) {
        elements.stream()
                .map(s -> (Callable<S>) () -> processElement(s))
                .forEach(completionService::submit);
    }

    private R processResults(CompletionService completionService, int totalElements) {
        int processedElements = 0;
        try {
            List<S> results = new ArrayList<>();
            while (processedElements < totalElements) {
                Future<S> result = completionService.take();
                results.add(result.get());
                processedElements++;
                logProgress(processedElements, totalElements);
            }
            logger.debug("Parallel processor [{}] finished processing", getName());
            return mapResults(results);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
             executorService.shutdown();
        }
    }

    private float lastLoggedOn = -1f;

    private  void logProgress(int processedElements, int totalElements){
        float progress = processedElements / (float) totalElements;
        if(progress >= lastLoggedOn + PROCESSOR_PROGRESS_LOGGING_INTERVAL) {
            logger.debug("Parallel processor [{}] at {}%", getName(), getProgress(progress));
            lastLoggedOn = progress;
        }
    }
    public String getName() {
        return Objects.isNull(name)
                ? Processor.super.getName()
                : name;
    }

    private int getProgress(float progress) {
        return (int)(progress * 100);
    }


    protected abstract void init(Context c);

    protected abstract List<E> getElements(T t);

    protected abstract S processElement(E element);

    protected abstract R mapResults(List<S> results);

}
