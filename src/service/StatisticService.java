package service;
import model.Animal;
import model.island.Island;
import model.island.Location;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticService {
    private static Island island;

    public StatisticService(Island island) {
        this.island = island;
    }

    public static void printStatistics() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("СТАТИСТИКА ОСТРОВА");
        System.out.println("=".repeat(60));

        Map<Class<?>,AtomicInteger> totalCounts = new ConcurrentHashMap<>();
        int totalAnimals = 0;
        int totalPlants = 0;

        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location location = island.getLocation(x, y);

                for (Animal animal : location.getAnimals()) {
                    totalAnimals++;
                    totalCounts.computeIfAbsent(animal.getClass(),
                            k -> new AtomicInteger(0)).incrementAndGet();
                }
                totalPlants += location.getPlants().size();
            }
        }
        System.out.println ("\nОбщая статистика:");
        System.out.printf ("Всего животных: %d%n", totalAnimals);
        System.out.printf ("Всего растений: %d%n", totalPlants);

        System.out.println("\nПо типам животных:");
        totalCounts.forEach((animalClass, count) -> {
            System.out.printf("  %s: %d%n",
                    animalClass.getSimpleName(), count.get());
        });
    }

//    public Map<Class<?>, Integer> getTotalAnimalCounts() {
//        Map<Class<?>, AtomicInteger> counts = new ConcurrentHashMap<>();
//
//        for (int x = 0; x < island.getWidth(); x++) {
//            for (int y = 0; y < island.getHeight(); y++) {
//                Location location = island.getLocation(x, y);
//                location.getAnimalCounts().forEach((animalClass, count) -> {
//                    counts.computeIfAbsent(
//                            animalClass,
//                            k -> new AtomicInteger(0)
//                    ).addAndGet(count);
//                });
//            }
//        }
//
//        // Конвертируем AtomicInteger в Integer
//        Map<Class<?>, Integer> result = new ConcurrentHashMap<>();
//        counts.forEach((animalClass, atomicCount) ->
//                result.put(animalClass, atomicCount.get()));
//
//        return result;
//    }
}
