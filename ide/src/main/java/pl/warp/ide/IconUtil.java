package pl.warp.ide;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Jaca777
 *         Created 2017-01-28 at 16
 */
public class IconUtil {
    public static ImageView getIcon(String name) {
        return new ImageView(new Image(IconUtil.class.getResourceAsStream(toIconPath(name))));
    }

    private static String toIconPath(String name) {
        return "icons/"+ name + ".png";
    }
}
