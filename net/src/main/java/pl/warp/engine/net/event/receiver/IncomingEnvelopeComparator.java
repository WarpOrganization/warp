package pl.warp.engine.net.event.receiver;

import java.util.Comparator;

/**
 * @author Hubertus
 * Created 02.01.2018
 */
public class IncomingEnvelopeComparator implements Comparator<IncomingEnvelope> {

    @Override
    public int compare(IncomingEnvelope o1, IncomingEnvelope o2) {
        return o1.getDependencyId() - o2.getDependencyId();
    }
}
