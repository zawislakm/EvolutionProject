package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    private final WorldMap worldMap;
    private final List<IPositionChangeObserver> SimulationObservers = new ArrayList<>();
    private boolean isPaused = false;
    private boolean isFinished = false;
    private final List<Animal> animalsWithMostCommonGenom = new ArrayList<>();

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void run() {

        while (!(this.isFinished)) {
            checkIfPause();

            this.worldMap.killAnimals();
            this.worldMap.moveAnimals();
            this.worldMap.eatPlants();
            this.worldMap.breedAnimals();
            this.worldMap.growPlants();

            worldMap.worldAge += 1;
            if (worldMap.animalsList.size() == 0) {
                this.isFinished = true;
            }
            moveGui();

        }
    }

    private void checkIfPause() {
        if (this.isPaused) {
            findAnimalGenoms();
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex + "system interrupted while pause");
                }
            }
        }
    }

    public void resumeThread() {
        this.isPaused = false;
        clearAnimalGenoms();
        synchronized (this) {
            notify();
        }
    }

    public void pauseThread() {
        this.isPaused = true;
    }

    public boolean getThreadStatus() {
        return this.isPaused;
    }

    public boolean getFinishedStatus() {
        return this.isFinished;
    }


    private void moveGui() {
        notifyObservers();

        try {
            int moveDelay = 1000;
            Thread.sleep(moveDelay);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage() + " Simulation Interrupted during sleep");
        }
    }

    private void notifyObservers() {
        for (IPositionChangeObserver observer : this.SimulationObservers) {
            observer.positionChanged();
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.SimulationObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        this.SimulationObservers.remove(observer);
    }


    //sets animal with most commonGenom to show
    private void findAnimalGenoms() {

        List<Integer> mostCommonGenom = worldMap.worldMapStatistics.getMostCommonGenom();
        for (Animal animal : worldMap.animalsList) {
            if (animal.genom.equals(mostCommonGenom)) {
                animal.withMostCommon = true;
                animalsWithMostCommonGenom.add(animal);
            }
        }
        notifyObservers();
    }

    //sets animal with most commonGenom to look normal
    private void clearAnimalGenoms() {

        for (Animal animal : this.animalsWithMostCommonGenom) {
            animal.withMostCommon = false;
        }
        animalsWithMostCommonGenom.clear();
        notifyObservers();
    }

}
