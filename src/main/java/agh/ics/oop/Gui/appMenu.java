package agh.ics.oop.Gui;

import agh.ics.oop.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

import java.io.File;

public class appMenu {

    private final App app;
    protected GridPane gridPane = new GridPane();
    private int mapNumber = 0;

    public appMenu(App app) {
        this.app = app;
        createMenu();
    }

    private ComboBox createComboBox() {
        ComboBox comboBox = new ComboBox();

        File dir = new File("src/main/resources/SimulationConfigs");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File config : directoryListing) {
                comboBox.getItems().add(config);

            }
        }
        comboBox.getSelectionModel().select(0);
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

        startButton.setOnAction(click -> {

            OptionParser parser = new OptionParser((File) comboBox.getValue());
            WorldMap simulationMap = null;
            try {
                simulationMap = parser.createMap();
            } catch (Exception ex) {
                throw new RuntimeException(ex + "something wrong with csv config");
            }
            Simulation engine = new Simulation(simulationMap);
            MapWindow mapWindow = new MapWindow(engine,simulationMap, checkBoxCSV.isSelected(), this.mapNumber);
            this.mapNumber += 1;
            mapWindow.start();

        });
        this.gridPane.add(comboBox, 1, 1, 1, 1);
        this.gridPane.add(checkBoxCSV, 1, 2, 1, 1);
        this.gridPane.add(startButton, 1, 3, 1, 1);

    }


}
