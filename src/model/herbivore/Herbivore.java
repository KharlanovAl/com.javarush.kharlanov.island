package model.herbivore;

import config.SimulationConfig;
import model.Animal;
import model.Eatable;
import model.plant.Plant;

public class Herbivore extends Animal {
    public Herbivore(String id, double weight, double maxFoodLevel, int speed) {
        super(id, weight, maxFoodLevel, speed);
    }

    @Override
    public double getEatingProbability(Eatable food) {
        if (food instanceof Plant) {
            return SimulationConfig.EATING_PROBABILITIES.getOrDefault
                    (this.getClass().getSimpleName().toUpperCase() + "_PLANT", 0.9);
        }
        return 0.0;
    }
}
