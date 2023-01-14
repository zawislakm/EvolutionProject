package agh.ics.oop;


import java.util.List;


/*
 keeps track of all data/stats for given animal
 */
public class AnimalStatistics {

    private final Animal animal;

    private List<Integer> genom;
    private int whichGenActive;
    private int energy;
    private int eatenPlants;
    private int child;
    private int ageOrDeathDay;
    private boolean isAlive;


    public AnimalStatistics(Animal animal) {
        this.animal = animal;
        update();
    }

    public void update() {
        this.genom = this.animal.genom;
        this.whichGenActive = this.animal.whichGenNow;
        this.energy = this.animal.energy;
        this.eatenPlants = this.animal.eatenPlants;
        this.child = this.animal.child;
        this.isAlive = this.animal.isAlive;
        if (this.isAlive) {
            this.ageOrDeathDay = this.animal.age;
        } else {
            this.ageOrDeathDay = this.animal.deathDay;
        }
    }

    public List<Integer> getGenom() {
        return this.genom;
    }

    public int getWhichGenActive() {
        return this.whichGenActive;
    }

    public int getEnergy() {
        return this.energy;
    }

    public int getEatenPlants() {
        return this.eatenPlants;
    }

    public int getChild() {
        return this.child;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public int getAgeOrDeathDay() {
        return this.ageOrDeathDay;
    }


}
