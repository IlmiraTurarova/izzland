package animalHierarchy;

import java.lang.reflect.InvocationTargetException;

public interface Animal extends Alive {
    void eat();

    void move();

    void starveAndDie();

    void multiply() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
