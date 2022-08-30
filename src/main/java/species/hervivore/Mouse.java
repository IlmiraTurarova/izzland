package species.hervivore;

import animalHierarchy.Alive;
import animalHierarchy.AnimalData;
import animalHierarchy.AnimalType;
import animalHierarchy.Herbivore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.carnivore.Bear;
import species.carnivore.Wolf;
import species.dump.Dump;
import species.hervivore.Caterpillar;
import species.plants.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
@AnimalData(idealWeight = 0.05, amimalsInCell = 500,moveSpeed = 1,eatTillFull = 0.01)
public class Mouse extends Herbivore {
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
            if (alive instanceof Caterpillar) {
                if (number <= 0.90) {
                    eaten += ((Caterpillar) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            }else if(alive instanceof Plant){
                eaten += ((Plant) alive).getWeight();
                synchronized (Dump.animalIsland[x][y]) {
                    Dump.animalIsland[x][y].animals.remove(alive);
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
        for (int i = 0; i < Dump.animalIsland[x][y].animals.size(); i++) {
            synchronized (Dump.animalIsland[x][y].animals) {
                if (Dump.animalIsland[x][y].animals.get(i) == this) {
                    couple++;
                }
                if (Dump.animalIsland[x][y].animals.get(i) != this
                        && Dump.animalIsland[x][y].animals.get(i) instanceof Mouse) {
                    couple++;
                    break;
                }
            }
            if (couple == 2) {
                synchronized (Dump.animalIsland[x][y]) {
                    Dump.animalIsland[x][y].animals.add((new Mouse(x,y,weight1)));
                }
            }
        }
    }


}
