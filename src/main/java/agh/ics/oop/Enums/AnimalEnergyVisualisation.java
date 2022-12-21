package agh.ics.oop.Enums;

public enum AnimalEnergyVisualisation {
    STRONGEST("src/main/resources/Images/80-100.png"),
    STRONG("src/main/resources/Images/60-80.png"),
    HEALTHY("src/main/resources/Images/40-60.png"),
    WEAK("src/main/resources/Images/20-40.png"),
    WEAKEST("src/main/resources/Images/0-20.png");

    private final String imagePath;

    AnimalEnergyVisualisation(String path) {
        this.imagePath = path;
    }

    private boolean isBetween(double leftSide, double rightSide, int animalEnergy) {
        return leftSide <= animalEnergy && animalEnergy <= rightSide;
    }

    public String getImagePath() {
        return this.imagePath;
    }
    //maxEnergy is energy of the strongest animal => there is always one dark green animal on map
    //other animals get color which depends on health percentage
    public AnimalEnergyVisualisation getStatus(int maxEnergy, int animalEnergy) {

        if (isBetween(maxEnergy * 0.80, maxEnergy + 1, animalEnergy)) {
            return AnimalEnergyVisualisation.STRONGEST;
        }
        if (isBetween(maxEnergy * 0.60, maxEnergy * 0.80, animalEnergy)) {
            return AnimalEnergyVisualisation.STRONG;
        }
        if (isBetween(maxEnergy * 0.40, maxEnergy * 0.60, animalEnergy)) {
            return AnimalEnergyVisualisation.HEALTHY;
        }
        if (isBetween(maxEnergy * 0.40, maxEnergy * 0.20, animalEnergy)) {
            return AnimalEnergyVisualisation.WEAK;
        }

        return AnimalEnergyVisualisation.WEAKEST;
    }

}
