package me.card.solitaire.graphical;

import javax.imageio.ImageIO;
import javax.swing.plaf.synth.SynthUI;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * The type Texture.
 */
public class Texture {

    /**
     * The constant CARD_BACK.
     */
    public static BufferedImage CARD_BACK = new Texture("/cardback.png").get();

    private String path;

    private Texture(String path) {
        this.path = path;
    }

    /**
     * Retrieves the image from internal jar, if not found will try to retrieve from resources folder
     *
     * @return the image or a 1x1 blank image if unable to find
     */
    private BufferedImage get() {
        try {
            URL url = getClass().getResource(path);
            if (url == null) {
                File file = new File("resources" + path);
                return ImageIO.read(file);
            } else {
                return ImageIO.read(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    }
}
