package species.hervivore;


import animalHierarchy.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.carnivore.Bear;
import species.carnivore.Wolf;
import species.dump.Dump;
import species.dump.Stats;
import species.plants.Plant;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@Data
@NoArgsConstructor
@AllArgsConstructor
@AnimalData(idealWeight = 70, amimalsInCell = 140,moveSpeed = 3,eatTillFull = 15)
public class Sheep extends Herbivore implements Animal {
    private int x;
    private int y;
    private double weight;

    @Override
    public synchronized void eat() {
        double eaten = 0;
        Class c = this.getClass();
        AnimalData thisAnimal = (AnimalData) c.getAnnotation(AnimalData.class);
        double tillFull=thisAnimal.eatTillFull();
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

            if (alive instanceof Plant) {
                eaten += ((Plant) alive).getWeight();
                alivezincell.remove(alive);
                Stats.eatenPlants++;
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
                        && Dump.animalIsland[x][y].animals.get(i) instanceof Sheep) {
                    couple++;
                    break;
                }
            }
            if (couple == 2) {
                synchronized (Dump.animalIsland[x][y]) {
                    Dump.animalIsland[x][y].animals.add((new Sheep(x,y,weight1)));
                }
            }
        }
    }

}