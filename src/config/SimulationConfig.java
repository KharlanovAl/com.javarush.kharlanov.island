package config;

import java.util.Map;

public class SimulationConfig {
    // размеры острова
    public static final int ISLAND_WIDTH = 10;
    public static final int ISLAND_HEIGHT = 10;
    //характеристики для растений
    public static final double PLANT_GROWTH_RATE = 0.1;
    public static final int MAX_PLANT_PER_LOCATION = 100;
    //минимум


    //Вероятность поедания при встрече
    public static final Map<String, Double> EATING_PROBABILITIES = Map.of(
            "WOLF_RABBIT", 0.15,
            "WOLF_DEER", 0.1,
            "FOX_RABBIT", 0.1,
            "RABBIT_PLANT", 1.0,
            "DEER_PLANT", 1.0
    );
    //характеристики животных
    public static class AnimalConfig{
        public static final Map<String, Double> WEIGHTS = Map.of(
                "WOLF", 50.0,
                "FOX", 8.0,
                "RABBIT", 2.0,
                "DEER", 300.0
        );

        public static final Map<String, Integer> MAX_COUNTS = Map.of(
                "WOLF", 30,
                "FOX", 30,
                "RABBIT", 150,
                "DEER", 20,
                "PLANT", 200
        );

        public static final Map<String, Integer> SPEED = Map.of(
                "WOLF", 3,
                "FOX", 4,
                "RABBIT", 5,
                "DEER", 4
        );

        public static final Map<String, Double> FOOD_NEED = Map.of(
                "WOLF", 8.0,
                "FOX", 2.0,
                "RABBIT", 0.45,
                "DEER", 50.0
        );
        public static final int MIN_HERBIVORES = 20;
        public static final int MIN_PREDATORS = 10;
    }
}
