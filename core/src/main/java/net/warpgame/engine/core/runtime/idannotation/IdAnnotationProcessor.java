package net.warpgame.engine.core.runtime.idannotation;

import net.warpgame.engine.core.runtime.preprocessing.EngineRuntimePreprocessor;
import net.warpgame.engine.core.runtime.processing.Processor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.List;

/**
 * @author Jaca777
 * Created 2018-06-04 at 23
 */
public class IdAnnotationProcessor implements Processor<ClassNode> {

    private static final String ID_OF_DESC = "Lnet/warpgame/engine/core/component/IdOf;";
    private static final int TYPE_FIELD_INDEX = 1;
    private EngineRuntimePreprocessor runtimePreprocessor;

    public IdAnnotationProcessor(EngineRuntimePreprocessor runtimePreprocessor) {
        this.runtimePreprocessor = runtimePreprocessor;
    }

    @Override
    public void process(ClassNode classNode) {
        for(FieldNode fieldNode : (List<FieldNode>) classNode.fields) {
            processField(fieldNode);
        }
    }

    private void processField(FieldNode fieldNode) {
        if(fieldNode.visibleAnnotations != null) {
            for (AnnotationNode annotationNode : (List<AnnotationNode>) fieldNode.visibleAnnotations) {
                processAnnotation(annotationNode);
            }
        }
    }

    private void processAnnotation(AnnotationNode annotationNode) {
        if(annotationNode.values != null) {
            for (Object object : annotationNode.values)
                processAnnotationValue(object);
        }
    }

    private void processAnnotationValue(Object object) {
        if (object instanceof AnnotationNode) {
            AnnotationNode node = (AnnotationNode) object;
            if (node.desc.equals(ID_OF_DESC))
                processIdAnnotation(node);
        }
    }

    private void processIdAnnotation(AnnotationNode node) {
        Type type = (Type) node.values.get(TYPE_FIELD_INDEX);
        String className = type.getClassName();
        int id = runtimePreprocessor.getId(className);
        node.values.add("id");
        node.values.add(id);
    }
}
