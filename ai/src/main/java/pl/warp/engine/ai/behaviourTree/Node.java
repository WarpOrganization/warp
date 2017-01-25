package pl.warp.engine.ai.behaviourTree;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public abstract class Node {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    public static final int RUNNING = 3;

    public static final String OWNER_KEY = "owner";
    private boolean isOpen = false;
    private int tickOpened = 0;

    public void open(int tick) {
        isOpen = true;
        tickOpened = tick;
    }

    public void close() {
        isOpen = false;
    }

    public boolean isOpen(int tick) {
        return tickOpened == tick - 1 && isOpen;
    }

    abstract int tick(Ticker ticker);

    public abstract void onOpen(Ticker ticker);

    public abstract void onEnter(Ticker ticker);

    public abstract void addChild(Node node);
}
