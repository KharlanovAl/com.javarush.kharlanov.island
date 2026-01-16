package model.predator;

import config.SimulationConfig;

public class Fox extends Predator {
    public Fox(String id) {
        super (id,
                SimulationConfig.AnimalConfig.WEIGHTS.get("FOX"),
                SimulationConfig.AnimalConfig.FOOD_NEED.get("FOX"),
                SimulationConfig.AnimalConfig.SPEED.get("FOX"));
    }
}
