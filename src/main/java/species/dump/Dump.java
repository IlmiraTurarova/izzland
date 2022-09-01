package species.dump;


import animalHierarchy.*;
import island.Cell;
import species.plants.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Dump {
    public static List<AnimalType> species = new ArrayList<>();
    public static
    ThreadLocalRandom randomIndex = ThreadLocalRandom.current();
    public static AnimalFactory factory = new AnimalFactory();
    public volatile static Cell[][] animalIsland = new Cell[10][10];
    public static int day=1;

    public static void filInSpecies() {
        species.add(AnimalType.WOLF);
        species.add(AnimalType.BOA);
        species.add(AnimalType.FOX);
        species.add(AnimalType.BEAR);
        species.add(AnimalType.EAGLE);
        species.add(AnimalType.HORSE);
        species.add(AnimalType.DEER);
        species.add(AnimalType.RABBIT);
        species.add(AnimalType.MOUSE);
        species.add(AnimalType.GOAT);
        species.add(AnimalType.SHEEP);
        species.add(AnimalType.BOAR);
        species.add(AnimalType.BUFFALO);
        species.add(AnimalType.DUCK);
        species.add(AnimalType.CATERPILLAR);
    }

    public static void filInIsland() {
        for (int i = 0; i < animalIsland.length; i++) {
            for (int j = 0; j < animalIsland[0].length; j++) {
                List<Alive> animalsIn = new ArrayList<>();
                for (int k = 0; k < 100; k++) {
                    AnimalType animalType = species.get(randomIndex.nextInt(0, species.size()));
                    animalsIn.add(factory.createAnimal(animalType));
                }
                for (int k = 0; k < 6; k++) {
                    animalsIn.add(new Plant());
                }
                animalIsland[i][j] = new Cell(i, j, animalsIn);
            }
        }

    }

    public static void fillInParams() {
        for (int i = 0; i < animalIsland.length; i++) {
            for (int j = 0; j < animalIsland[0].length; j++) {

                for (Alive alive : animalIsland[i][j].getAnimals()) {
                    if (alive instanceof Herbivore) {
                        String className = alive.getClass().getName().toUpperCase().split("\\.")[2];
                        for (AnimalType type : species) {
                            if (className.equals(type.name())) {
                                ((Herbivore) alive).setWeight(type.getWeight());
                                ((Herbivore) alive).setX(i);
                                ((Herbivore) alive).setY(j);
                            }
                        }
                    } else if (alive instanceof Carnivore) {
                        String className = alive.getClass().getName().toUpperCase().split("\\.")[2];
                        for (AnimalType type : species) {
                            if (className.equals(type.name())) {
                                ((Carnivore) alive).setWeight(type.getWeight());
                                ((Carnivore) alive).setX(i);
                                ((Carnivore) alive).setY(j);
                            }
                        }
                    } else if (alive instanceof Plant) {
                        ((Plant) alive).setX(i);
                        ((Plant) alive).setX(j);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        filInSpecies();
        filInIsland();
        fillInParams();
        Stats stats = new Stats();
        Object lock = new Object();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

        for (int i = 0; i < animalIsland.length; i++) {
            for (int j = 0; j < animalIsland[0].length; j++) {

                synchronized (lock) {
                    executorService.scheduleAtFixedRate(animalIsland[i][j], 0, 1, TimeUnit.SECONDS);
                }
                synchronized (lock) {
                    executorService.scheduleAtFixedRate(() -> {
                        addPlants();
                    }, 0, 5, TimeUnit.SECONDS);
                }
            }
        }
        synchronized (lock) {
            executorService.scheduleAtFixedRate(() -> {
                System.out.println("Day " + day);
                day++;
                stats.printStats();
                Stats.eatenAnimals = 0;
                Stats.eatenPlants = 0;
            }, 0, 1, TimeUnit.SECONDS);
        }

        Thread.sleep(31000);
        executorService.shutdown();
    }


    public static void addPlants() {
        for (int i = 0; i < animalIsland.length; i++) {
            for (int j = 0; j < animalIsland[0].length; j++) {
                for (int k = 0; k < 4; k++) {
                    animalIsland[i][j].animals.add(new Plant());
                }
            }
        }
    }
}
