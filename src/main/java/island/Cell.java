package island;


import animalHierarchy.Alive;
import animalHierarchy.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import species.dump.Dump;
import species.dump.Stats;

import java.util.List;

@Data
@AllArgsConstructor
public class Cell extends Thread {
    private int x;
    private int y;
    public List<Alive> animals;
    private static int day=1;

    @Override
    public void run() {
        Object lock = new Object();
        Stats stats = new Stats();
        synchronized (lock) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i) instanceof Animal) {
                    ((Animal) animals.get(i)).move();
                }
            }
        }

        synchronized (lock) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i) instanceof Animal) {
                    ((Animal) animals.get(i)).eat();
                }
            }
        }
        synchronized (lock) {
            synchronized (animals) {
                for (int i = 0; i < animals.size(); i++) {
                    if (animals.get(i) instanceof Animal) {
                        ((Animal) animals.get(i)).starveAndDie();
                    }

                }
            }
        }
        synchronized (lock) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i) instanceof Animal) {
                    ((Animal) animals.get(i)).multiply();
                }
            }
        }
        synchronized (lock) {

            System.out.println("День " + day);
            day++;
            stats.printStats();
            Stats.eatenAnimals=0;
            Stats.eatenPlants=0;
        }
    }
}

