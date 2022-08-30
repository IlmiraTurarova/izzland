package species.hervivore;


import animalHierarchy.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.carnivore.Wolf;
import species.dump.Dump;
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
        double tillFull = 0;
        double eaten = 0;
        for (AnimalType type : Dump.species) {
            if (type.name().equalsIgnoreCase("Sheep")) {
                tillFull = type.getEatTillFull();
            }
        }
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
            }
        }
        double weightEndOfHunt = this.weight + eaten;
        if (weightBeginningToHunt == weightEndOfHunt) {
            this.weight = this.weight - (this.weight * 0.5);
        }
        Dump.animalIsland[x][y].animals = alivezincell;

    }


}