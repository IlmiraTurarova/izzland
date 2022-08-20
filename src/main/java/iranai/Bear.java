package iranai;


import animalHierarchy.Alive;
import animalHierarchy.Animal;
import animalHierarchy.AnimalType;
import animalHierarchy.Carnivore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import species.dump.Dump;
import species.hervivore.*;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bear extends Carnivore implements Animal {
    static ThreadLocalRandom randomN = ThreadLocalRandom.current();
    private int x;
    private int y;
    private double weight;

    @Override
    public synchronized void  eat() {
        double tillFull = 0;
        double eaten = 0;
        for (AnimalType type : Dump.species) {
            if (type.name().equalsIgnoreCase("Bear")) {
                tillFull = type.getEatTillFull();
            }
        }

        Double number = randomN.nextDouble();
        ListIterator<Alive> iter = Dump.animalIsland[x][y].animals.listIterator();
        while(iter.hasNext()) {
            Alive alive=iter.next();
            if (eaten >= tillFull) {
                this.weight += tillFull;
                break;
            } else {
                this.weight += eaten;
            }
            if (alive instanceof Boa) {
                if (number <= 0.80) {
                    eaten += ((Boa) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Horse) {
                if (number <= 0.40) {
                    eaten += ((Horse) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Deer) {
                if (number <= 0.80) {
                    eaten += ((Deer) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Rabbit) {
                if (number <= 0.80) {
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
            } else if (alive instanceof Goat) {
                if (number <= 0.70) {
                    eaten += ((Goat) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Sheep) {
                if (number <= 0.70) {
                    eaten += ((Sheep) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Boar) {
                if (number <= 0.50) {
                    eaten += ((Boar) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Buffalo) {
                if (number <= 0.20) {
                    eaten += ((Buffalo) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            } else if (alive instanceof Duck) {
                if (number <= 0.10) {
                    eaten += ((Duck) alive).getWeight();
                    synchronized (Dump.animalIsland[x][y]) {
                        Dump.animalIsland[x][y].animals.remove(alive);
                    }
                }
            }
        }

    }

    @Override
    public void move() {
        int oldx = x;
        int oldy = y;
        int speed = 0;
        for (AnimalType type : Dump.species) {
            if (type.name().equalsIgnoreCase("Bear")) {
                speed = type.getSpeed();
            }
        }
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

    @Override
    public void starveAndDie() {

    }

    @Override
    public void multiply() {

    }


}