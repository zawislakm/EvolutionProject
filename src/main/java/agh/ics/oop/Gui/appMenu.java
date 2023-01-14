package agh.ics.oop.Gui;

import agh.ics.oop.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

import java.io.File;

public class appMenu {


    protected final VBox menuVBox = new VBox();
    private int mapNumber = 0;

    public appMenu() {
        createMenu();
    }

    private ComboBox createComboBox() {
        ComboBox comboBox = new ComboBox();


        try {
            File dir = new File("src/main/resources/SimulationConfigs");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File config : directoryListing) {
                    comboBox.getItems().add(config.getName());
                }
            }
            comboBox.getSelectionModel().select(0);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex + " Something went wrong during reading map configs");
        }
        return comboBox;
    }

    private CheckBox checkBoxSaveCSV() {
        CheckBox checkBox = new CheckBox("Write data to CSV");

        return checkBox;
    }

    private void createMenu() {
        Button startButton = new Button();
        startButton.setText("Start Button");
        ComboBox comboBox = createComboBox();
        CheckBox checkBoxCSV = checkBoxSaveCSV();
        Label startLabel = new Label("Pick World Configuration");
        startButton.setOnAction(click -> {

            OptionParser parser = new OptionParser((String) comboBox.getValue());
            WorldMap simulationMap = null;
            try {
                simulationMap = parser.createMap();
            } catch (Exception ex) { // nie wolno łapać Exception
                throw new RuntimeException(ex + "something wrong with csv config");
            }
            Simulation engine = new Simulation(simulationMap);
            MapWindow mapWindow = new MapWindow(engine, simulationMap, checkBoxCSV.isSelected(), this.mapNumber);
            this.mapNumber += 1;
            mapWindow.start();

        });

        this.menuVBox.setSpacing(20);
        this.menuVBox.setStyle("-fx-padding: 20;");
        this.menuVBox.getChildren().add(startLabel);
        this.menuVBox.getChildren().add(comboBox);
        this.menuVBox.getChildren().add(checkBoxCSV);
        this.menuVBox.getChildren().add(startButton);
        this.menuVBox.setAlignment(Pos.BASELINE_CENTER);

    }


}
