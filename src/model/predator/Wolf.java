package model.predator;

import config.SimulationConfig;

public class Wolf extends Predator {
    public Wolf(String id) {
        super (id,
                SimulationConfig.AnimalConfig.WEIGHTS.get("WOLF"),
                SimulationConfig.AnimalConfig.FOOD_NEED.get("WOLF"),
                SimulationConfig.AnimalConfig.SPEED.get("WOLF"));
    }
}
