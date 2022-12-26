package agh.ics.oop;

import agh.ics.oop.MapType.*;
import agh.ics.oop.MutationType.*;
import agh.ics.oop.NextGenType.*;
import agh.ics.oop.PlantGrowType.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*
 parse CSV config to simulation options
 */
public class OptionParser {

    private final  List<Integer> intParameters = new ArrayList<>();
    protected File fileToRead;
    private String filePath;

    public OptionParser(String fileToRead) {
        this.filePath = "src/main/resources/SimulationConfigs/" + fileToRead;
        parseCSV();
    }

    private void parseCSV() {

        Scanner fileScanner;
        try {
            this.fileToRead = new File(filePath);
            fileScanner = new Scanner(this.fileToRead);
            String line = fileScanner.nextLine();
            String[] strParameters = line.split(";");
            for (String prameter : strParameters) {
                intParameters.add(Integer.parseInt(prameter));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e + "File not Found During parse from CSV");
        }
    }

    public WorldMap createMap() throws Exception {

        if (intParameters.size() == 14) {
            IMapType mapType = ((intParameters.get(0) == 0) ? new EarthMap() : new HellMap());
            INextGenType nextGenType = ((intParameters.get(1) == 0) ? new LittleCrazinessGen() : new ExpectedNextGen());
            IMutationType mutationType = ((intParameters.get(2) == 0) ? new FullRandom() : new SlightFix());
            IPlantGrowType plantGrowType = ((intParameters.get(3) == 0) ? new ForestEquator() : new ToxicBodies());
            int width = intParameters.get(4);
            int height = intParameters.get(5);
            int breedCost = intParameters.get(6);
            int genomLenght = intParameters.get(7);
            int minimalEnergyToBreed = intParameters.get(8);
            int quantityGrowEveryDay = intParameters.get(9);
            int energyFromPlant = intParameters.get(10);
            int quantityAnimalSpawnStart = intParameters.get(11);
            int quantityPlantSpawnStart = intParameters.get(12);
            int animalStartEnergy = intParameters.get(13);

            WorldMap worldMap = new WorldMap(mapType, nextGenType, mutationType, plantGrowType, width, height, breedCost,
                    genomLenght, minimalEnergyToBreed, quantityGrowEveryDay, energyFromPlant, quantityAnimalSpawnStart, quantityPlantSpawnStart, animalStartEnergy);
            return worldMap;
        } else {
            throw new Exception("Incorrect amount of starting values");
        }

    }


}
