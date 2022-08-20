package animalHierarchy;

import iranai.*;
import species.carnivore.*;
import species.hervivore.*;

public class AnimalFactory {
    public Animal createAnimal(AnimalType animalType){
        Animal animal = switch (animalType){
            case WOLF->new Wolf();
            case BOA->new Boa();
            case FOX->new Fox();
            case BEAR->new Bear();
            case EAGLE-> new Eagle();
            case HORSE-> new Horse();
            case DEER->new Deer();
            case RABBIT-> new Rabbit();
            case MOUSE-> new Mouse();
            case GOAT-> new Goat();
            case SHEEP->new Sheep();
            case BOAR->new Boar();
            case BUFFALO->new Buffalo();
            case DUCK-> new Duck();
            case CATERPILLAR->new Caterpillar();

        };
        return animal;
    }
}
