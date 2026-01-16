package model.island;

import config.SimulationConfig;
import model.Animal;
import model.Eatable;
import model.herbivore.Deer;
import model.herbivore.Herbivore;
import model.herbivore.Rabbit;
import model.plant.Plant;
import model.predator.Fox;
import model.predator.Predator;
import model.predator.Wolf;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Location {
    private final int x;
    private final int y;
    private final List<Animal> animals;
    private final List<Plant> plants;
    private final Map<Class<?>, Integer> maxAnimalCounts;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        this.animals = new CopyOnWriteArrayList<>();
        this.plants = new CopyOnWriteArrayList<>();
        this.maxAnimalCounts = new ConcurrentHashMap<>();

        initializeMaxCounts();
        initializePlants();
    }

    private void initializeMaxCounts() {
        maxAnimalCounts.put(Wolf.class, SimulationConfig.AnimalConfig.MAX_COUNTS.get("WOLF"));
        maxAnimalCounts.put(Fox.class, SimulationConfig.AnimalConfig.MAX_COUNTS.get("FOX"));
        maxAnimalCounts.put(Rabbit.class, SimulationConfig.AnimalConfig.MAX_COUNTS.get("RABBIT"));
        maxAnimalCounts.put(Deer.class, SimulationConfig.AnimalConfig.MAX_COUNTS.get("DEER"));
    }

    private void initializePlants() {
        int initialPlants = ThreadLocalRandom.current().nextInt(5, 15);
        for (int i = 0; i < initialPlants; i++) {
            Plant plant = new Plant("P" + x + y + "-" + i,
                    ThreadLocalRandom.current().nextDouble(0.1, 1.0),
                    SimulationConfig.PLANT_GROWTH_RATE);
            plant.setLocation(this);
            plants.add(plant);
        }
    }

    public void addAnimal(Animal animal) {
        if (canAddAnimal(animal.getClass())) {
            animals.add(animal);
            animal.setLocation(this);
        }
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public boolean canAddAnimal(Class<? extends Animal> animalType) {
        long currentCount = animals.stream()
                .filter(a -> a.getClass() == animalType)
                .count();
        Integer maxCount = maxAnimalCounts.get(animalType);
        // Возвращаем true только если тип известен и есть место
        return maxCount != null && currentCount < maxCount;
    }

    public void growPlants() {
        // Расти существующим растениям
        for (Plant plant : plants) {
            plant.grow();
        }

        // Добавлять новые растения
        if (plants.size() < SimulationConfig.MAX_PLANT_PER_LOCATION) {
            int newPlants = ThreadLocalRandom.current().nextInt(1, 4);
            for (int i = 0; i < newPlants; i++) {
                Plant plant = new Plant("P" + x + y + "-new-" + plants.size(),
                        ThreadLocalRandom.current().nextDouble(0.1, 0.5),
                        SimulationConfig.PLANT_GROWTH_RATE);
                plant.setLocation(this);
                plants.add(plant);
            }
        }
    }

    public List<Eatable> findFoodFor(Animal animal) {
        List<Eatable> foodList = new ArrayList<>();

        if (animal instanceof Herbivore) {
            // Фильтруем живые растения
            plants.stream()
                    .filter(Plant::isAlive)
                    .forEach(foodList::add);
        } else if (animal instanceof Predator) {
            // Фильтруем живых травоядных
            animals.stream()
                    .filter(a -> a instanceof Herbivore && a.isAlive())
                    .forEach(foodList::add);
        }
        return foodList;
    }

    public void cleanupDeadEntities() {
        animals.removeIf(animal -> !animal.isAlive());
        plants.removeIf(plant -> !plant.isAlive());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public Map<Class<?>, Integer> getAnimalCounts() { // Исправить название
        Map<Class<?>, Integer> counts = new HashMap<>();
        for (Animal animal : animals) {
            counts.merge(animal.getClass(), 1, Integer::sum);
        }
        return counts;
    }

    public Map<Class<?>, Integer> getMaxAnimalCounts() { // Убрать дублирование
        return new HashMap<>(maxAnimalCounts);
    }

    @Override
    public String toString() {
        return "Location[" + x + "," + y + "]";
    }

    // Дополнительные полезные методы:
    public int getTotalAnimalCount() {
        return animals.size();
    }

    public boolean hasSpaceFor(Class<? extends Animal> animalType) {
        return canAddAnimal(animalType);
    }
}