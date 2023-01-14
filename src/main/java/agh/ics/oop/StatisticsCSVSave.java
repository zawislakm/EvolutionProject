package agh.ics.oop;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
    save statistic to CSV if required, notified about next day by IPositionChangeObserver
 */
public class StatisticsCSVSave implements IPositionChangeObserver {

    private final WorldMapStatistics worldMapStatistics;
    private final Simulation engineSimulation;

    private FileWriter csvWrite;

    public StatisticsCSVSave(WorldMapStatistics worldMapStatistics, Simulation engine) {
        this.worldMapStatistics = worldMapStatistics;
        this.engineSimulation = engine;

        try {
            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
            this.csvWrite = new FileWriter("src/main/resources/SavedSimulations/ " + timeStamp + ".csv");
        } catch (IOException ex) {
            throw new RuntimeException(ex + "something went wrong during creating CSV file");
        }

    }

    private void updateStats() {
        try {
            if (engineSimulation.getFinishedStatus()) {
                this.csvWrite.close();

            } else {
                List<String> stats = new ArrayList<>();
                worldMapStatistics.update();
                stats.add(Integer.toString(worldMapStatistics.getWorldAge()));
                stats.add(Integer.toString(worldMapStatistics.getAmountOfAnimals()));
                stats.add(Integer.toString(worldMapStatistics.getAmountOfPlants()));
                stats.add(Integer.toString(worldMapStatistics.getAmountOfFreePositions()));
                stats.add(Double.toString(worldMapStatistics.getAverageEnergy()));
                stats.add(Double.toString(worldMapStatistics.getAverageLifeLength()));
                stats.add(worldMapStatistics.getMostCommonGenom().toString());

                for (String stat : stats) {
                    this.csvWrite.append(stat + ";");
                }
                this.csvWrite.append("\n");
                this.csvWrite.flush();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex + "something went wrong during writing to csv");

        }
    }


    public void positionChanged() {
        updateStats();
    }
}
