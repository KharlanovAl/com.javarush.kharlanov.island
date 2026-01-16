package model.plant;

import model.LivingEntity;
import model.island.Location;

public class Plant extends LivingEntity {
    private double growthRate;

    public Plant(String id, double weight, double growthRate) {
        super(id, weight);
        this.growthRate = growthRate;
    }

    public void grow() {
        this.weight += this.weight * growthRate;
    }

    @Override
    public String toString() {
        return "Растение " + id + "вес: " + String.format("%.2f", weight) + ")";
    }
}
