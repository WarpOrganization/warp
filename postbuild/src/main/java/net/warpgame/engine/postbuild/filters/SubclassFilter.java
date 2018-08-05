package net.warpgame.engine.postbuild.filters;

import org.objectweb.asm.tree.ClassNode;

/**
 * @author Jaca777
 * Created 2018-08-05 at 23
 */
public class SubclassFilter extends ClassFilter {

    private String superclassName;

    public SubclassFilter(String superclassName) {
        this.superclassName = superclassName;
    }

    @Override
    protected boolean passesFilter(ClassNode classNode) {
        return classNode.superName.equals(superclassName);
    }

}
