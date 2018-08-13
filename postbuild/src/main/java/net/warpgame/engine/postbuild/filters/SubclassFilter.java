package net.warpgame.engine.postbuild.filters;

import net.warpgame.engine.postbuild.classtree.ClassTree;
import net.warpgame.engine.postbuild.processing.Context;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jaca777
 * Created 2018-08-05 at 23
 */
public class SubclassFilter extends ClassFilter {

    private String superclassName;
    private Set<String> subclasses;

    public SubclassFilter(String superclassName) {
        this.superclassName = superclassName;
    }

    @Override
    protected void init(Context c) {
        this.subclasses = new HashSet<>();
        ClassTree classTree = c.require(ClassTree.class);
        loadSubclasses(classTree, superclassName);
    }

    private void loadSubclasses(ClassTree classTree, String className) {
        Collection<String> names = classTree.getInheritanceLinks().get(className);
        names.forEach(n -> {
            subclasses.add(n);
            loadSubclasses(classTree, n);
        });
    }

    @Override
    protected boolean passesFilter(ClassNode classNode, Context c) {
        return subclasses.contains(classNode.name);
    }

}
