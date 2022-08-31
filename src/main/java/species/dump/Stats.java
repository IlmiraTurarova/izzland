package species.dump;

import animalHierarchy.Alive;
import species.carnivore.*;
import species.hervivore.*;
import species.plants.Plant;

public class Stats {
    public static int eatenAnimals =0;
    public static int eatenPlants =0;

    public void printStats() {

        int wolfCount = 0;
        int sheepCount = 0;
        int plantCount = 0;
        int bearCount = 0;
        int foxCount = 0;
        int boaCount = 0;
        int eagleCount = 0;
        int boarCount = 0;
        int buffaloCount = 0;
        int deerCount = 0;
        int mouseCount = 0;
        int duckCount = 0;
        int goatCount = 0;
        int caterpillarCount = 0;
        int rabbitCount = 0;
        int horseCount = 0;


        for (int i = 0; i < Dump.animalIsland.length; i++) {
            for (int j = 0; j < Dump.animalIsland[0].length; j++) {
                synchronized (Dump.animalIsland[i][j]) {
                    if (Dump.animalIsland[i][j].animals.size() == 0) {
                        continue;
                    }
                    for (Alive alive : Dump.animalIsland[i][j].animals) {

                        if (alive instanceof Wolf) {
                            wolfCount++;
                        } else if (alive instanceof Sheep) {
                            sheepCount++;
                        } else if (alive instanceof Bear) {
                            bearCount++;
                        } else if (alive instanceof Fox) {
                            foxCount++;
                        } else if (alive instanceof Boa) {
                            boaCount++;
                        } else if (alive instanceof Eagle) {
                            eagleCount++;
                        } else if (alive instanceof Boar) {
                            boarCount++;
                        } else if (alive instanceof Buffalo) {
                            buffaloCount++;
                        } else if (alive instanceof Deer) {
                            deerCount++;
                        } else if (alive instanceof Mouse) {
                            mouseCount++;
                        } else if (alive instanceof Duck) {
                            duckCount++;
                        } else if (alive instanceof Goat) {
                            goatCount++;
                        } else if (alive instanceof Caterpillar) {
                            caterpillarCount++;
                        } else if (alive instanceof Rabbit) {
                            rabbitCount++;
                        } else if (alive instanceof Horse) {
                            horseCount++;
                        } else if (alive instanceof Plant) {
                            plantCount++;
                        }
                    }
                }
            }
        }

        System.out.println("\uD83D\uDC3A: " + wolfCount
                + " \uD83D\uDC11: " + sheepCount
                + " \uD83D\uDC3B: " + bearCount
                + " \uD83E\uDD8A: " + foxCount
                + "  \uD83D\uDC0D: " + boaCount
                + "  \uD83E\uDD85: " + eagleCount
                + "  \uD83D\uDC17: " + boarCount
                + "  \uD83D\uDC03: " + buffaloCount
                + "  \uD83D\uDC1B: " + caterpillarCount
                + "  \uD83E\uDD8C: " + deerCount
                + "  \uD83D\uDC01: " + mouseCount
                + "  \uD83E\uDD86: " + duckCount
                + "  \uD83D\uDC10: " + goatCount
                + "  \uD83D\uDC07: " + rabbitCount
                + "  \uD83D\uDC0E: " + horseCount
                + " \uD83C\uDF3F: " + plantCount
                + " \uD83D\uDC80: " + eatenAnimals
                + " \uD83E\uDD40: " + eatenPlants
        );

    }
}


