package animalHierarchy;

import lombok.Data;
import species.carnivore.Wolf;
import species.dump.Dump;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Data
public abstract class Herbivore implements Animal {
    private int x;
    private int y;
    private double weight;

    public abstract void eat();

    public void move() {
        int oldx = x;
        int oldy = y;
        Class c = this.getClass();
        AnimalData thisAnimal = (AnimalData) c.getAnnotation(AnimalData.class);
        int speed=thisAnimal.moveSpeed();
        int animalsInCell=thisAnimal.amimalsInCell();
        int random = (int) (Math.random() * 4);
        try {
            if (random == 0) {
                x += speed;
                if (x > Dump.animalIsland.length) {
                    x -= speed;
                    throw new Exception();
                }
                if (x < 0) {
                    x -= speed;
                    throw new Exception();
                }
            } else if (random == 1) {
                y += speed;
                if (y > Dump.animalIsland[0].length) {
                    y -= speed;
                    throw new Exception();
                }
                if (y < 0) {
                    y -= speed;
                    throw new Exception();
                }
            } else if (random == 2) {

                x -= speed;
                if (x > Dump.animalIsland.length) {
                    x += speed;
                    throw new Exception();
                }
                if (x < 0) {
                    x += speed;
                    throw new Exception();
                }
            } else {
                y -= speed;
                if (y > Dump.animalIsland[0].length) {
                    y += speed;
                    throw new Exception();
                }
                if (y < 0) {
                    y += speed;
                    throw new Exception();
                }
            }
            int animalsCount=0;
            for (int i = 0; i < Dump.animalIsland[x][y].animals.size(); i++) {
                if(Dump.animalIsland[x][y].animals.get(1).getClass().getSimpleName().equalsIgnoreCase(c.getSimpleName())){
                    animalsCount++;
                }
            }
            if(animalsCount>=animalsInCell){
                throw new Exception();
            }
            int newx = x;
            int newy = y;

            synchronized (Dump.animalIsland[oldx][oldy]) {
                Dump.animalIsland[oldx][oldy].animals.removeIf(x -> x == this);
            }
            synchronized (Dump.animalIsland[newx][newy]) {
                Dump.animalIsland[newx][newy].animals.add(this);
            }


        } catch (Exception e) {
            move();
        }
    }

    public void starveAndDie() {
        Double idealWeight = 0.0;
        Class c = this.getClass();
        AnimalData thisAnimal = (AnimalData) c.getAnnotation(AnimalData.class);
        idealWeight = thisAnimal.idealWeight();

        if (this.getWeight() < (idealWeight * 0.5)) {
            synchronized (Dump.animalIsland[x][y]) {
                Dump.animalIsland[x][y].animals.remove(this);
            }
        }
    }

}
