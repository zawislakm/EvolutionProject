package agh.ics.oop.Gui;

import agh.ics.oop.*;
import agh.ics.oop.IPositionChangeObserver;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MapWindow implements IPositionChangeObserver {


    private WorldMap worldMap;
    private Simulation engine;
    private final GridPane mapWindow = new GridPane();
    private boolean saveCSVInfo;
    private StatisticsGui StatisticsGui;
    private int mapNumber;
    private Animal tracedAnimal = null;
    private Vector2d upperRight;

    public MapWindow(Simulation engine, WorldMap worldMap, boolean infoFromCheckBox, int mapNumber) {
        this.engine = engine;
        this.worldMap = worldMap;
        this.saveCSVInfo = infoFromCheckBox;
        this.mapNumber = mapNumber;
        this.upperRight = worldMap.getUpperRight();
        this.StatisticsGui = new StatisticsGui(worldMap.worldMapStatistics, engine);
    }

    protected void start() {
        this.upperRight = worldMap.getUpperRight();
        mapWindow.getChildren().add(drawMapGridPane());

        this.engine.addObserver(this);
        if (this.saveCSVInfo) {
            this.engine.addObserver(new StatisticsCSVSave(this.worldMap.worldMapStatistics, engine));
        }

        Thread engineThread = new Thread(this.engine);
        engineThread.start();

        int width = (worldMap.getUpperRight().x + 1) * 90;
        int height = (worldMap.getUpperRight().y + 1) * 60;


        Scene mapWindowScene = new Scene(mapWindow, width, height);
        Stage newWindowStage = new Stage();
        newWindowStage.setScene(mapWindowScene);
        newWindowStage.setTitle("Map number " + this.mapNumber);

        newWindowStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                engine.setFinishedStatus();
            }
        });
        newWindowStage.show();
    }


    private Button pauseButton() {
        Button pauseButton = null;
        if (engine.getThreadStatus()) {
            pauseButton = new Button("Resume");
        } else {
            pauseButton = new Button("Pause");
        }

        pauseButton.setOnAction(click -> {
            if (engine.getThreadStatus()) {
                engine.resumeThread();
            } else {
                engine.pauseThread();
            }
        });
        return pauseButton;
    }

    private Button stopTrack() {
        Button stopTrack = new Button("Stop tracking");
        stopTrack.setOnAction(click -> {
            //end tracking animal
            this.tracedAnimal.beingTraced = false;
            this.tracedAnimal = null;
            this.StatisticsGui.removeAnimalTracker();
            update();
        });
        return stopTrack;
    }

    private Button stopSimulation() {
        Button stopSimulation = new Button("Stop simulation");
        stopSimulation.setOnAction(click -> {
            engine.setFinishedStatus();
        });
        return stopSimulation;
    }

    private HBox createButtons() {
        HBox buttonHBox = new HBox();
        if (engine.getFinishedStatus()){
            return buttonHBox;
        }

        buttonHBox.getChildren().add(pauseButton());
        if (this.tracedAnimal != null) {
            buttonHBox.getChildren().add(stopTrack());
        }
        buttonHBox.getChildren().add(stopSimulation());

        return buttonHBox;
    }


    private GridPane drawMapGridPane() {
        GridPane mapGridPane = new GridPane();
        mapGridPane.setGridLinesVisible(true);


        for (int x = 0; x <= upperRight.x; x++) {
            for (int y = 0; y <= upperRight.y; y++) {
                Vector2d nowPosition = new Vector2d(x, y);
                Object objectAt = this.worldMap.objectAt(nowPosition);
                GuiElementBox guiElementBox = new GuiElementBox((WorldMapElement) objectAt);

                VBox vBox = guiElementBox.getVBox();

                if (objectAt instanceof Animal && engine.getThreadStatus() && this.tracedAnimal == null) {
                    Button button = new Button();

                    button.setOnAction(click -> {
                        this.tracedAnimal = (Animal) objectAt;
                        this.tracedAnimal.beingTraced = true;
                        this.StatisticsGui.setAnimalTracker(new AnimalStatistics(this.tracedAnimal));
                        update();
                    });

                    button.setGraphic(vBox);
                    mapGridPane.add(button, x, y, 1, 1);
                } else {

                    mapGridPane.add(vBox, x, y, 1, 1);
                    GridPane.setHalignment(vBox, HPos.CENTER);
                }
            }
        }
        for (int x = 0; x <= upperRight.x; x++) {
            mapGridPane.getColumnConstraints().add(new ColumnConstraints(40));
        }
        for (int y = 0; y <= upperRight.y; y++) {
            mapGridPane.getRowConstraints().add(new RowConstraints(40));
        }

        return mapGridPane;
    }



    private void update() {
        this.StatisticsGui.update();
        this.mapWindow.setHgap(10);
        this.mapWindow.setVgap(10);
        this.mapWindow.getChildren().clear();
        this.mapWindow.add(drawMapGridPane(), 1, 1, 1, 1);
        this.mapWindow.add(this.StatisticsGui.statsVBox, 2, 1, 1, 1);
        this.mapWindow.add(createButtons(), 1, 2, 1, 1);

//        this.mapWindow.setSpacing(20);
//        this.mapWindow.getChildren().add(drawMapGridPane());
//        this.mapWindow.getChildren().add(this.StatisticsGui.statsVBox);
//        this.mapWindow.getChildren().add(createButtons());
    }

    public void positionChanged() {
        Platform.runLater(() -> {
            update();
        });
    }
}
