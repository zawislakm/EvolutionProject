package agh.ics.oop.Gui;

import agh.ics.oop.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StatisticsGui {

    private WorldMapStatistics worldMapStatistics;
    private Simulation engineSimulation;
    protected VBox statsVBox = new VBox();
    private AnimalStatistics animalStat = null;

    public StatisticsGui(WorldMapStatistics worldMapStatistics, Simulation engine) {
        this.worldMapStatistics = worldMapStatistics;
        this.engineSimulation = engine;
        update();
    }

    protected void setAnimalTracker(AnimalStatistics animalStat) {
        this.animalStat = animalStat;
    }

    protected void removeAnimalTracker() {
        this.animalStat = null;
    }


    private VBox getStatistics() {
        VBox boxWithStats = new VBox();

        GridPane worldStatGridPane = new GridPane();
        worldStatGridPane.setGridLinesVisible(true);

        worldMapStatistics.update();//updates all stats same time


        Label animalsAmount = new Label("Animals Amount: " + this.worldMapStatistics.getAmountOfAnimals());
        Label plantAmount = new Label("Plant Amount: " + this.worldMapStatistics.getAmountOfPlants());
        Label freePositionsAmount = new Label("Free Positions Amount: " + this.worldMapStatistics.getAmountOfFreePositions());
        Label averageEnergy = new Label("Average Energy: " + this.worldMapStatistics.getAverageEnergy());
        Label averageLifeLength = new Label("Average Life Length: " + this.worldMapStatistics.getAverageLifeLength());
        Label mostCommonGenom = new Label("Most common genom: " + this.worldMapStatistics.getMostCommonGenom());

        worldStatGridPane.add(animalsAmount, 1, 1, 1, 1);
        worldStatGridPane.add(plantAmount, 1, 2, 1, 1);
        worldStatGridPane.add(freePositionsAmount, 1, 3, 1, 1);
        worldStatGridPane.add(averageEnergy, 1, 4, 1, 1);
        worldStatGridPane.add(averageLifeLength, 1, 5, 1, 1);
        worldStatGridPane.add(mostCommonGenom, 1, 6, 1, 1);


        if (engineSimulation.getFinishedStatus()) {
            Label endSimulation = new Label("SIMULATION ENDED");
            worldStatGridPane.add(endSimulation, 1, 7, 1, 1);
        }

        boxWithStats.setSpacing(20);
        boxWithStats.getChildren().add(worldStatGridPane);

        if (this.animalStat != null) {
            animalStat.update();//updates all stats same time
            GridPane aniamlStatGridPane = new GridPane();
            aniamlStatGridPane.setGridLinesVisible(true);
            Label animalGenom = new Label("Animal's genom: " + this.animalStat.getGenom());
            Label whichGenActive = new Label("Active part of genom: " + this.animalStat.getWhichGenActive());
            Label energy = new Label("Energy amount: " + this.animalStat.getEnergy());
            Label eatenPlants = new Label("Eaten Plants amount: " + this.animalStat.getEatenPlants());
            Label childAmount = new Label("Child amount: " + this.animalStat.getChild());

            Label day = null;
            if (this.animalStat.isAlive()) {
                day = new Label("Age: " + this.animalStat.getAgeOrDeathDay());
            } else {
                day = new Label("Died at day" + this.animalStat.getAgeOrDeathDay());
            }
            aniamlStatGridPane.add(animalGenom, 2, 1, 1, 1);
            aniamlStatGridPane.add(whichGenActive, 2, 2, 1, 1);
            aniamlStatGridPane.add(energy, 2, 3, 1, 1);
            aniamlStatGridPane.add(eatenPlants, 2, 4, 1, 1);
            aniamlStatGridPane.add(childAmount, 2, 5, 1, 1);
            aniamlStatGridPane.add(day, 2, 6, 1, 1);
            boxWithStats.getChildren().add(aniamlStatGridPane);
        }

        return boxWithStats;
    }

    protected void update() {
        statsVBox.getChildren().clear();
        statsVBox.getChildren().add(getStatistics());
    }


}
