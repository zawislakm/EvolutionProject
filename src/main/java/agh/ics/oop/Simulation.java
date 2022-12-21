package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {

    private WorldMap worldMap;
    private final List<IPositionChangeObserver> SimulationObservers = new ArrayList<>();
    private boolean isPaused = false;
    private boolean isFinished = false;
    private List<Animal> animalsWithMostCommonGenom = new ArrayList<>();

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;
    }

    public void run() {

        System.out.println(worldMap);
        while (!(this.isFinished)) {
            checkIfPause();
            //usuniecie martwych
            this.worldMap.killAnimals();
            //ruszenie zwierzakami
            this.worldMap.moveAnimals(); //dziala chyba
            //konsumpcja roslin
            this.worldMap.eatPlants(); //wyglada ze dziala
            //rozmazanie sie zwierzakow
            this.worldMap.breedAnimals();
            //wzrastanie nowych roslin
            this.worldMap.growPlants();

            worldMap.worldAge += 1;
            if (worldMap.animalsList.size()==0){
                this.isFinished = true;
            }
            moveGui();
            //System.out.println(worldMap);
        }

        moveGui();

        System.out.println("start");
        System.out.println(worldMap);

        System.out.println("koniec");

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

    public boolean getFinishedStatus(){
        return this.isFinished;
    }


    private void moveGui() {
        notifyObservers();

        try {
            int moveDelay = 1000;
            Thread.sleep(moveDelay);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage() + "Simulation Interupted during sleep");
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

    public void removeObserver(IPositionChangeObserver observer){
        this.SimulationObservers.remove(observer);
    }


    private void findAnimalGenoms(){
        //sets animal with most commonGenom to show
        List<Integer> mostCommonGenom = worldMap.worldMapStatistics.getMostCommonGenom();
        for (Animal animal : worldMap.animalsList){
            if(animal.genom.equals(mostCommonGenom)){
                animal.withMostCommon = true;
                animalsWithMostCommonGenom.add(animal);
            }
        }
        notifyObservers();
    }

    private void clearAnimalGenoms(){
        //sets animal with most commonGenom to look normal
        for(Animal animal : this.animalsWithMostCommonGenom){
            animal.withMostCommon = false;
        }
        animalsWithMostCommonGenom.clear();
        notifyObservers();
    }

}
