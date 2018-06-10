package net.warpgame.engine.net.message;

import java.util.Comparator;

/**
 * @author Hubertus
 * Created 02.01.2018
 */
public class IncomingEnvelopeComparator implements Comparator<IncomingEnvelope> {

    @Override
    public int compare(IncomingEnvelope o1, IncomingEnvelope o2) {
        return o1.getMessageDependencyId() - o2.getMessageDependencyId();
    }
}
