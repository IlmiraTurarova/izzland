package animalHierarchy;

import lombok.Data;

@Data
public abstract class Herbivore implements Animal{
    private int x;
    private int y;
    private double weight;
    public abstract void eat();
    public abstract void move();
    public abstract void beEaten();
    public abstract void starveAndDie();
}
