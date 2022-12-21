package agh.ics.oop;

import agh.ics.oop.MapType.IMapType;
import agh.ics.oop.MutationType.IMutationType;
import agh.ics.oop.NextGenType.INextGenType;
import agh.ics.oop.PlantGrowType.IPlantGrowType;
import agh.ics.oop.PlantGrowType.ToxicBodies;

import java.util.*;

public class WorldMap {

    private final IMapType mapType;
    private final INextGenType nextGenType;
    private final IMutationType mutationType;
    private final IPlantGrowType plantGrowType;
    protected int width;
    protected int height;
    protected int breedCost;
    private int animalStartEnergy;
    public int genomLength; //prodlem przy typach
    protected int minimalEnergyToBreed;
    protected int energyFromPlant;
    private int quantityGrowEveryDay;
    private int quantityAnimalSpawnStart;
    private int quantityPlantSpawnStart;
    protected final Vector2d lowerLeft = new Vector2d(0, 0);
    protected Vector2d upperRight; //przy rysowaniu mapy problem w gui liczy rozmiar mapu
    protected Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected List<Animal> animalsList = new ArrayList<>();
    protected List<Animal> deadAnimalList = new ArrayList<>();
    public TreeMap<Vector2d, Integer> sortedDeadAnimalCountDesc = new TreeMap<>(Collections.reverseOrder()); //hell map uses
    protected Map<Vector2d, Integer> deadAnimalCount = new LinkedHashMap<>(); // count dead animal on this position
    protected List<Vector2d> preferredPositions;
    protected List<Plant> plantsList = new ArrayList<>();
    private Map<Vector2d, Plant> plants = new HashMap<>();
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);

    protected List<Vector2d> freePositions = new ArrayList<>(); //stores all free positions

    public WorldMapStatistics worldMapStatistics = new WorldMapStatistics(this);

    protected int worldAge = 0;


    public WorldMap(IMapType mapType, INextGenType nextGenType, IMutationType mutationType, IPlantGrowType plantGrowType,
                    int width, int height, int breedCost, int genomLength, int minimalEnergyToBreed, int quantityGrowEveryDay, int energyFromPlant,
                    int quantityAnimalSpawnStart, int quantityPlantSpawnStart, int animalStartEnergy) {
        this.mapType = mapType;
        this.nextGenType = nextGenType;
        this.mutationType = mutationType;
        this.plantGrowType = plantGrowType;
        this.width = width;
        this.height = height;
        this.breedCost = breedCost;
        this.genomLength = genomLength;
        this.minimalEnergyToBreed = minimalEnergyToBreed;
        this.quantityGrowEveryDay = quantityGrowEveryDay;
        this.energyFromPlant = energyFromPlant;
        this.quantityAnimalSpawnStart = quantityAnimalSpawnStart;
        this.quantityPlantSpawnStart = quantityPlantSpawnStart;
        this.animalStartEnergy = animalStartEnergy;
        this.upperRight = new Vector2d(this.width - 1, this.height - 1); // moze minus moze plus 1, zastanowic sie jak mapa to czyta
        this.preferredPositions = this.pickPreferredPositions();
        WorldInit();
        findFreePositions();

    }

    public String toString() {
        return mapVisualizer.draw(this.lowerLeft, this.upperRight);
    }

    private void WorldInit() {

        int iterator = 0;
        while (iterator < quantityAnimalSpawnStart) {
            Animal animal = new Animal(this, generatePosition(), animalStartEnergy, generateGenom());
            if (placeAnimal(animal)) {
                iterator += 1;
            }
        }
        int iterator1 = 0;
        while (iterator1 < quantityPlantSpawnStart) {
            Plant plant = new Plant(generatePosition());
            if (placePlant(plant)) {
                iterator1 += 1;
            }
        }
    }

    private void findFreePositions() {
        for (int x = 0; x <= this.upperRight.x; x++) {
            for (int y = 0; y <= this.upperRight.y; y++) {
                Vector2d nowVec = new Vector2d(x, y);
                addToFreePositions(nowVec);
            }
        }
    }

    private void addToFreePositions(Vector2d nowVec) { //zwolnienie tego iejsca
        if (objectAt(nowVec) == null) {
            this.freePositions.add(nowVec);
        }
    }

    private void removeFromFreePositions(Vector2d nowVec) { //zajecie tego miejsca
        if (objectAt(nowVec) != null) {
            this.freePositions.remove(nowVec);
        }
    }

    private List<Integer> generateGenom() {
        List<Integer> newGenom = new ArrayList<>();
        for (int i = 0; i < genomLength; i++) {
            newGenom.add((int) (Math.random() * 7 + 1) - 1);
        }
        return newGenom;
    }

    private Vector2d generatePosition() {
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        return new Vector2d(x, y);
    }


    protected Vector2d mapSpecification(Animal animal, Vector2d newPosition) {// to bedzie zwracac vector gdzie jest zwierze po ruchu
        if (newPosition.precedes(upperRight) && newPosition.follows(lowerLeft)) {
            animal.lowerEnergy(1);
            return newPosition;
        }


        return this.mapType.mapSpecyfication(animal, getLowerLeft(),getUpperRight(),newPosition,this.breedCost);
    }

    protected int nextGen(int whichGenNow) {
        return this.nextGenType.nextGen(this, whichGenNow);
    }

    protected List<Integer> mutateGenom(List<Integer> genom) {
        int numberOfmutations = (int) (Math.random() * genom.size());
        return this.mutationType.mutateGenom(genom, numberOfmutations);
    }

    private List<Vector2d> pickPreferredPositions() {
        return this.plantGrowType.pickPreferredPositions(this);
    }


    private boolean placeAnimal(Animal animal) {
        if (isInBorder(animal.getPosition())) {
            animalsList.add(animal);
            addAnimalHashMap(animal, animal.getPosition());
            return true;
        }
        return false;
    }

    protected boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) { // wybrac najlepszego z listy przy pomocy comperatora
        if (animals.get(position) != null) {
            List<Animal> listFromHashMap = animals.get(position);
            Collections.sort(listFromHashMap, new AnimalComparator());

            if (listFromHashMap.get(0).energy >= 0){ // prevent minus health in hell map
                return listFromHashMap.get(0);
            }

        }

        if (plants.get(position) != null) {
            return plants.get(position);
        }

        return null;
    }

    public boolean isInBorder(Vector2d position) {
        return position.precedes(this.upperRight) && position.follows(this.lowerLeft);
    }

    //use in simulation
    protected void killAnimals() {

        List<Animal> toErase = new ArrayList<>();

        for (int i = 0; i < this.animalsList.size(); i++) {
            Animal nowAnimal = this.animalsList.get(i);
            if (nowAnimal.energy < 1) {
                deadAnimalList.add(nowAnimal);
                nowAnimal.kill(this.worldAge);
                //nowAnimal.deathDay = worldAge;
                toErase.add(nowAnimal);
            }
        }

        for (Animal deadAnimal : toErase) {
            animalsList.remove(deadAnimal);
            removeAnimalHasMap(deadAnimal, deadAnimal.getPosition());
            addToFreePositions(deadAnimal.getPosition());
            // adding to hashmap
            int count = ((deadAnimalCount.get(deadAnimal.getPosition()) == null) ? 1 : 1 + deadAnimalCount.get(deadAnimal.getPosition()));
            deadAnimalCount.put(deadAnimal.getPosition(), count);

        }

        //sorts hashmap by increasing valuse
        List<Map.Entry<Vector2d, Integer>> list = new ArrayList<>(deadAnimalCount.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Vector2d, Integer>>() {
            public int compare(Map.Entry<Vector2d, Integer> o1, Map.Entry<Vector2d, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        Map<Vector2d, Integer> temporaryDeadAnimals = new LinkedHashMap<>();
        for (Map.Entry<Vector2d, Integer> e : list) {
            temporaryDeadAnimals.put(e.getKey(), e.getValue());
        }
        this.deadAnimalCount = temporaryDeadAnimals;


    }

    protected void moveAnimals() {

        for (int i = 0; i < this.animalsList.size(); i++) {

            Animal nowAnimal = this.animalsList.get(i);
            Vector2d oldPosition = nowAnimal.getPosition();
            nowAnimal.move();
            Vector2d newPosition = nowAnimal.getPosition();

            //notyfing hashmap of animals
            removeAnimalHasMap(nowAnimal, oldPosition);
            addAnimalHashMap(nowAnimal, newPosition);
            addToFreePositions(oldPosition);
            removeFromFreePositions(newPosition);
        }
    }

    private void addAnimalHashMap(Animal animal, Vector2d newPosition) {

        if (animals.get(newPosition) == null) {
            List<Animal> listForHashMap = new ArrayList<>();
            listForHashMap.add(animal);
            animals.put(newPosition, listForHashMap);
        } else {
            List<Animal> listFromHashMap = animals.get(newPosition);
            listFromHashMap.add(animal);
            animals.put(newPosition, listFromHashMap);
        }
    }

    private void removeAnimalHasMap(Animal animal, Vector2d oldPosition) {
        List<Animal> listFromHashMap = animals.get(oldPosition);
        listFromHashMap.remove(animal);
        if (listFromHashMap.size() == 0) {
            animals.remove(oldPosition);
        } else {
            animals.put(oldPosition, listFromHashMap);
        }

    }


    protected void eatPlants() {
        List<Plant> toEarse = new ArrayList<>();


        for (Plant plant : plantsList) {
            if (animals.get(plant.getPosition()) != null) {
                List<Animal> listFromHashMap = animals.get(plant.getPosition());
                Collections.sort(listFromHashMap, new AnimalComparator());
                listFromHashMap.get(0).feedAnimal(this.energyFromPlant);
                toEarse.add(plant);
            }


        }
        for (Plant eatenPlant : toEarse) {
            this.removePlant(eatenPlant);
        }
    }

    private void removePlant(Plant eatenPlant) {
        this.plants.remove(eatenPlant.getPosition());
        this.plantsList.remove(eatenPlant);
        addToFreePositions(eatenPlant.getPosition());
    }

    protected void growPlants() {
        if (plantGrowType instanceof ToxicBodies) {
            this.preferredPositions = pickPreferredPositions();
        }
        int quantityPreferred = (int) (Math.ceil((this.quantityGrowEveryDay * 80.0) / 100.0));
        int quantityNotPreferred = quantityGrowEveryDay - quantityPreferred;

        int alreadyGrown = 0;
        List<Vector2d> tempPreferred = new ArrayList<>();
        tempPreferred.addAll(this.preferredPositions);
        for (int i = 0; i < quantityPreferred; i++) {
            if (this.freePositions.size() == 0 || tempPreferred.size() == 0) {
                break;
            }
            int randomIndex = (int) (Math.random() * tempPreferred.size());
            Vector2d nowVector = tempPreferred.get(randomIndex);
            if (placePlant(new Plant(nowVector))) {
                alreadyGrown += 1;
            }
            tempPreferred.remove(nowVector);

        }
        List<Vector2d> tempNotPreferred = new ArrayList<>();
        tempNotPreferred.addAll(this.freePositions);

        for (int i = alreadyGrown; i < quantityGrowEveryDay; i++) {
            if (this.freePositions.size() == 0 || tempNotPreferred.size() == 0 || alreadyGrown == quantityGrowEveryDay) {
                break;
            }
            int randomIndex = (int) (Math.random() * tempNotPreferred.size());
            Vector2d nowVector = tempNotPreferred.get(randomIndex);
            if (placePlant(new Plant(nowVector))) {
                alreadyGrown += 1;
            }
            tempNotPreferred.remove(nowVector);

        }

    }


    private boolean placePlant(Plant plant) {
        if (plants.get(plant.getPosition()) == null) {
            this.plants.put(plant.getPosition(), plant);
            this.plantsList.add(plant);
            removeFromFreePositions(plant.getPosition());

            return true;
        }
        return false;

    }

    protected void breedAnimals() {

        for (Vector2d key : animals.keySet()) {
            if (animals.get(key) != null) {
                List<Animal> listFromHashMap = animals.get(key);
                Collections.sort(listFromHashMap, new AnimalComparator());

                if (listFromHashMap.size() > 1) {

                    Animal strongerAnimal = listFromHashMap.get(0);
                    Animal weakerAnimal = listFromHashMap.get(1);
                    if (weakerAnimal.energy > this.minimalEnergyToBreed) {
                        Animal newBorn = strongerAnimal.breedAnimal(weakerAnimal, breedCost);
                        this.placeAnimal(newBorn);
                    }

                }


            }
        }
    }


    public Vector2d getUpperRight(){
        return new Vector2d(upperRight.x,upperRight.y);
    }

    public Vector2d getLowerLeft(){
        return new Vector2d(lowerLeft.x,lowerLeft.y);
    }
}
