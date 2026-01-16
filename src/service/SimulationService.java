package service;

import model.Animal;
import model.Eatable;
import model.island.Island;
import model.island.Location;

import java.util.List;
import java.util.concurrent.*;

public class SimulationService {
    private final Island island;
    private final StatisticService statisticService;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService executor;
    private final ExecutorService animalExecutor;

    private volatile boolean isRunning = false;

    public SimulationService(Island island) {
        this.island = island;
        this.animalExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        this.statisticService = new StatisticService(island);
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.executor = Executors.newFixedThreadPool
                (Runtime.getRuntime().availableProcessors() * 2);
    }

    public void startSimulation(int days) {
        if (isRunning) {
            System.out.println("Симуляция уже запущена");
            return;
        }

        isRunning = true;
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ЗАПУСК СИМУЛЯЦИИ НА " + days + " ДНЕЙ");
        System.out.println("=".repeat(60));

        // Планируем задачу роста растений
        ScheduledFuture<?> plantGrowthTask = scheduler.scheduleAtFixedRate(
                this::growPlantsAllLocations,
                1, 1, TimeUnit.SECONDS
        );

        // Задача для статистики
        ScheduledFuture<?> statsTask = scheduler.scheduleAtFixedRate(
                () -> statisticService.printStatistics(),
                2, 5, TimeUnit.SECONDS
        );

        // Основной цикл симуляции
        for (int day = 1; day <= days && isRunning; day++) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("ДЕНЬ " + day);
            System.out.println("=".repeat(40));

            try {
                runDayCycle();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        plantGrowthTask.cancel(false);
        statsTask.cancel(false);
        stopSimulation();
    }

    private void runDayCycle() {
        List<Future<?>> futures = new CopyOnWriteArrayList<>();

        // Обрабатываем всех животных на острове
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location location = island.getLocation(x, y);

                for (Animal animal : location.getAnimals()) {
                    if (animal.isAlive()) {
                        futures.add(animalExecutor.submit(() -> {
                            processAnimal(animal);
                        }));
                    }
                }
                // Очистка мертвых существ в локации
                location.cleanupDeadEntities();
            }
        }
        // Ждем завершения всех задач
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                System.err.println("Ошибка при обработке животного: " + e.getMessage());
            }
        }
    }

    private void processAnimal(Animal animal) {
        if (!animal.isAlive()) return;

        Location currentLocation = (Location) animal.getLocation();
        if (currentLocation == null) return;

        // 1. Уменьшение сытости
        animal.dailyDecreaseFood();

        // 2. Поиск и поедание пищи
        List<Eatable> foodList = currentLocation.findFoodFor(animal);
        if (!foodList.isEmpty()) {
            Eatable food = foodList.get(ThreadLocalRandom.current().nextInt(foodList.size()));
            animal.eat(food);
        }

        // 3. Перемещение (с вероятностью 30%)
        if (ThreadLocalRandom.current().nextDouble() < 0.3) {
            List<Location> adjacentLocations = island.getAdjacentLocations(currentLocation);
            if (!adjacentLocations.isEmpty()) {
                Location newLocation = adjacentLocations.get(
                        ThreadLocalRandom.current().nextInt(adjacentLocations.size())
                );

                if (newLocation.canAddAnimal(animal.getClass())) {
                    animal.move(newLocation);
                }
            }
        }
    }

    private void growPlantsAllLocations() {
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                island.getLocation(x, y).growPlants();
            }
        }
        System.out.println("[Растения выросли на всех локациях]");
    }

    public void stopSimulation() {
        isRunning = false;

        scheduler.shutdown();
        animalExecutor.shutdown();

        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
            if (!animalExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                animalExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            animalExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("СИМУЛЯЦИЯ ЗАВЕРШЕНА");
        System.out.println("=".repeat(60));

        // Финальная статистика
        StatisticService.printStatistics();
    }

    public boolean isRunning() {
        return isRunning;
    }
}

