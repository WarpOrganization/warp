package net.warpgame.engine.postbuild.classtree;

import com.google.common.collect.Multimap;

/**
 * @author Jaca777
 * Created 2018-07-01 at 17
 */
public class ClassTree {
    private Multimap<String, String> inheritanceLinks;

    public ClassTree(Multimap<String, String> inheritanceLinks) {
        this.inheritanceLinks = inheritanceLinks;
    }

    public Multimap<String, String> getInheritanceLinks() {
        return inheritanceLinks;
    }
}
