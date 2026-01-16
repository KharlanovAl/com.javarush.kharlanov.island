package model;

import model.island.Location;
import java.util.Random;

public abstract class Animal extends LivingEntity {
    private double maxFoodNeed;
    private double currentFoodLevel;
    private int speed;
    private Random random;

    public Animal (String id, double weight, double maxFoodNeed, int speed) {
        super(id, weight);
        this.maxFoodNeed = maxFoodNeed;
        this.speed = speed;
        this.random = new Random();
        this.currentFoodLevel = maxFoodNeed * 0.7;
    }

    public abstract double getEatingProbability(Eatable food);

    public void eat(Eatable food) {
        if (!food.isAlive()) return;

        double probability = getEatingProbability(food);
        if (random.nextDouble() < probability) {
            currentFoodLevel += food.getWeight();
            food.setAlive(false);

            if (currentFoodLevel > maxFoodNeed * 1.5) {
                currentFoodLevel = maxFoodNeed * 1.5;
            }

            System.out.println(this + " съел " + food);
        } else {
            System.out.println(this + " не смог съесть " + food);
        }
    }

    public void move(Location newLocation) {
        if (!isAlive()) return;

        currentFoodLevel -= this.weight * 0.01;

        if (currentFoodLevel <= 0) {
            this.alive = false;
            System.out.println(this + "умер от голода)");
            return;
        }
        if (location != null) {
            location.removeAnimal(this);
        }
        newLocation.addAnimal(this);
        this.location = newLocation;

        System.out.println(this + " переместился в локацию [" +
                newLocation.getX() + "," + newLocation.getY() + "]");
    }
// Ежедневное уменьшение сытости---сделать так чтоб если не поел 3 дня умер
    public void dailyDecreaseFood() {
        currentFoodLevel -= maxFoodNeed * 0.1;
        if (currentFoodLevel <= 0) {
            this.alive = false;
            System.out.println(this + " умер от голода");
        }
    }

    public double getCurrentFoodLevel() {
        return currentFoodLevel;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + id + " (вес: " + weight + ", сытость: " + String.format("%.2f", currentFoodLevel) + "/" + maxFoodNeed + ")";
    }


}
