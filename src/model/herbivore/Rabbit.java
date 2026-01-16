package model.herbivore;

import config.SimulationConfig;

public class Rabbit extends Herbivore {
    public Rabbit(String id) {
        super(id,
                SimulationConfig.AnimalConfig.WEIGHTS.get("RABBIT"),
                SimulationConfig.AnimalConfig.FOOD_NEED.get("RABBIT"),
                SimulationConfig.AnimalConfig.SPEED.get("RABBIT"));
    }
}
