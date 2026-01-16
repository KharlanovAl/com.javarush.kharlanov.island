package model;

import model.island.Location;

public abstract class LivingEntity implements Eatable {
    protected String id;
    protected double weight;
    protected boolean alive;
    protected Location location;

    protected LivingEntity(String id, double weight) {
        this.id = id;
        this.weight = weight;
        this.alive = true;
    }

    @Override
    public double getWeight() {

        return weight;
    }
    @Override
    public boolean isAlive() {

        return alive;
    }
    @Override
    public void setAlive(boolean alive) {

        this.alive = alive;
    }

    public Location getLocation() {

        return location;
    }

    public void setLocation(Location location) {

        this.location = location;
    }
    public String getId() {

        return id;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + id + " (вес: " + weight + ")";
    }

}
