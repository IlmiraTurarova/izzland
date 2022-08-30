package species.carnivore;

import animalHierarchy.Alive;
import animalHierarchy.AnimalData;
import animalHierarchy.AnimalType;
import animalHierarchy.Carnivore;
import species.hervivore.Duck;
import species.hervivore.Mouse;
import species.hervivore.Rabbit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.dump.Dump;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
@AnimalData(idealWeight = 6, amimalsInCell = 20,moveSpeed = 3,eatTillFull = 1)
public class Eagle extends Carnivore {
    static ThreadLocalRandom randomN = ThreadLocalRandom.current();
    private int x;
    private int y;
    private double weight;
    @Override
    public synchronized void eat() {
        double tillFull = 0;
        double eaten = 0;
        for (AnimalType type : Dump.species) {
            if (type.name().equalsIgnoreCase("Eagle")) {
                tillFull = type.getEatTillFull();
            }
        }
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
            if (alive instanceof Fox) {
                if (number <= 0.10) {
                    eaten += ((Fox) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Rabbit) {
                if (number <= 0.90) {
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
            } else if (alive instanceof Duck) {
                if (number <= 0.80) {
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
