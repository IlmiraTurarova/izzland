package species.carnivore;


import animalHierarchy.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.dump.Dump;
import species.hervivore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
@AnimalData(idealWeight = 500, amimalsInCell = 5,moveSpeed = 2,eatTillFull = 80)
public class Bear extends Carnivore implements Animal {
    static ThreadLocalRandom randomN = ThreadLocalRandom.current();
    private int x;
    private int y;
    private double weight;

    @Override
    public synchronized void  eat() {

        double eaten = 0;
        Class c = this.getClass();
        AnimalData thisAnimal = (AnimalData) c.getAnnotation(AnimalData.class);
        double tillFull=thisAnimal.eatTillFull();

        Double number = randomN.nextDouble();
        List<Alive> alivezincell = new ArrayList<>();
        synchronized (Dump.animalIsland[x][y].animals) {
            alivezincell.addAll(Dump.animalIsland[x][y].animals);
        }
        double weightBeginningToHunt = this.weight;
        for (int i = 0; i < alivezincell.size(); i++) {
            Alive alive = alivezincell.get(i);
            if (eaten >= tillFull) {
                this.weight += tillFull;
                break;
            } else {
                this.weight += eaten;
            }
            if (alive instanceof Boa) {
                if (number <= 0.80) {
                    eaten += ((Boa) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Horse) {
                if (number <= 0.40) {
                    eaten += ((Horse) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Deer) {
                if (number <= 0.80) {
                    eaten += ((Deer) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Rabbit) {
                if (number <= 0.80) {
                    eaten += ((Rabbit) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Mouse) {
                if (number <= 0.90) {
                    eaten += ((Mouse) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Goat) {
                if (number <= 0.70) {
                    eaten += ((Goat) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Sheep) {
                if (number <= 0.70) {
                    eaten += ((Sheep) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Boar) {
                if (number <= 0.50) {
                    eaten += ((Boar) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Buffalo) {
                if (number <= 0.20) {
                    eaten += ((Buffalo) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Duck) {
                if (number <= 0.10) {
                    eaten += ((Duck) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            }
        }
        double weightEndOfHunt = this.weight + eaten;
        if (weightBeginningToHunt == weightEndOfHunt) {
            this.weight = this.weight - (this.weight * 0.5);
        }
        Dump.animalIsland[x][y].animals = alivezincell;

    }

}