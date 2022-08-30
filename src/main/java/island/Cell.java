package island;


import animalHierarchy.Alive;
import animalHierarchy.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import species.dump.Dump;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Data
@AllArgsConstructor
public class Cell extends Thread {
    private int x;
    private int y;
    public List<Alive> animals;

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", animals=" + animals +
                '}';
    }

    @Override
    public void run() {
        synchronized (Dump.animalIsland[x][y].animals) {

            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i) instanceof Animal) {
                    ((Animal) animals.get(i)).move();
                }
            }
        }

        synchronized (Dump.animalIsland[x][y].animals) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i) instanceof Animal) {
                    ((Animal) animals.get(i)).eat();
                }
            }
        }
        synchronized (Dump.animalIsland[x][y].animals) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i) instanceof Animal) {
                    ((Animal) animals.get(i)).starveAndDie();
                }
            }
        }
        synchronized (Dump.animalIsland[x][y].animals) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i) instanceof Animal) {
                    try {
                        ((Animal) animals.get(i)).multiply();
                    } catch (Exception e) {
                        System.out.println("does not work");
                    }
                }
            }
        }
    }
}

