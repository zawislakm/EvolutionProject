package agh.ics.oop;

import java.util.*;

/*
 keeps track of all data/stats connected with WorldMap
 */
public class WorldMapStatistics {

    private final WorldMap worldMap;

    private int amountOfAnimals;
    private int amountOfPlants;
    private int amountOfFreePositions;
    private List<Integer> mostCommonGenom;
    private double averageEnergy;
    private double averageLifeLength;
    private int maxEnergy;

    public WorldMapStatistics(WorldMap worldMap) {
        this.worldMap = worldMap;
    }
    public int getAmountOfAnimals() {
        return this.amountOfAnimals;
    }
    public int getAmountOfPlants() {
        return this.amountOfPlants;
    }
    public int getAmountOfFreePositions() {
        return this.amountOfFreePositions;
    }
    public List<Integer> getMostCommonGenom() {
        return this.mostCommonGenom;
    }
    public double getAverageEnergy() {
        return this.averageEnergy;
    }
    public double getAverageLifeLength() {
        return this.averageLifeLength;
    }
    public int getWorldAge() {
        return worldMap.worldAge;
    }
    public int getMaxEnergy() {
        return this.maxEnergy;
    }
    public void update() {
        updateAnimalsAmount();
        updatePlantsAmount();
        updateFreePositionsAmount();
        updateMaxEnergy();
        updateAverageEnergy();
        updateAverageLifeLength();
        updateMostCommonGenom();
    }
    private void updateAnimalsAmount() {
        this.amountOfAnimals = worldMap.animalsList.size();
    }
    private void updatePlantsAmount() {
        this.amountOfPlants = worldMap.plantsList.size();
    }
    private void updateFreePositionsAmount() {
        this.amountOfFreePositions = worldMap.freePositions.size();
    }
    private void updateMaxEnergy() {
        this.maxEnergy = 0;
        for (Animal animal : worldMap.animalsList) {
            this.maxEnergy = Math.max(this.maxEnergy, animal.energy);
        }
    }
    private void updateAverageEnergy() {
        double newAverageEnergy = 0;
        for (Animal animal : worldMap.animalsList) {
            newAverageEnergy += animal.energy;
        }
        newAverageEnergy = newAverageEnergy / worldMap.animalsList.size();
        this.averageEnergy = Math.round(newAverageEnergy);
    }
    private void updateAverageLifeLength() {
        if (worldMap.deadAnimalList.size() == 0) {
            this.averageLifeLength = 0;
        } else {
            double newAverageLifeLength = 0;
            for (Animal animal : worldMap.deadAnimalList) {
                newAverageLifeLength += animal.age;
            }
            newAverageLifeLength = newAverageLifeLength / worldMap.deadAnimalList.size();
            this.averageLifeLength = Math.round(newAverageLifeLength);
        }
    }
    private void updateMostCommonGenom() { //not done

        if (worldMap.animalsList.size() == 0) {
            this.mostCommonGenom = null;
        } else {
            Map<List<Integer>, Integer> genomCount = new HashMap<>();

            for (Animal animal : worldMap.animalsList) {
                List<Integer> nowAnimalGenom = animal.genom;
                int count = ((genomCount.get(nowAnimalGenom) == null) ? 1 : 1 + genomCount.get(nowAnimalGenom));
                genomCount.put(nowAnimalGenom, count);
            }

            List<Map.Entry<List<Integer>, Integer>> list = new ArrayList<>(genomCount.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<List<Integer>, Integer>>() {
                public int compare(Map.Entry<List<Integer>, Integer> o1, Map.Entry<List<Integer>, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }
            });

            this.mostCommonGenom = list.get(0).getKey();
        }
    }
}
