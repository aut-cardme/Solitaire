package me.card.solitaire.graphical;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {

    public static BufferedImage CARD_BACK = new Texture("resources/cardback.png").get();

    private String path;

    private Texture(String path){
        this.path = path;
    }

    private BufferedImage get(){
        try {
            File file = new File(path);
            System.out.println(file.getAbsolutePath());
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
