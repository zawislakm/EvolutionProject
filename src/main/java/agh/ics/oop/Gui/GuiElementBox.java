package agh.ics.oop.Gui;

import agh.ics.oop.WorldMapElement;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class GuiElementBox {

    private static Map<String, Image> alreadyLoadedElements = new HashMap<>();

    private final WorldMapElement worldMapElement;

    public GuiElementBox(WorldMapElement worldMapElement) {
        this.worldMapElement = worldMapElement;
    }


    public VBox getVBox() {//Object
        if (this.worldMapElement == null) { //object null
            return new VBox();
        }

        String imageName = worldMapElement.getImageName();
        Image image = getImage(imageName);
        // check if image is already loaded, if not -> load it and add to hashmap with already loaded images

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        VBox vBox = new VBox(imageView);
        vBox.setAlignment(Pos.CENTER);

        return vBox;
    }

    public Image getImage(String imageName) {
        Image image = null;
        if (alreadyLoadedElements.get(imageName) == null) {
            try {
                alreadyLoadedElements.put(imageName, new Image(new FileInputStream(imageName)));
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage() + " Something went wrong during loading image");
                System.exit(-1);
            }
        }
        image = alreadyLoadedElements.get(imageName);
        return image;
    }
}
