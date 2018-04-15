package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class ScreenShotTool {

    public static void takeScreenShot() {
        try {
            ImageIO.write(grabScreen(), "png", new File("resources/screenshots/screen.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static BufferedImage grabScreen() throws Exception {
        try {
            return new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (SecurityException | AWTException e) {
            e.printStackTrace();
        }
        throw new Exception();
    }

}
