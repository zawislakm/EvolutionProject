package agh.ics.oop;

import agh.ics.oop.Enums.AnimalEnergyVisualisation;
import agh.ics.oop.Enums.MapDirection;

import java.util.ArrayList;
import java.util.List;

public class Animal extends WorldMapElement {

    private final WorldMap worldMap;
    protected MapDirection orientation = MapDirection.NORTH;
    protected int energy;
    protected List<Integer> genom;
    protected int whichGenNow;

    //stats
    protected int age = 0;
    protected int child = 0;
    protected int eatenPlants = 0;
    protected int deathDay;
    protected boolean isAlive = true;

    //type of image
    public boolean withMostCommon = false;
    public boolean beingTraced = false;
    private AnimalEnergyVisualisation animalStatus = AnimalEnergyVisualisation.HEALTHY;

    public Animal(WorldMap worldMap, Vector2d position, int energy, List<Integer> genom) {
        this.worldMap = worldMap;
        this.position = position;
        this.energy = energy;
        this.genom = genom;
        this.whichGenNow = (int) (Math.random() * genom.size());
        this.orientation.random((int) (Math.random() * 8));
    }

    public String toString() {
        return switch (this.orientation) {
            case NORTH -> "N ";
            case NORTHEAST -> "NE";
            case EAST -> "E ";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S ";
            case SOUTHWEST -> "SW";
            case WEST -> "W ";
            case NORTHWEST -> "NW";
        };
    }

    public String getImageName() {
        if (this.withMostCommon) {
            return "src/main/resources/Images/COMMON.png";
        }

        if (this.beingTraced) {
            return "src/main/resources/Images/TRACED.png";
        }
        int maxEnergy = worldMap.worldMapStatistics.getMaxEnergy();
        this.animalStatus = this.animalStatus.getStatus(maxEnergy, this.energy);


        return this.animalStatus.getImagePath();

    }


    protected void move() {

        this.age += 1;
        this.orientation = this.orientation.orientationRotationFromInt(genom.get(whichGenNow)); // change orientation
        this.whichGenNow = this.worldMap.nextGen(whichGenNow); //pick which gen will be used next
        Vector2d moveVector = orientation.toUnitVector();
        Vector2d newPosition = this.position.add(moveVector);
        newPosition = this.worldMap.mapSpecification(this, newPosition); //EarthMap or HellMap IMapType
        this.position = newPosition;
    }

    public void lowerEnergy(int moveCost){
        this.energy -= moveCost;
    }

    public void animalRotate180(){
        this.orientation = this.orientation.orientationRotationFromInt(4);
    }
    protected void feedAnimal(int powerUp) {
        this.eatenPlants += 1;
        this.energy += powerUp;
    }

    protected Animal breedAnimal(Animal weaker, int EnergyToBreed) {
        int sumOfEnergy = this.energy + weaker.energy;
        double strongerPercentage = this.energy / (double) sumOfEnergy;
        double weakerPercentage = 1 - strongerPercentage;


        int strongerCost = (int) (Math.ceil(EnergyToBreed * strongerPercentage));
        int weakerCost = EnergyToBreed - strongerCost;
        this.energy -= strongerCost;
        weaker.energy -= weakerCost;

        this.child += 1;
        weaker.child += 1;

        int strongerGenLength = (int) (Math.ceil(this.genom.size() * strongerPercentage));
        int weakerGenLength = this.genom.size() - strongerGenLength;
        int pickStrongerSide = (int) (Math.random() * 2); //0 -> right, 1 -> left
        List<Integer> newBornGenom = new ArrayList<>();


        //creating genom for newBorn animal
        if (pickStrongerSide == 0) {
            int weakerStart = strongerGenLength;


            for (int i = 0; i < weakerStart; i++) {
                newBornGenom.add(this.genom.get(i));
            }
            for (int i = weakerStart; i < this.genom.size(); i++) {
                newBornGenom.add(weaker.genom.get(i));
            }

        } else {
            int strongerStart = weakerGenLength;

            for (int i = 0; i < strongerStart; i++) {
                newBornGenom.add(weaker.genom.get(i));
            }

            for (int i = strongerStart; i < this.genom.size(); i++) {
                newBornGenom.add(this.genom.get(i));
            }

        }

        newBornGenom = this.worldMap.mutateGenom(newBornGenom); //FullRandom or SlightFix IMutationType

        return new Animal(this.worldMap, this.position, EnergyToBreed, newBornGenom);
    }

    protected void kill(int worldAge) {
        this.isAlive = false;
        this.deathDay = worldAge;
    }

}
