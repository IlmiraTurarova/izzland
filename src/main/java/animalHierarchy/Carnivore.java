package animalHierarchy;

import lombok.Data;
import species.dump.Dump;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Data
public abstract class Carnivore implements Animal{
    private int x;
    private int y;
    private double weight;
    public abstract void eat();
    public  void move(){
        int oldx = x;
        int oldy = y;
        Class c=this.getClass();
        AnimalData thisAnimal = (AnimalData) c.getAnnotation(AnimalData.class);
        int speed=thisAnimal.moveSpeed();
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
    public  void starveAndDie(){
        Class c=this.getClass();
        AnimalData thisAnimal = (AnimalData) c.getAnnotation(AnimalData.class);
        Double idealWeight=thisAnimal.idealWeight();
        if (this.weight < (idealWeight * 0.5)) {
            synchronized (Dump.animalIsland[x][y]) {
                Dump.animalIsland[x][y].animals.removeIf(x -> x == this);
            }
        }
    }

    public void multiply() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int couple = 0;
        Class c = this.getClass();
        AnimalData thisAnimal = (AnimalData) c.getAnnotation(AnimalData.class);
        double weight1=thisAnimal.idealWeight();
        Constructor constructor = c.getConstructor(int.class,int.class,double.class);
        String className=c.getSimpleName();
        for (int i = 0; i < Dump.animalIsland[x][y].animals.size(); i++) {
            synchronized (Dump.animalIsland[x][y].animals) {
                if (Dump.animalIsland[x][y].animals.get(i) == this) {
                    couple++;
                }
                if (Dump.animalIsland[x][y].animals.get(i) != this
                        && Dump.animalIsland[x][y].animals.get(i).getClass().getSimpleName().equals(className)) {
                    couple++;
                    break;
                }
            }
            if (couple == 2) {
                synchronized (Dump.animalIsland[x][y]) {
                    Dump.animalIsland[x][y].animals.add((Alive) constructor.newInstance(x,y,weight1));
                }
            }
        }

    }
}
