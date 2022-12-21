package agh.ics.oop.Gui;

import agh.ics.oop.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class StatisticsGui {

    private WorldMapStatistics worldMapStatistics;
    private Simulation engineSimulation;
    protected GridPane statsGridPane = new GridPane();
    private AnimalStatistics animalStat = null;



//    private LineChart animalsAmountChart;
//
//    private XYChart.Series animalsData = new XYChart.Series();
//    private LineChart<Number, Number> plantAmountChart;
//    private LineChart freePositionsAmountChart;
//    private LineChart averageEnergy;
//    private LineChart averageLifeLength;



    public StatisticsGui(WorldMapStatistics worldMapStatistics, Simulation engine) {
        this.worldMapStatistics = worldMapStatistics;
        this.engineSimulation = engine;
        init();
    }

    protected void setAnimalTracker(AnimalStatistics animalStat){
        this.animalStat = animalStat;
    }

    protected void removeAnimalTracker(){
        this.animalStat = null;
    }



    private void init() {
//        this.statsGridPane.add(getStatistics(), 1, 1, 1, 1);
//        NumberAxis xAxisAnimals = new NumberAxis();
//        xAxisAnimals.setLabel("Days");
//        NumberAxis yAxisAnimals = new NumberAxis();
//        yAxisAnimals.setLabel("Animal Amount");
//        this.animalsAmountChart = new LineChart(xAxisAnimals, yAxisAnimals);
//
//
//        this.animalsData.getData().add(new XYChart.Data<>(this.worldMapStatistics.getWorldAge(), this.worldMapStatistics.getAmountOfAnimals()));
//        this.animalsAmountChart.getData().add(this.animalsData);

        statsGridPane.add(getStatistics(), 1, 1, 1, 1);
    }

    private GridPane getStatistics() {
        GridPane statisticsGridPane = new GridPane();
        statisticsGridPane.setGridLinesVisible(true);

        worldMapStatistics.update();//updates all stats same time
        if (engineSimulation.getFinishedStatus()){
            Label endSimulation = new Label("Simulation ended");
            statisticsGridPane.add(endSimulation,1,1,1,1);
            return statisticsGridPane;
        }

        Label animalsAmount = new Label("Animals Amount: " + this.worldMapStatistics.getAmountOfAnimals());
        Label plantAmount = new Label("Plant Amount: " + this.worldMapStatistics.getAmountOfPlants());
        Label freePositionsAmount = new Label("Free Positions Amount: " + this.worldMapStatistics.getAmountOfFreePositions());
        Label averageEnergy = new Label("Average Energy: " + this.worldMapStatistics.getAverageEnergy());
        Label averageLifeLength = new Label("Average Life Length: " + this.worldMapStatistics.getAverageLifeLength());
        Label mostCommonGenom = new Label("Most common genom: " + this.worldMapStatistics.getMostCommonGenom());

        statisticsGridPane.add(animalsAmount, 1, 1, 1, 1);
        statisticsGridPane.add(plantAmount, 1, 2, 1, 1);
        statisticsGridPane.add(freePositionsAmount, 1, 3, 1, 1);
        statisticsGridPane.add(averageEnergy, 1, 4, 1, 1);
        statisticsGridPane.add(averageLifeLength, 1, 5, 1, 1);
        statisticsGridPane.add(mostCommonGenom, 1, 6, 1, 1);

        if (this.animalStat != null){
            animalStat.update();//updates all stats same time
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
            statisticsGridPane.add(animalGenom,2,1,1,1);
            statisticsGridPane.add(whichGenActive,2,2,1,1);
            statisticsGridPane.add(energy,2,3,1,1);
            statisticsGridPane.add(eatenPlants,2,4,1,1);
            statisticsGridPane.add(childAmount,2,5,1,1);
            statisticsGridPane.add(day,2,6,1,1);
        }

        return statisticsGridPane;
    }

    protected void update() {
        statsGridPane.getChildren().clear();
        statsGridPane.add(getStatistics(), 1, 1, 1, 1);

    }


}
