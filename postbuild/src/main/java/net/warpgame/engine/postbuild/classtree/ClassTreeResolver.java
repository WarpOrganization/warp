package net.warpgame.engine.postbuild.classtree;

import net.warpgame.engine.core.graph.DAG;
import net.warpgame.engine.postbuild.loader.BuildClasses;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jaca777
 * Created 2018-07-01 at 17
 */
public class ClassTreeResolver {

    private BuildClasses buildClasses;
    private Map<String, String> inheritanceLinks = new HashMap<>();
    private DAG<ClassNode> dag = DAG.empty();

    public ClassTreeResolver(BuildClasses buildClasses) {
        this.buildClasses = buildClasses;
    }

    public ClassTree resolveClassTree() {
        createInheritanceLinks();
        createTree();
        return new ClassTree(dag);
    }

    private void createTree() {
        for (Map.Entry<String, String> link : inheritanceLinks.entrySet()) {
            ClassNode from = buildClasses.getClassByName(link.getKey());
            ClassNode to = buildClasses.getClassByName(link.getValue());
            dag.addEdge(from, to);
        }
    }

    private void createInheritanceLinks() {
        for(ClassNode classNode : buildClasses.getBuildClasses()) {
            inheritanceLinks.put(classNode.superName, classNode.name);
        }
    }
}