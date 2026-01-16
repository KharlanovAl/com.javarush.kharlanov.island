package model.island;

import config.SimulationConfig;
import model.Animal;
import model.herbivore.Deer;
import model.herbivore.Rabbit;
import model.predator.Fox;
import model.predator.Wolf;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalFabric {
    private static final Map<String, Integer> animalCounters = new ConcurrentHashMap<>();

    static {
        // Инициализируем счетчики
        animalCounters.put("WOLF", 0);
        animalCounters.put("FOX", 0);
        animalCounters.put("RABBIT", 0);
        animalCounters.put("DEER", 0);
    }

    public static Animal createRandomAnimal() {
        String[] animalTypes = {"WOLF", "FOX", "RABBIT", "DEER"};
        String type = animalTypes[ThreadLocalRandom.current().nextInt(animalTypes.length)];
        return createAnimal(type);
    }

    public static Animal createAnimal(String type) {
        String upperType = type.toUpperCase();
        int count = animalCounters.merge(upperType, 1, Integer::sum);
        switch (type.toUpperCase()) {
            case "WOLF":
                return new Wolf("W" + (++count));
            case "FOX":
                return new Fox("F" + (++count));
            case "RABBIT":
                return new Rabbit("R" + (++count));
            case "DEER":
                return new Deer("D" + (++count));
            default:
                throw new IllegalArgumentException("Unknown animal type: " + type);
        }
    }

    public static void initializeLocationWithAnimals (Location location) {
        if (location == null) return;
        int herbivoresToAdd = Math.max(SimulationConfig.AnimalConfig.MIN_HERBIVORES / 2, 5);
        for (int i = 0; i < herbivoresToAdd; i++) {
            Animal herbivore;
            if (ThreadLocalRandom.current().nextBoolean()) {
                herbivore = createAnimal("RABBIT");
            } else {
                herbivore = createAnimal("DEER");
            }
        }
        int predatorsToAdd = Math.max(SimulationConfig.AnimalConfig.MIN_PREDATORS / 2, 3);
        for (int i = 0; i < predatorsToAdd; i++) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                location.addAnimal(createAnimal("WOLF"));
            } else  {
                location.addAnimal(createAnimal("FOX"));
            }
        }
    }
}
