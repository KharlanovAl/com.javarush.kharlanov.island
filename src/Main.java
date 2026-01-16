import config.SimulationConfig;
import model.island.Island;
import model.island.Location;
import service.SimulationService;


public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("МОДЕЛЬ ЭКОСИСТЕМЫ ОСТРОВА");
        System.out.println("=".repeat(60));

        // 1) ИНИЦИАЛИЗИРОВАТЬ ОСТРОВ
        System.out.println("\n1. Создание острова...");
        Island island = new Island();
        System.out.println("Остров создан: " + island);
        System.out.println("Размер: " + SimulationConfig.ISLAND_WIDTH +
                "x" + SimulationConfig.ISLAND_HEIGHT);

        // 2) ИНИЦИАЛИЗИРОВАТЬ НА ОСТРОВЕ 1 ЛОКАЦИЮ
        System.out.println("\n2. Инициализация первой локации...");
        island.initializeFirstLocation(0, 0); // Инициализируем локацию [0,0]
        System.out.println("Локация [0,0] инициализирована");

        // 3) ИНИЦИАЛИЗИРОВАТЬ НА ОДНОЙ ЛОКАЦИИ ОБИТАТЕЛЕЙ
        System.out.println("\n3. Добавление обитателей в первую локацию...");
        Location firstLocation = island.getLocation(0, 0);
        System.out.println("Локация [0,0] содержит:");
        System.out.println("  Животных: " + firstLocation.getAnimals().size());
        System.out.println("  Растений: " + firstLocation.getPlants().size());

        // Вывод информации о животных
        System.out.println("\nТипы животных в локации:");
        firstLocation.getAnimalCounts().forEach((animalClass, count) -> {
            System.out.println("  " + animalClass.getSimpleName() + ": " + count);
        });

        // 4) СОЗДАТЬ ШЕДУЛЕД ПУЛ С ВЫВОДОМ СТАТИСТИКИ И РОСТОМ РАСТЕНИЙ
        System.out.println("\n4. Запуск симуляции...");
        SimulationService simulationService = new SimulationService(island);

        // Запуск симуляции на 10 дней
        simulationService.startSimulation(10);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("ПРОГРАММА ЗАВЕРШЕНА");
        System.out.println("=".repeat(60));
    }
}
