package model.herbivore;

import config.SimulationConfig;

public class Deer extends Herbivore {
    public Deer(String id) {
        super (id,
                SimulationConfig.AnimalConfig.WEIGHTS.get("DEER"),
                SimulationConfig.AnimalConfig.FOOD_NEED.get("DEER"),
                SimulationConfig.AnimalConfig.SPEED.get("DEER"));
    }
}
