package species.dump;

import animalHierarchy.Alive;
import species.carnivore.Wolf;
import species.hervivore.Sheep;
import species.plants.Plant;

public class Stats {
    public void printStats() {

        int wolfCount = 0;
        int sheepCount = 0;
        int plantCount = 0;

            for (int i = 0; i < Dump.animalIsland.length; i++) {
                for (int j = 0; j < Dump.animalIsland[0].length; j++) {
                    for (Alive alive : Dump.animalIsland[i][j].animals) {

                        if (alive instanceof Wolf) {
                            wolfCount++;
                        } else if (alive instanceof Sheep) {
                            sheepCount++;
                        } else if (alive instanceof Plant) {
                            plantCount++;
                        }
                    }
                }
            }

            System.out.println("Волков: " + wolfCount + " Овец: " + sheepCount + " Растений: " + plantCount);
        }
    }

