package island;


import animalHierarchy.Alive;
import animalHierarchy.Animal;
import lombok.AllArgsConstructor;
import lombok.Data;
import species.dump.Dump;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class Cell extends Thread{
    private int x;
    private int y;
    public List<Alive> animals;

    @Override
    public void run() {

        synchronized (Dump.animalIsland[x][y].animals) {
            for (int i = 0; i < animals.size(); i++) {
                if (animals.get(i) instanceof Animal) {
                    ((Animal) animals.get(i)).move();
                    //((Animal) animals.get(i)).eat();
                     //((Animal) animals.get(i)).beEaten();

                }
            }
        }
        }
    }

