package pl.warp.ide.scene.descriptor;

import pl.warp.engine.core.scene.Component;
import pl.warp.ide.scene.ComponentDescriptor;

import javax.xml.soap.Node;

/**
 * Created by user on 2017-01-17.
 */
public interface DescriptorRepository {
    ComponentDescriptor getDesc(Component component);
}
