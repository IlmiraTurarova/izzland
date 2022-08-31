package species.carnivore;

import animalHierarchy.Alive;
import animalHierarchy.AnimalData;
import animalHierarchy.Carnivore;
import species.dump.Stats;
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
            if (alive instanceof Fox) {
                if (number <= 0.10) {
                    eaten += ((Fox) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                        Stats.eatenAnimals++;
                    }
                }
            } else if (alive instanceof Rabbit) {
                if (number <= 0.90) {
                    eaten += ((Rabbit) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                        Stats.eatenAnimals++;
                    }
                }
            } else if (alive instanceof Mouse) {
                if (number <= 0.90) {
                    eaten += ((Mouse) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                        Stats.eatenAnimals++;
                    }
                }
            } else if (alive instanceof Duck) {
                if (number <= 0.80) {
                    eaten += ((Duck) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                        Stats.eatenAnimals++;
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
    @Override
    public void multiply()  {
        int couple = 0;
        Class c = this.getClass();
        AnimalData thisAnimal = (AnimalData) c.getAnnotation(AnimalData.class);
        double weight1=thisAnimal.idealWeight();
        synchronized (Dump.animalIsland[x][y]) {
        for (int i = 0; i < Dump.animalIsland[x][y].animals.size(); i++) {

                if (Dump.animalIsland[x][y].animals.get(i) == this) {
                    couple++;
                }
                if (Dump.animalIsland[x][y].animals.get(i) != this
                        && Dump.animalIsland[x][y].animals.get(i) instanceof Eagle) {
                    couple++;
                    break;
                }
            }
            if (couple == 2) {
                synchronized (Dump.animalIsland[x][y]) {
                    Dump.animalIsland[x][y].animals.add((new Eagle(x,y,weight1)));
                }
            }
        }
    }



}
