package model.predator;

import config.SimulationConfig;
import model.Animal;
import model.Eatable;
import model.herbivore.Herbivore;

public class Predator extends Animal {
    public Predator(String id, double weight, double maxFoodLevel, int speed) {
        super(id, weight, maxFoodLevel, speed);
    }
    @Override
    public double getEatingProbability(Eatable food) {
        if (food instanceof Herbivore) {
            String predatorType = this.getClass().getSimpleName().toUpperCase();
            String preyType = food.getClass().getSimpleName().toUpperCase();
            String key = predatorType + preyType;

            return SimulationConfig.EATING_PROBABILITIES.getOrDefault(key,0.5);
        }
        return 0.0;
    }
}
