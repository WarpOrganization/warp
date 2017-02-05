package pl.warp.engine.ai.behaviortree;

/**
 * @author Hubertus
 *         Created 03.01.2017
 */
public abstract class Node {

    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    public static final int RUNNING = 3;

    private boolean isOpen = false;
    private int tickOpened = 0;

    public void enter(Ticker ticker){
        if(tickOpened == ticker.getCurrentTick() - 1 && isOpen){
            onReEnter(ticker);
        }else {
            open(ticker);
        }
    }

    private void open(Ticker ticker) {
        isOpen = true;
        tickOpened = ticker.getCurrentTick();
        onOpen(ticker);
    }

    public void close(Ticker ticker) {
        isOpen = false;
        onClose(ticker);
    }

    abstract int tick(Ticker ticker, int delta);

    protected abstract void onOpen(Ticker ticker);

    protected abstract void onReEnter(Ticker ticker);

    public abstract void addChild(Node node);

    protected abstract void init(Ticker ticker);

    protected abstract void onInit(Ticker ticker);

    protected abstract void onClose(Ticker ticker);
}
